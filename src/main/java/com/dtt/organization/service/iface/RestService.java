package com.dtt.organization.service.iface;


import com.dtt.organization.dto.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


@Service
public interface RestService {
    UserInfo getUserInfo(String code, HttpServletRequest request);
    String generateJWTWithRsa(Boolean isAuthorizedUrl, String state, String nonce);
}
