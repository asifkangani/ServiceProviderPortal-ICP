package com.dtt.organization.dto;


public class WalletCertResponseDto {

    private String status;
    private String certificateIssueDate;
    private String certificateExpiryDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCertificateIssueDate() {
        return certificateIssueDate;
    }

    public void setCertificateIssueDate(String certificateIssueDate) {
        this.certificateIssueDate = certificateIssueDate;
    }

    public String getCertificateExpiryDate() {
        return certificateExpiryDate;
    }

    public void setCertificateExpiryDate(String certificateExpiryDate) {
        this.certificateExpiryDate = certificateExpiryDate;
    }
}
