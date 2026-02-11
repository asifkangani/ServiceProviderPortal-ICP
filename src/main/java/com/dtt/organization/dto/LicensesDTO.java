package com.dtt.organization.dto;

public class LicensesDTO {
    private String licenseId;
    private String softwareName;
    private String licenseKey;
    private String status;
    private String expiryDate;

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "LicensesDTO{" +
                "licenseId='" + licenseId + '\'' +
                ", softwareName='" + softwareName + '\'' +
                ", licenseKey='" + licenseKey + '\'' +
                ", status='" + status + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
