package com.dtt.organization.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * The Class RegisterOrganizationDTO.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterOrganizationDTO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    private int organizationId;

    private String organizationUid;

    private String organizationName;

    private String organizationEmail;

    private String eSealImage;

    private String authorizedLetterForSignatories;

    private String tax;

    private String otherLegalDocument;

    private String otherESealDocument;

    private String uniqueRegdNo;

    private String taxNo;

    private int segment;

    private String corporateOfficeAddress;

    private String incorporation;

    private String signedPdf;

    private String createdBy;

    private String updatedBy;

    private String createdOn;

    private String updatedOn;

    private String status;

    private String emailDomain;

    private boolean domainStatus;

    private String agentUrl;

//	private List<OrgUser> orgUserList;

    private List<String> directorsEmailList;

    private List<String> serviceList;

    private List<String> documentListCheckbox;

    private boolean enablePostPaidOption;

    private String spocUgpassEmail;

    private List<Integer> templateId;

    private boolean manageByAdmin;
    private boolean walletCertificateStatus;

    public boolean isWalletCertificateStatus() {
        return walletCertificateStatus;
    }

    public void setWalletCertificateStatus(boolean walletCertificateStatus) {
        this.walletCertificateStatus = walletCertificateStatus;
    }

    public boolean isManageByAdmin() {
        return manageByAdmin;
    }

    public void setManageByAdmin(boolean manageByAdmin) {
        this.manageByAdmin = manageByAdmin;
    }

    public boolean isDomainStatus() {
        return domainStatus;
    }

    public void setDomainStatus(boolean domainStatus) {
        this.domainStatus = domainStatus;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationUid() {
        return organizationUid;
    }

    public void setOrganizationUid(String organizationUid) {
        this.organizationUid = organizationUid;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationEmail() {
        return organizationEmail;
    }

    public void setOrganizationEmail(String organizationEmail) {
        this.organizationEmail = organizationEmail;
    }

    public String geteSealImage() {
        return eSealImage;
    }

    public void seteSealImage(String eSealImage) {
        this.eSealImage = eSealImage;
    }

    public String getAuthorizedLetterForSignatories() {
        return authorizedLetterForSignatories;
    }

    public void setAuthorizedLetterForSignatories(String authorizedLetterForSignatories) {
        this.authorizedLetterForSignatories = authorizedLetterForSignatories;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getOtherLegalDocument() {
        return otherLegalDocument;
    }

    public void setOtherLegalDocument(String otherLegalDocument) {
        this.otherLegalDocument = otherLegalDocument;
    }

    public String getOtherESealDocument() {
        return otherESealDocument;
    }

    public void setOtherESealDocument(String otherESealDocument) {
        this.otherESealDocument = otherESealDocument;
    }

    public String getUniqueRegdNo() {
        return uniqueRegdNo;
    }

    public void setUniqueRegdNo(String uniqueRegdNo) {
        this.uniqueRegdNo = uniqueRegdNo;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public int getSegment() {
        return segment;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }

    public String getCorporateOfficeAddress() {
        return corporateOfficeAddress;
    }

    public void setCorporateOfficeAddress(String corporateOfficeAddress) {
        this.corporateOfficeAddress = corporateOfficeAddress;
    }

    public String getIncorporation() {
        return incorporation;
    }

    public void setIncorporation(String incorporation) {
        this.incorporation = incorporation;
    }

    public String getSignedPdf() {
        return signedPdf;
    }

    public void setSignedPdf(String signedPdf) {
        this.signedPdf = signedPdf;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//	public List<OrgUser> getOrgUserList() {
//		return orgUserList;
//	}
//
//	public void setOrgUserList(List<OrgUser> orgUserList) {
//		this.orgUserList = orgUserList;
//	}

    public List<String> getDirectorsEmailList() {
        return directorsEmailList;
    }

    public void setDirectorsEmailList(List<String> directorsEmailList) {
        this.directorsEmailList = directorsEmailList;
    }

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }

    public List<String> getDocumentListCheckbox() {
        return documentListCheckbox;
    }

    public void setDocumentListCheckbox(List<String> documentListCheckbox) {
        this.documentListCheckbox = documentListCheckbox;
    }

    public boolean isEnablePostPaidOption() {
        return enablePostPaidOption;
    }

    public void setEnablePostPaidOption(boolean enablePostPaidOption) {
        this.enablePostPaidOption = enablePostPaidOption;
    }

    public String getSpocUgpassEmail() {
        return spocUgpassEmail;
    }

    public void setSpocUgpassEmail(String spocUgpassEmail) {
        this.spocUgpassEmail = spocUgpassEmail;
    }

    public List<Integer> getTemplateId() {
        return templateId;
    }

    public void setTemplateId(List<Integer> templateId) {
        this.templateId = templateId;
    }

    public String getAgentUrl() {
        return agentUrl;
    }

    public void setAgentUrl(String agentUrl) {
        this.agentUrl = agentUrl;
    }

    @Override
    public String toString() {
        return "RegisterOrganizationDTO{" +
                "organizationId=" + organizationId +
                ", organizationUid='" + organizationUid + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", organizationEmail='" + organizationEmail + '\'' +
                ", eSealImage='" + eSealImage + '\'' +
                ", authorizedLetterForSignatories='" + authorizedLetterForSignatories + '\'' +
                ", tax='" + tax + '\'' +
                ", otherLegalDocument='" + otherLegalDocument + '\'' +
                ", otherESealDocument='" + otherESealDocument + '\'' +
                ", uniqueRegdNo='" + uniqueRegdNo + '\'' +
                ", taxNo='" + taxNo + '\'' +
                ", segment=" + segment +
                ", corporateOfficeAddress='" + corporateOfficeAddress + '\'' +
                ", incorporation='" + incorporation + '\'' +
                ", signedPdf='" + signedPdf + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", updatedOn='" + updatedOn + '\'' +
                ", status='" + status + '\'' +
                ", emailDomain='" + emailDomain + '\'' +
                ", domainStatus=" + domainStatus +
                ", agentUrl='" + agentUrl + '\'' +
                ", directorsEmailList=" + directorsEmailList +
                ", serviceList=" + serviceList +
                ", documentListCheckbox=" + documentListCheckbox +
                ", enablePostPaidOption=" + enablePostPaidOption +
                ", spocUgpassEmail='" + spocUgpassEmail + '\'' +
                ", templateId=" + templateId +
                ", manageByAdmin=" + manageByAdmin +
                '}';
    }
}