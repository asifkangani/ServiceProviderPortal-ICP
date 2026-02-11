package com.dtt.organization.dto;

public class SoftwareLicenseDTO {

    private int id;
    private String ouid;
    private String appid;
    private String createdDateTime;
    private String updatedDateTime;
    private String licenseInfo;
    private String issuedOn;
    private String validUpTo;
    private String licenseType;
    private String lastActivated;
    private String firstActivated;
    private String licenceStatus;
    private String applicationName;
    private String organizationName;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getOuid() {
        return ouid;
    }
    public void setOuid(String ouid) {
        this.ouid = ouid;
    }
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getCreatedDateTime() {
        return createdDateTime;
    }
    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
    public String getUpdatedDateTime() {
        return updatedDateTime;
    }
    public void setUpdatedDateTime(String updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }
    public String getLicenseInfo() {
        return licenseInfo;
    }
    public void setLicenseInfo(String licenseInfo) {
        this.licenseInfo = licenseInfo;
    }
    public String getIssuedOn() {
        return issuedOn;
    }
    public void setIssuedOn(String issuedOn) {
        this.issuedOn = issuedOn;
    }
    public String getValidUpTo() {
        return validUpTo;
    }
    public void setValidUpTo(String validUpTo) {
        this.validUpTo = validUpTo;
    }
    public String getLicenseType() {
        return licenseType;
    }
    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }
    public String getLastActivated() {
        return lastActivated;
    }
    public void setLastActivated(String lastActivated) {
        this.lastActivated = lastActivated;
    }
    public String getFirstActivated() {
        return firstActivated;
    }
    public void setFirstActivated(String firstActivated) {
        this.firstActivated = firstActivated;
    }
    public String getLicenceStatus() {
        return licenceStatus;
    }
    public void setLicenceStatus(String licenceStatus) {
        this.licenceStatus = licenceStatus;
    }
    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    public String getOrganizationName() {
        return organizationName;
    }
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    @Override
    public String toString() {
        return "SoftwareLicenses [id=" + id + ", ouid=" + ouid + ", appid=" + appid + ", createdDateTime="
                + createdDateTime + ", updatedDateTime=" + updatedDateTime + ", licenseInfo=" + licenseInfo
                + ", issuedOn=" + issuedOn + ", validUpTo=" + validUpTo + ", licenseType=" + licenseType
                + ", lastActivated=" + lastActivated + ", firstActivated=" + firstActivated + ", licenceStatus="
                + licenceStatus + ", applicationName=" + applicationName + ", organizationName=" + organizationName
                + "]";
    }



}
