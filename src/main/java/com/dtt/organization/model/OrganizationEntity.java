package com.dtt.organization.model;

import jakarta.persistence.*;

@Entity
@Table(name = "spp_di_organization_details")
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name = "ouid")
    private String ouid;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "reg_no")
    private String regNo;

    @Column(name = "org_type")
    private String orgType;

    @Column(name = "tax_number")
    private String taxNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "org_email")
    private String orgEmail;

//    @Column(name = "spoc_authorization_letter")
//    private String spocAuthorizationLetter;
//
//    @Column(name = "organization_letter")
//    private String organizationLetter;

    @Column(name = "status")
    private String status;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_on")
    private String updatedOn;

    @Column(name = "org_added_by_admin")
    private boolean orgAddedByAdmin;

    public boolean getOrgAddedByAdmin() {
        return orgAddedByAdmin;
    }

    public void setOrgAddedByAdmin(boolean orgAddedByAdmin) {
        this.orgAddedByAdmin = orgAddedByAdmin;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }

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




//    public String getSpocAuthorizationLetter() {
//        return spocAuthorizationLetter;
//    }
//
//    public void setSpocAuthorizationLetter(String spocAuthorizationLetter) {
//        this.spocAuthorizationLetter = spocAuthorizationLetter;
//    }
//
//    public String getOrganizationLetter() {
//        return organizationLetter;
//    }
//
//    public void setOrganizationLetter(String organizationLetter) {
//        this.organizationLetter = organizationLetter;
//    }

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

    @Override
    public String toString() {
        return "OrganizationEntity{" +
                "id=" + id +
                ", ouid='" + ouid + '\'' +
                ", orgName='" + orgName + '\'' +
                ", regNo='" + regNo + '\'' +
                ", orgType='" + orgType + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", address='" + address + '\'' +
                ", orgEmail='" + orgEmail + '\'' +
                ", status='" + status + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", updatedOn='" + updatedOn + '\'' +
                ", orgAddedByAdmin='" + orgAddedByAdmin + '\'' +
                '}';
    }
}

