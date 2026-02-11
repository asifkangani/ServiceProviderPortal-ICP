package com.dtt.organization.controller;

import com.dtt.organization.dto.LoginRequestDTO;
import com.dtt.organization.dto.TrustedUserDTO;
import com.dtt.organization.service.iface.TrustedUserService;
import com.dtt.organization.util.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/public/spoc")
public class TrustedUserController {
    private static final String CLASS = "TrustedUserController";
    private static final Logger logger = LoggerFactory.getLogger(TrustedUserController.class);
    @Autowired
    private TrustedUserService trustedUserService;


    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequestDTO dto) {
        logger.info("Login request by email={}", dto.getEmail());
        return trustedUserService.login(dto);
    }

//    @PostMapping("/forgot-password")
//    public ApiResponse forgotPassword(@RequestParam String  email) {
//        logger.info("Forgot password request by email={}", email);
//        return trustedUserService.forgotPassword(email);
//    }
//
//    @PostMapping("/reset-password")
//    public ApiResponse resetPassword(@RequestParam String token, @RequestParam String newPassword) {
//        logger.info("Reset password request received");
//        return  trustedUserService.resetPassword(token,newPassword);
//    }
//
//    @PostMapping("/change-password")
//    public ApiResponse changePassword(Principal principal, @RequestParam String currentPassword, @RequestParam String newPassword) {
//
//        logger.info("Change password request received" +
//                        " | username={}",
//                principal.getName()
//        );
//        return trustedUserService.changePassword(principal.getName(), currentPassword, newPassword);
//    }

    @PostMapping("/save")
    public ApiResponse saveTrustedUser(@Valid @RequestBody TrustedUserDTO dto) {
        logger.info("{} save trusted user",CLASS);
      return trustedUserService.saveTrustedUser(dto);
    }

    @GetMapping("/get/all")
    public ApiResponse getAll() {
        logger.info("{} get All trusted users ",CLASS);
        return trustedUserService.getAllTrustedUsers();
    }












}
