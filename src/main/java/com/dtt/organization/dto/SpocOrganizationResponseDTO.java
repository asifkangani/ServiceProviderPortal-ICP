package com.dtt.organization.dto;

public class SpocOrganizationResponseDTO {

    private Long organizationId;
    private String organizationName;
    private String regNo;
    private String status;

    private String createdOn;

    private String orgType;

    // getters & setters


    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
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

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Override
    public String toString() {
        return "SpocOrganizationResponseDTO{" +
                "organizationId=" + organizationId +
                ", organizationName='" + organizationName + '\'' +
                ", regNo='" + regNo + '\'' +
                ", status='" + status + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", orgType='" + orgType + '\'' +
                '}';
    }
}
