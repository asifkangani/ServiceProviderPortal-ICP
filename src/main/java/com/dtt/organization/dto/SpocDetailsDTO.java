package com.dtt.organization.dto;

import jakarta.validation.constraints.*;

public class SpocDetailsDTO {


    @NotBlank(message = "SPOC name is mandatory")
    private String spocName;

    @NotBlank(message = "SPOC email is mandatory")
    @Email(message = "Invalid SPOC email")
    private String spocOfficalEmail;

    @NotBlank(message = "SPOC document number is mandatory")
    private String spocDocumentNumber;

    // getters & setters


    public String getSpocName() {
        return spocName;
    }

    public void setSpocName(String spocName) {
        this.spocName = spocName;
    }

    public String getSpocOfficalEmail() {
        return spocOfficalEmail;
    }

    public void setSpocOfficalEmail(String spocOfficalEmail) {
        this.spocOfficalEmail = spocOfficalEmail;
    }

    public String getSpocDocumentNumber() {
        return spocDocumentNumber;
    }

    public void setSpocDocumentNumber(String spocDocumentNumber) {
        this.spocDocumentNumber = spocDocumentNumber;
    }

    @Override
    public String toString() {
        return "SpocDetailsDTO{" +
                ", spocName='" + spocName + '\'' +
                ", spocOfficalEmail='" + spocOfficalEmail + '\'' +
                ", spocDocumentNumber='" + spocDocumentNumber + '\'' +
                '}';
    }
}

