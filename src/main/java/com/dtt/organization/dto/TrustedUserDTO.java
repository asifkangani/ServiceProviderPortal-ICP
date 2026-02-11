package com.dtt.organization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class TrustedUserDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(
            regexp = "^\\+[0-9]{12}$",
            message = "Mobile number must be in format +<countrycode><number> (e.g. +919100152939)"
    )
    private String mobileNumber;

    public TrustedUserDTO() {
    }

    public TrustedUserDTO(String name, String email, String mobileNumber) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "TrustedUserDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
