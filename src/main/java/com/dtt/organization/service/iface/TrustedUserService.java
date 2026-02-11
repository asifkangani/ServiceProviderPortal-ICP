package com.dtt.organization.service.iface;

import com.dtt.organization.dto.LoginRequestDTO;
import com.dtt.organization.dto.TrustedUserDTO;
import com.dtt.organization.util.ApiResponse;

public interface TrustedUserService {



    ApiResponse login(LoginRequestDTO dto);

//    ApiResponse  forgotPassword(String email);

//    ApiResponse resetPassword(String token,String password);

//    String validateResetToken(String token);

//    ApiResponse changePassword(String email,String currentPassword,String newPassword);

    ApiResponse saveTrustedUser(TrustedUserDTO trustedUserDTO);
    ApiResponse getAllTrustedUsers();
}
