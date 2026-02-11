package com.dtt.organization.dto;


public class SoftwareWithLicenseDTO {



    private  Long softwareId;
    private  String softwareName;
    private  String softwareVersion;
    private  String sizeOfSoftware;
    private String orgName;
    private String ouid;
    private Long licenseId;
    private String licenceStatus;
    private String licenseType;
    private String validUpTo;

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getSizeOfSoftware() {
        return sizeOfSoftware;
    }

    public void setSizeOfSoftware(String sizeOfSoftware) {
        this.sizeOfSoftware = sizeOfSoftware;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }

    public Long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Long licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenceStatus() {
        return licenceStatus;
    }

    public void setLicenceStatus(String licenceStatus) {
        this.licenceStatus = licenceStatus;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getValidUpTo() {
        return validUpTo;
    }

    public void setValidUpTo(String validUpTo) {
        this.validUpTo = validUpTo;
    }

    @Override
    public String toString() {
        return "SoftwareWithLicenseDTO{" +
                "softwareId=" + softwareId +
                ", softwareName='" + softwareName + '\'' +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", sizeOfSoftware='" + sizeOfSoftware + '\'' +
                ", orgName='" + orgName + '\'' +
                ", ouid='" + ouid + '\'' +
                ", licenseId=" + licenseId +
                ", licenceStatus='" + licenceStatus + '\'' +
                ", licenseType='" + licenseType + '\'' +
                ", validUpTo='" + validUpTo + '\'' +
                '}';
    }
}
