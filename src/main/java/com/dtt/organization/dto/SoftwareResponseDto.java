package com.dtt.organization.dto;


public class SoftwareResponseDto {

    private Long softwareId;
    private String fileName;
    private String softwareVersion;
    private String downloadLink;
    private String installManual;
    private String softwareName;
    private String sizeOfSoftware;
    private String status;
    private String createdOn;
    private String updatedOn;
    private String publishedOn;

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getInstallManual() {
        return installManual;
    }

    public void setInstallManual(String installManual) {
        this.installManual = installManual;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getSizeOfSoftware() {
        return sizeOfSoftware;
    }

    public void setSizeOfSoftware(String sizeOfSoftware) {
        this.sizeOfSoftware = sizeOfSoftware;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }
// getters & setters
}
