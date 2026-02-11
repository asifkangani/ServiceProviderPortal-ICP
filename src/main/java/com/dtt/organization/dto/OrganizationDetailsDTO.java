package com.dtt.organization.dto;



import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

public class OrganizationDetailsDTO {


    @NotBlank(message = "organization name is mandatory")
    private String orgName;


    private String regNo;

    @NotBlank(message = "organization type is mandatory")
    private String orgType;


    private String taxNumber;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "organization email is mandatory")
    private String orgEmail;




    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
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

    @Override
    public String toString() {
        return "OrganizationDetailsDTO{" +
                "orgName='" + orgName + '\'' +
                ", regNo='" + regNo + '\'' +
                ", orgType='" + orgType + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", address='" + address + '\'' +
                ", orgEmail='" + orgEmail + '\'' +
                '}';
    }
}
