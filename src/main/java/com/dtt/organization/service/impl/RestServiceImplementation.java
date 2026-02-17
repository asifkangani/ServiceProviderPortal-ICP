package com.dtt.organization.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dtt.organization.dto.JwkNew;
import com.dtt.organization.dto.UserInfo;
import com.dtt.organization.service.iface.RestService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class RestServiceImplementation implements RestService {

    @Value("${idp.clientId}")
    private String clientId;

    @Value("${idp.clintSecret}")
    private String clientSecret;

    @Value("${idp.redirectUri}")
    private String redirectUri;
    @Value("${idp.openid}")
    private boolean openId;
    @Value("${jwt.clientassertiontype}")
    private String clientassertiontype;

    @Value("${idp.tokenUrl}")
    private String tokenUrl;

    @Value("${idp.idpjwkSetURL}")
    private String idpjwkSetURL;
    @Value("${idp.userInfoUrl}")
    private String userInfoUrl;
    @Value("${idp.aud}")
    private String Aud;
    @Value("${idp.scope}")
    private String scope;


    RestTemplate restTemplate = new RestTemplate();
    @Value("${privateKey}")
    private String privateKey;

    @Value("${idp.authorizationHeaderName}")
    private String authorizationHeaderName;


    @Value("${url.login.user.photo}")
    private String loginUserPhoto;

    private static final long VALIDITY_IN_MILLISECONDS = 24 * 60 * 60 * 1000;

    private static Logger logger = LoggerFactory.getLogger(RestServiceImplementation.class);


    private static final String CLASS = "RestServiceImplementation";

    public String generateJWTWithRsa(Boolean isAuthorizedUrl, String state, String nonce) {
        try {
            // Generate Token
            String id = UUID.randomUUID().toString();
            //Payout data of JWT
            Map<String, Object> claims = new HashMap<>( );
            claims.put("iss", clientId);
            claims.put("sub", clientId);
            claims.put("iat", new Date(System.currentTimeMillis()));

            if(isAuthorizedUrl) {
                claims.put("redirect_uri",redirectUri);
                claims.put("aud", Aud);
                claims.put("scope",scope);
                claims.put("state",state);
                claims.put("nonce",nonce);
            }else {
                claims.put("aud", tokenUrl);
            }

            PrivateKey privateKey = getPrivateKey();
            String signedToken = generateJwtToken(privateKey, VALIDITY_IN_MILLISECONDS,id, claims);
            return signedToken;
        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return null;
        }
    }


    public String generateJwtToken(PrivateKey privateKey, long expirationInMillis, String id, Map<String, Object> claims) {
        try {
            JwtBuilder builder = Jwts.builder()
                    .setId(id)
                    .setClaims(claims)
                    .setHeaderParam("typ", "JWT")
                    .setNotBefore(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()))
                    .signWith(SignatureAlgorithm.RS256, privateKey);

            if (expirationInMillis >= 0) {
                long expMillis = System.currentTimeMillis() + expirationInMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);
            }

            String token = builder.compact();
            return token;
        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return null;
        }
    }





    private PrivateKey getPrivateKey() {
        try {
            String rsaPrivateKey = privateKey;
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey _privateKey = kf.generatePrivate(keySpec);
            return _privateKey;
        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return null;
        }
    }



    public String generateApplicationUid() {
        UUID ouid = UUID.randomUUID();
        return ouid.toString();
    }

    @Override
    public UserInfo getUserInfo(String code, HttpServletRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            String authHeaderVal=clientId+":"+clientSecret;
            String clientIdBase64= Base64.getEncoder().encodeToString(authHeaderVal.getBytes());
            headers.set(authorizationHeaderName, "Basic "+clientIdBase64);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", "authorization_code");
            map.add("client_id", clientId);
            map.add("redirect_uri", redirectUri);
            map.add("code", code);
            if(openId){
                map.add("client_assertion", generateJWTWithRsa(false, null, null));
                map.add("client_assertion_type",clientassertiontype);
            }


            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
            ResponseEntity<String> response = restTemplate.exchange(this.tokenUrl, HttpMethod.POST, entity,
                    String.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                if(root.has("error")){
                    logger.info("{} token end point: {} ",CLASS,response.getBody());
                    return null;
                }

                JsonNode access_token = root.path("access_token");
                JsonNode expiresInNode = root.path("expires_in");
                String accessToken = access_token.asText();
                long expiresIn = expiresInNode.asLong();


                if(openId){
                    JsonNode idToken = root.path("id_token");

                    String token = idToken.asText();
                    if (idToken.asText() != null) {
                        try {
                            DecodedJWT decodedIdToken = JWT.decode(token);
                            String tokennonce =decodedIdToken.getClaim("nonce").asString();
                            String nonce = (String) request.getSession().getAttribute("nonce");
                            if(!tokennonce.equals(nonce) ) {

                                throw new RuntimeException("Nonce not match!");
                            }

                            ResponseEntity<String> restResponse = restTemplate.getForEntity(idpjwkSetURL, String.class);
						/*
						We can also cache this jwksurl response, and reuse for future calls
						 */
                            JSONObject jwkUrlResponse = new JSONObject(restResponse.getBody());
                            JSONArray arrKeys = jwkUrlResponse.getJSONArray("keys");
                            JwkNew jwkNew = new JwkNew();
                            for (int i = 0; i < arrKeys.length(); i++) {
                                String kid = arrKeys.getJSONObject(i).getString("kid");
                                if (kid.equals(decodedIdToken.getKeyId())) {
                                    jwkNew.setAlgorithm(arrKeys.getJSONObject(i).getString("alg"));
                                    jwkNew.setE(arrKeys.getJSONObject(i).getString("e"));
                                    jwkNew.setId(arrKeys.getJSONObject(i).getString("kid"));
                                    jwkNew.setN(arrKeys.getJSONObject(i).getString("n"));
                                    jwkNew.setType(arrKeys.getJSONObject(i).getString("kty"));
                                    jwkNew.setUsage(arrKeys.getJSONObject(i).getString("use"));
                                }
                            }


                            Claim claim = decodedIdToken.getClaim("daes_claims");
                            UserInfo userInfo = claim.as(UserInfo.class);
                            userInfo.setIdToken(token);
                            String Application_id = generateApplicationUid();
                            userInfo.setApplicationID(Application_id);


                            if (expiresIn > 0) {
                                Instant expiryTime = Instant.now().plusSeconds(expiresIn);
                                request.getSession().setAttribute("tokenExpiry", expiryTime);

                                request.getSession().setAttribute("tokenExpiry", expiryTime);

                            }
                            return userInfo;
                        } catch (Exception e) {
                            logger.error("Unexpected exception", e);

                        }
                    }
                }

                else{
                    HttpHeaders headers1 = new HttpHeaders();
                    headers1.set(authorizationHeaderName, "Bearer "+accessToken);
                    HttpEntity<MultiValueMap<String, String>> entity1 = new HttpEntity<>(headers1);
                    ResponseEntity<String> response1 = restTemplate.exchange(this.userInfoUrl, HttpMethod.GET, entity1,
                            String.class);
                    if (response1.getStatusCode().equals(HttpStatus.OK)) {
                        JsonNode root1 = mapper.readTree(response1.getBody());
                        if(root1.has("error")){
                            return null;
                        }

                        UserInfo userInfo = mapper.readValue(root1.toString(),UserInfo.class);
                        String Application_id = generateApplicationUid();
                        userInfo.setApplicationID(Application_id);
                        userInfo.setAccessToken(accessToken);

                        if (expiresIn > 0) {
                            Instant expiryTime = Instant.now().plusSeconds(expiresIn);
                            request.getSession().setAttribute("tokenExpiry", expiryTime);
                            request.getSession().setAttribute("tokenExpiry", expiryTime);


                            Duration duration = Duration.between(Instant.now(), expiryTime);
                            request.getSession().setMaxInactiveInterval((int) duration.getSeconds());
                        }
                        return userInfo;
                    }

                }
            }
        } catch (Exception e) {
            logger.error("Unexpected exception", e);
        }
        return null;
    }

}


