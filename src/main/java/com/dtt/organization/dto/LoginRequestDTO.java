package com.dtt.organization.dto;

public class LoginRequestDTO {
    private String email;
    private String password;
    private Long organizationId;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", organizationId=" + organizationId +
                '}';
    }
}
