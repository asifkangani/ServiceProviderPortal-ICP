package com.dtt.organization.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Configuration

public class APIRequestHandler {

    RestTemplate restTemplate = new RestTemplate();

    public ApiResponse handleApiRequest(String url, HttpMethod method, HttpEntity<Object> requestEntity) {
        try {
            ResponseEntity<ApiResponse> response = restTemplate.exchange(url, method, requestEntity, ApiResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return new ApiResponse(response.getBody().isSuccess(), response.getBody().getMessage(), response.getBody().getResult());

            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new ApiResponse(false, "Bad Request", null);
            } else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new ApiResponse(false, "Internal Server Error", null);
            }
            return new ApiResponse(false, "Unexpected Error", null);
        } catch (RestClientException ex) {
            return new ApiResponse(false, "Error in API request: " + ex.getMessage(), null);
        }
    }
}

