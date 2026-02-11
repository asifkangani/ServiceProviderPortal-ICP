package com.dtt.organization.dto;

import jakarta.validation.constraints.Email;

public class AuditorDetailsDTO {

    private String auditorName;
    private String auditorDocumentNumber;

    @Email(message = "Invalid auditor email")
    private String auditorOfficalEmail;

    // getters & setters


    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public String getAuditorDocumentNumber() {
        return auditorDocumentNumber;
    }

    public void setAuditorDocumentNumber(String auditorDocumentNumber) {
        this.auditorDocumentNumber = auditorDocumentNumber;
    }

    public String getAuditorOfficalEmail() {
        return auditorOfficalEmail;
    }

    public void setAuditorOfficalEmail(String auditorOfficalEmail) {
        this.auditorOfficalEmail = auditorOfficalEmail;
    }

    @Override
    public String toString() {
        return "AuditorDetailsDTO{" +

                ", auditorName='" + auditorName + '\'' +
                ", auditorDocumentNumber='" + auditorDocumentNumber + '\'' +
                ", auditorOfficalEmail='" + auditorOfficalEmail + '\'' +
                '}';
    }
}
