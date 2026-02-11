package com.dtt.organization.controller;




import com.dtt.organization.security.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {
    @Value("${portal.name}")
    private String portalName;

    @ModelAttribute
    public void addGlobalAttributes(Authentication authentication,
                                    HttpServletRequest request,
                                    org.springframework.ui.Model model) {


        if (authentication != null
                && authentication.getPrincipal() instanceof CustomUserDetails) {

            CustomUserDetails user =
                    (CustomUserDetails) authentication.getPrincipal();

            model.addAttribute("userName", user.getFullName());
            model.addAttribute("userEmail", user.getEmail());
        }


        String uri = request.getRequestURI();
        model.addAttribute("portalName",portalName);

        List<String> orgPages = List.of("/organizations", "/organization-details", "/available-softwares");

        if (uri.contains("/dashboard")) {
            model.addAttribute("activePage", "dashboard");
        }
        else if (orgPages.stream().anyMatch(uri::contains)) {
            model.addAttribute("activePage", "organizations");
        }
        if (uri.contains("/create-organization")) {
            model.addAttribute("activePage", "create-organization");
        }
        else if (uri.contains("/profile")) {
            model.addAttribute("activePage", "profile");
        }
    }
}

