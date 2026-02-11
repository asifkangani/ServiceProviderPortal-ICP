package com.dtt.organization.dto;


public class OrganizationRecentDTO {

    private String orgName;
    private String orgType;
    private String status;
    private String createdOn;
    private String taxNumber;
    private String regNo;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
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

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    @Override
    public String toString() {
        return "OrganizationRecentDTO{" +
                "orgName='" + orgName + '\'' +
                ", orgType='" + orgType + '\'' +
                ", status='" + status + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", regNo='" + regNo + '\'' +
                '}';
    }
}
