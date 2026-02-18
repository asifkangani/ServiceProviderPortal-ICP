package com.dtt.organization.service.impl;

import com.dtt.organization.dto.LoginRequestDTO;
import com.dtt.organization.dto.TrustedUserDTO;
import com.dtt.organization.dto.TrustedUserResponseDTO;
import com.dtt.organization.model.TrustedUsersEntity;
import com.dtt.organization.repository.PasswordResetTokenRepository;
import com.dtt.organization.repository.TrustedUsersRepository;
import com.dtt.organization.repository.SpocRepository;
import com.dtt.organization.service.iface.TrustedUserService;
import com.dtt.organization.util.APIRequestHandler;
import com.dtt.organization.util.ApiResponse;
import com.dtt.organization.util.AppUtil;
import com.dtt.organization.util.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrustedUserServiceImpl implements TrustedUserService {
    private static final String CLASS = "TrustedUserServiceImpl";
    private static final Logger logger = LoggerFactory.getLogger(TrustedUserServiceImpl.class);

    private final SpocRepository spocRepository;

    private final TrustedUsersRepository trustedUsersRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;



    @Value("${portal.url}")
    private String portalUrl;

    @Value("${portal.name}")
    private String portalName;

    @Value("${spoc.details}")
    String spocDetails;

    @Autowired
    APIRequestHandler apiRequestHandler;


    public TrustedUserServiceImpl(

            SpocRepository spocRepository,

            TrustedUsersRepository trustedUsersRepository,
            EmailService emailService,PasswordEncoder passwordEncoder,PasswordResetTokenRepository passwordResetTokenRepository) {


        this.spocRepository = spocRepository;

        this.trustedUsersRepository = trustedUsersRepository;
        this.emailService=emailService;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }


//    @Value("${reset.password.url}")
//    private String resetPasswordUrl;



    @Override
    public ApiResponse  login(LoginRequestDTO dto) {
        logger.info("{} login  :::" ,CLASS);

        try {

            TrustedUsersEntity auth = trustedUsersRepository.findByEmail(dto.getEmail());

            if (auth == null) {
                return new ApiResponse<>(false, "Invalid email", null);
            }

            boolean matches = passwordEncoder.matches(dto.getPassword(), auth.getPassword()
            );

            if (!matches) {
                return new ApiResponse<>(false, "Invalid password", null);
            }

            return new ApiResponse (true, "Login successful", null);
        }catch (Exception e){
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something went wrong",null);

        }
    }


    public ApiResponse saveTrustedUser(TrustedUserDTO trustedUserDTO) {
        logger.info("{} save trust user :::" ,CLASS);
        try {
            TrustedUsersEntity trustedUsersEntity =
                    trustedUsersRepository.findByEmail(trustedUserDTO.getEmail());

            if (trustedUsersEntity != null) {
                return new ApiResponse(
                        false,
                        "This user is already added as Trusted User",
                        null
                );
            }

            String url = spocDetails + trustedUserDTO.getEmail();
            logger.info(CLASS + " fetching user details by calling {} ", url);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

            ApiResponse spocResponse =
                    apiRequestHandler.handleApiRequest(
                            url,
                            HttpMethod.GET,
                            requestEntity
                    );

            if (spocResponse == null || !spocResponse.isSuccess()) {
                logger.warn("{} Email not found in SPOC details ::: {}",
                        CLASS, trustedUserDTO.getEmail());

                return new ApiResponse(
                        false,
                        "User is not available in SPOC details. Cannot add as Trusted User",
                        null
                );
            }

            TrustedUsersEntity trustedUsers = new TrustedUsersEntity();
            trustedUsers.setName(trustedUserDTO.getName());
            trustedUsers.setEmail(trustedUserDTO.getEmail());
            trustedUsers.setMobileNumber(trustedUserDTO.getMobileNumber());
            trustedUsers.setCreatedOn(AppUtil.getDate());
            trustedUsers.setUpdatedOn(AppUtil.getDate());




            String body =
                    "<html>" +
                            "<body style='font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #000;'>" +

                            "<p>Dear " + trustedUserDTO.getName() + ",</p>" +

                            "<p>You are added as Trusted User by Administrator" +
                            "<p>Kindly Visit <strong>" + portalName + "</strong>" +


                            "<p>" +
                            "<strong>Link:</strong><br/>" +
                            "<a href='" + portalUrl + "' target='_blank'>" + portalUrl + "</a>" +
                            "</p>" +


                            "<p>Regards,<br/>" +
                            "Admin</p>" +

                            "<p style='font-size: 10px; font-style: italic; color: gray;'>" +
                            "* This is an automated email from <strong>" + portalName + "</strong>. " +
                            "Please contact the administrator if you have any questions regarding this email." +
                            "</p>" +

                            "</body>" +
                            "</html>";


            trustedUsersRepository.save(trustedUsers);
            emailService.sendEmail(trustedUserDTO.getEmail(),  body,"Added as Trusted User " + portalName);



            return new ApiResponse(true,"Trusted user saved successfully",null);


        }catch (Exception e){
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something went wrong",null);
        }
    }

    @Override
    public ApiResponse getAllTrustedUsers() {
        logger.info("{} get all trusted users  :::" ,CLASS);
        try{


            List<TrustedUserResponseDTO> users =
                    trustedUsersRepository.findAll()
                            .stream()
                            .map(user -> {
                                TrustedUserResponseDTO dto = new TrustedUserResponseDTO();
                                dto.setId(user.getId());
                                dto.setName(user.getName());
                                dto.setEmail(user.getEmail());
                                dto.setMobileNumber(user.getMobileNumber());
                                dto.setCreatedOn(user.getCreatedOn());
                                dto.setUpdatedOn(user.getUpdatedOn());
                                return dto;
                            })
                            .toList();

            return new ApiResponse(true,"Trusted user saved successfully",trustedUsersRepository.findAll());

        }catch (Exception e){
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something went wrong",null);
        }
    }





}
