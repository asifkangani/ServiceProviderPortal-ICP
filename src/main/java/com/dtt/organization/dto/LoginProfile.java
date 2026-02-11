package com.dtt.organization.dto;

import java.io.Serializable;

public class LoginProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    public String Email ;
    public String OrgnizationId;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getOrgnizationId() {
        return OrgnizationId;
    }

    public void setOrgnizationId(String orgnizationId) {
        OrgnizationId = orgnizationId;
    }

    @Override
    public String toString() {
        return "LoginProfile{" +
                "Email='" + Email + '\'' +
                ", OrgnizationId='" + OrgnizationId + '\'' +
                '}';
    }
}



