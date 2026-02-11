package com.dtt.organization.dto;



public class GetAllOrganizationtDTO {

    private Long orgDetailsId;
    private String orgName;
    private String orgNo;
    private String regNo;
    private String orgType;
    private String taxNumber;
    private String status;
    private String spocName;
    private String spocOfficalEmail;
    private String address;
    private String orgEmail;

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public Long getOrgDetailsId() {
        return orgDetailsId;
    }

    public void setOrgDetailsId(Long orgDetailsId) {
        this.orgDetailsId = orgDetailsId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    @Override
    public String toString() {
        return "GetAllOrganizationtDTO{" +
                "orgDetailsId=" + orgDetailsId +
                ", orgName='" + orgName + '\'' +
                ", orgNo='" + orgNo + '\'' +
                ", regNo='" + regNo + '\'' +
                ", orgType='" + orgType + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", status='" + status + '\'' +
                ", spocName='" + spocName + '\'' +
                ", spocOfficalEmail='" + spocOfficalEmail + '\'' +
                ", address='" + address + '\'' +
                ", orgEmail='" + orgEmail + '\'' +
                '}';
    }
}
