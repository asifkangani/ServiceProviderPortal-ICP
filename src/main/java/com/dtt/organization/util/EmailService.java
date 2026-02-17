package com.dtt.organization.util;


import com.dtt.organization.controller.OrganizationController;
import com.dtt.organization.dto.EmailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class EmailService {

    private static final String CLASS = "EmailService";
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${send.email.url}")
    private String sendEmail;


    private RestTemplate restTemplate = new RestTemplate();

    @Value("${url.getEmaillist}")
    private  String getEmaillist;

    @Value("${max.adminEmails}")

    private  int maxAdminEmailCount;

    public ApiResponse sendEmail(String spocEmail, String emailBody, String emailSubject) {
        try {


            List<String> listOfEmail = new ArrayList<>();
            listOfEmail.add(spocEmail);

            String subject = emailSubject;
            EmailDto emailDto = new EmailDto();
            emailDto.setEmailBody(emailBody);
            emailDto.setRecipients(listOfEmail);
            emailDto.setSubject(subject);



            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity<>(emailDto, headers);

            ResponseEntity<ApiResponse> res = restTemplate.exchange(sendEmail, HttpMethod.POST, requestEntity,
                    ApiResponse.class);


            if (res.getStatusCode() == HttpStatus.OK) {
                return new ApiResponse(true, res.getBody().getMessage(), res.getBody().getResult());
            } else if (res.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new ApiResponse(false, "Bad Request", null);
            } else if (res.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new ApiResponse(false, "Internal Server Error", null);
            }
            return new ApiResponse(true, "Email send successfully", null);
        }catch (ResourceAccessException e) {

            return new ApiResponse(false, "Service is unavailable or connection refused", null);

        } catch (HttpClientErrorException | HttpServerErrorException e) {

            return new ApiResponse(false, e.getStatusCode().toString(), null);

        } catch (Exception e) {
            return new ApiResponse(false, "Unexpected error: " + e.getMessage(), null);
        }
    }
    public   ApiResponse sendEmailForAdmin(List<String> spocEmail, String emailBody, String emailSubject) {
        try {

            List<String> listOfEmail = new ArrayList<>();
            for (String i : spocEmail) {
                listOfEmail.add(i);
            }


            String subject = emailSubject;
            EmailDto emailDto = new EmailDto();
            emailDto.setEmailBody(emailBody);
            emailDto.setRecipients(listOfEmail);
            emailDto.setSubject(subject);
            emailDto.setSendMailToAdmin(true);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity<>(emailDto, headers);

            ResponseEntity<ApiResponse> res = restTemplate.exchange(sendEmail, HttpMethod.POST, requestEntity,
                    ApiResponse.class);


            if (res.getStatusCode() == HttpStatus.OK) {
                return new ApiResponse(true, res.getBody().getMessage(), res.getBody().getResult());
            } else if (res.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new ApiResponse(false, "Bad Request", null);
            } else if (res.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new ApiResponse(false, "Internal Server Error", null);
            }
            return new ApiResponse(true, "Email send successfully", null);
        }catch (ResourceAccessException e) {

            return new ApiResponse(false, "Service is unavailable or connection refused", null);

        } catch (HttpClientErrorException | HttpServerErrorException e) {

            return new ApiResponse(false, e.getStatusCode().toString(), null);

        } catch (Exception e) {
            return new ApiResponse(false, "Something went wrong: " + e.getMessage(), null);
        }

    }


    public ApiResponse getAdminEmailList() {
        ResponseEntity<ApiResponse> res = null;
        try {
            String adminEmailUrl = getEmaillist;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);


            res = restTemplate.exchange(adminEmailUrl, HttpMethod.GET, requestEntity, ApiResponse.class);


            if (res.getStatusCode() == HttpStatus.OK) {
                List<String> emailList = (List<String>) res.getBody().getResult();

                if (emailList == null || emailList.isEmpty()) {
                    return new ApiResponse(true, "No Emails Found", Collections.emptyList());
                }

                if (maxAdminEmailCount == 0) {
                    return new ApiResponse(true, "Email List Fetched Successfully", emailList);
                }

                if (emailList.size() <= maxAdminEmailCount) {
                    return new ApiResponse(true, "Email List Fetched Successfully", emailList);
                }


                return new ApiResponse(true, "Partial Email List Fetched Successfully",
                        emailList.subList(0, maxAdminEmailCount));
            } else {

                return new ApiResponse(false, "Error Fetching Emails", res.getBody().getResult());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Unexpected exception", e);
            return new ApiResponse(false, "Error Fetching Emails", e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
            logger.error("Unexpected exception", e);
            return new ApiResponse(false, "Unexpected Error Occurred", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return new ApiResponse(false, "Unknown Error Occurred", e.getMessage());
        }
    }
}
