package com.dtt.organization.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Configuration
public class APIRequestHandler {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${security.allowed-hosts}")
    private List<String> allowedHosts;

    public ApiResponse handleApiRequest(
            String url,
            HttpMethod method,
            HttpEntity<Object> requestEntity) {

        try {
            validateUrl(url);

            ResponseEntity<ApiResponse> response =
                    restTemplate.exchange(url, method, requestEntity, ApiResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }

            return new ApiResponse(false, "API call failed", null);

        } catch (IllegalArgumentException e) {
            return new ApiResponse(false, e.getMessage(), null);
        } catch (RestClientException e) {
            return new ApiResponse(false, "Error in API request", null);
        }
    }

    private void validateUrl(String url) {
        try {
            URI uri = new URI(url);

            // 1. Allow only http / https
            if (uri.getScheme() == null ||
                    (!"http".equalsIgnoreCase(uri.getScheme())
                            && !"https".equalsIgnoreCase(uri.getScheme()))) {
                throw new IllegalArgumentException("Invalid URL scheme");
            }

            // 2. Allow only configured hosts
            String host = uri.getHost();
            if (host == null || !allowedHosts.contains(host)) {
                throw new IllegalArgumentException("Host not allowed");
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or unsafe URL");
        }
    }
}