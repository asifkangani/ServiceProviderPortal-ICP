package com.dtt.organization.controller;


import com.dtt.organization.dto.UserInfo;
import com.dtt.organization.model.TrustedUsersEntity;
import com.dtt.organization.repository.TrustedUsersRepository;
import com.dtt.organization.security.CustomUserDetailsService;
import com.dtt.organization.service.iface.RestService;
import com.dtt.organization.service.impl.OrganizationServiceImpl;
import com.dtt.organization.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
public class LoginController {


    RestTemplate restTemplate = new RestTemplate();

    @Value("${idp.idpUrl}")
    private String idp;

    @Value("${idp.openid}")
    private boolean openId;

    @Value("${idp.clientId}")
    private String clientId;

    @Value("${idp.redirectUri}")
    private String redirectUri;

    @Value("${idp.scope}")
    private String scope;

    @Value("${idp.logoutUrl}")
    private String logoutUrl;


    @Autowired
    RestService restService;

    @Value("${idp.authorizationHeaderName}")
    private String authorizationHeaderName;


    @Value("${url.login.user.photo}")
    private String loginUserPhoto;

    @Autowired
    TrustedUsersRepository trustedUsersRepository;

    @Autowired
    OrganizationServiceImpl organizationService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;



    private static Logger logger = LoggerFactory.getLogger(LoginController.class);


    private static final  String CLASS = "Login Controller";



    @GetMapping("/")

    public ModelAndView getLoginPage(Model model, HttpSession session){
        String state= java.util.UUID.randomUUID().toString();
        String nonce = java.util.UUID.randomUUID().toString();
        String idpUrl;
        if(openId)
            idpUrl =idp+"client_id="+clientId+"&redirect_uri="+redirectUri+"&response_type=code"+"&scope="+scope+"&state="+state+"&nonce="+nonce+"&request="+restService.generateJWTWithRsa(true, state, nonce);
        else
            idpUrl =idp+"client_id="+clientId+"&redirect_uri="+redirectUri+"&response_type=code"+"&scope="+scope.replace("openid ","")+"&state="+state+"&nonce="+nonce;

        model.addAttribute("idpUrl",idpUrl);
        model.addAttribute("logoutUrl",logoutUrl);
        session.setAttribute("state", state);
        session.setAttribute("nonce", nonce);
        return new ModelAndView("login");

    }


    @GetMapping("/eoi-redirect")
    public ModelAndView callback(
            @RequestParam(name = "code") Optional<String> code,
            @RequestParam(name = "state") Optional<String> state,
            @RequestParam(name = "error") Optional<String> error,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) {


        try {
            System.out.println("inside try");
            if (!code.isPresent()) {
                logger.warn(CLASS + "Missing authorization code. Redirecting to login.");
                return new ModelAndView("redirect:/login");
            }

            logger.info(CLASS + "Authorization code present, fetching user info...");

            UserInfo userInfo = restService.getUserInfo(code.get(), request);
            if (userInfo == null) {
                logger.warn(CLASS + "UserInfo is null. Redirecting to login.");
                return new ModelAndView("redirect:/login");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set(authorizationHeaderName, "Bearer " + userInfo.getAccessToken());
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

            ResponseEntity<ApiResponse> photoResponse = restTemplate.exchange(
                    loginUserPhoto, HttpMethod.GET, entity, ApiResponse.class);

            if (photoResponse.getBody() == null || !photoResponse.getBody().isSuccess()) {
                logger.error(CLASS + "Failed to fetch user photo");
                model.addAttribute("message", "Error fetching user photo");
                return new ModelAndView("login");
            }

//            session.setAttribute("userPhoto", photoResponse.getBody().getResult());
//            session.setAttribute("userInfo", userInfo);
//            session.setAttribute("isLoginWithIdp", true);
//            session.setAttribute("email", userInfo.getEmail());
//            session.setAttribute("spocSuid", userInfo.getSuid());
//            session.setAttribute("name", userInfo.getName());

            logger.info(CLASS + "Session attributes set for user: {}", userInfo.getEmail());

            String email = userInfo.getEmail();
            TrustedUsersEntity trustedSpocs =  trustedUsersRepository.findByEmail(email);

            //uncomment for deploy
//            ApiResponse forms = organizationService.syncDataFromAdmin(email);
//
//            if (forms.getResult() == null && (trustedSpocs == null)) {
//                logger.warn(CLASS + "User is not trusted: {}", email);
//                model.addAttribute("message", "Not a Trusted User");
//
//                return new ModelAndView("login");
//            }
//
//            if(forms.getResult()!=null){
//                return new ModelAndView("redirect:/dashboard");
//            }

            //uncomment

            if (trustedSpocs != null) {


                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                // 2. Create authentication token
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 3. Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(auth);

                // 4. Store security context in session
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

                // 5. Redirect to dashboard
                return new ModelAndView("redirect:/dashboard");
            }

            else{
                model.addAttribute("message", "Not a Trusted User");
                return new ModelAndView("login");
            }



        } catch (Exception e) {
            logger.error(CLASS + "Error in callback", e);
            return new ModelAndView("redirect:/login");
        }
    }


    @GetMapping("/logout")
    @CacheEvict
    public ModelAndView idpLogout(HttpSession session, HttpServletResponse response, Model model, HttpServletRequest request)
    {


        logger.info("::::::::IDP logout::::::::::");
        UserInfo userInfo= (UserInfo) session.getAttribute("userInfo");


        if(userInfo!=null)
        {

            session.invalidate();

            response.setHeader("Cache-Control", "no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");


            return new ModelAndView("redirect:/");

        }

        response.setHeader("Cache-Control", "no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        return new ModelAndView("redirect:/") ;

    }

    @GetMapping("/api/get/ssp/status")
    public String status(){
        return "Up and running";
    }







}
