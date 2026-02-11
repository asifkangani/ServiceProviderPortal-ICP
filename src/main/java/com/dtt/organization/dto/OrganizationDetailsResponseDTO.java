package com.dtt.organization.dto;


import java.util.List;

public class OrganizationDetailsResponseDTO {

    private String ouid;
    private Long orgDetailsId;
    private String orgName;
    private String orgNo;
    private String taxNumber;
    private String orgType;
    private String regNo;

    private String orgEmail;
    private String address;
    private String status;

    private String spocName;
    private String spocOfficialEmail;
    private String spocDocumentNumber;

    private String auditorName;
    private String auditorDocumentNumber;
    private String auditorOfficialEmail;

    private String createdOn;
    private List<DocumentResponseDTO> documents;
    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
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

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getSpocOfficialEmail() {
        return spocOfficialEmail;
    }

    public void setSpocOfficialEmail(String spocOfficialEmail) {
        this.spocOfficialEmail = spocOfficialEmail;
    }

    public String getSpocDocumentNumber() {
        return spocDocumentNumber;
    }

    public void setSpocDocumentNumber(String spocDocumentNumber) {
        this.spocDocumentNumber = spocDocumentNumber;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public String getAuditorDocumentNumber() {
        return auditorDocumentNumber;
    }

    public void setAuditorDocumentNumber(String auditorDocumentNumber) {
        this.auditorDocumentNumber = auditorDocumentNumber;
    }

    public String getAuditorOfficialEmail() {
        return auditorOfficialEmail;
    }

    public void setAuditorOfficialEmail(String auditorOfficialEmail) {
        this.auditorOfficialEmail = auditorOfficialEmail;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public List<DocumentResponseDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentResponseDTO> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "OrganizationDetailsResponseDTO{" +
                "ouid='" + ouid + '\'' +
                ", orgDetailsId=" + orgDetailsId +
                ", orgName='" + orgName + '\'' +
                ", orgNo='" + orgNo + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", orgType='" + orgType + '\'' +
                ", regNo='" + regNo + '\'' +
                ", orgEmail='" + orgEmail + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", spocName='" + spocName + '\'' +
                ", spocOfficialEmail='" + spocOfficialEmail + '\'' +
                ", spocDocumentNumber='" + spocDocumentNumber + '\'' +
                ", auditorName='" + auditorName + '\'' +
                ", auditorDocumentNumber='" + auditorDocumentNumber + '\'' +
                ", auditorOfficialEmail='" + auditorOfficialEmail + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", documents=" + documents +
                '}';
    }
}
