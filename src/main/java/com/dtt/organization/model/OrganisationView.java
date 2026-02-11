package com.dtt.organization.model;





import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import jakarta.persistence.*;



@Entity
@Table(name = "OrganizationsView")

public class OrganisationView implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "organization_details_id")
    private int organizationDetailsId;


    @Column(name = "ouid")
    private String ouid;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "organization_email")
    private String organizationEmail;

    //    @Lob
    @Column(name = "e_seal_image")
    private String eSealImage;


    @Column(name = "authorized_letter_for_signatories")
    private String authorizedLetterForSignatories;

    @Column(name = "unique_regd_no")
    private String uniqueRegdNo;

    @Column(name = "tax_no")
    private String taxNo;

    @Column(name = "corporate_office_address")
    private String corporateOfficeAddress;


    @Column(name = "incorporation_file")
    private String incorporationFile;


    @Column(name = "tax_file")
    private String taxFile;


    @Column(name = "other_legal_document")
    private String otherLegalDocument;


    @Column(name = "other_eseal_document")
    private String otherEsealDocument;

    @Column(name = "organization_status")
    private String organizationStatus;

    @Column(name = "created_on")
    private String createdDate;

    @Column(name = "updated_on")
    private String updatedDate;

    @Column(name = "enable_post_paid_option")
    private Boolean enablePostPaidOption;

    @Column(name = "spoc_ugpass_email")
    private String spocUgpassEmail;

    @Column(name = "certificate_issue_date")
    private Date certificateIssueDate;

    @Column(name = "certificate_expiry_date")
    private Date certificateExpiryDate;

    @Column(name = "certificate_status")
    private String certificateStatus;

    @Column(name = "e_seal_created_date")
    private String eSealCreatedDate;

    @Column(name = "wallet_certificate_issue_date")
    private Date waletCertificateIssueDate;

    @Column(name = "wallet_certificate_expiry_date")
    private Date walletCertificateExpiryDate;



    public int getOrganizationDetailsId() {
        return organizationDetailsId;
    }

    public void setOrganizationDetailsId(int organizationDetailsId) {
        this.organizationDetailsId = organizationDetailsId;
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

    public Date getWaletCertificateIssueDate() {
        return waletCertificateIssueDate;
    }

    public void setWaletCertificateIssueDate(Date waletCertificateIssueDate) {
        this.waletCertificateIssueDate = waletCertificateIssueDate;
    }

    public Date getWalletCertificateExpiryDate() {
        return walletCertificateExpiryDate;
    }

    public void setWalletCertificateExpiryDate(Date walletCertificateExpiryDate) {
        this.walletCertificateExpiryDate = walletCertificateExpiryDate;
    }

    public String getCorporateOfficeAddress() {
        return corporateOfficeAddress;
    }

    public void setCorporateOfficeAddress(String corporateOfficeAddress) {
        this.corporateOfficeAddress = corporateOfficeAddress;
    }

    public String getIncorporationFile() {
        return incorporationFile;
    }

    public void setIncorporationFile(String incorporationFile) {
        this.incorporationFile = incorporationFile;
    }

    public String getTaxFile() {
        return taxFile;
    }

    public void setTaxFile(String taxFile) {
        this.taxFile = taxFile;
    }

    public String getOtherLegalDocument() {
        return otherLegalDocument;
    }

    public void setOtherLegalDocument(String otherLegalDocument) {
        this.otherLegalDocument = otherLegalDocument;
    }

    public String getOtherEsealDocument() {
        return otherEsealDocument;
    }

    public void setOtherEsealDocument(String otherEsealDocument) {
        this.otherEsealDocument = otherEsealDocument;
    }

    public String getOrganizationStatus() {
        return organizationStatus;
    }

    public void setOrganizationStatus(String organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    public Boolean getEnablePostPaidOption() {
        return enablePostPaidOption;
    }

    public void setEnablePostPaidOption(Boolean enablePostPaidOption) {
        this.enablePostPaidOption = enablePostPaidOption;
    }

    public String getSpocUgpassEmail() {
        return spocUgpassEmail;
    }

    public void setSpocUgpassEmail(String spocUgpassEmail) {
        this.spocUgpassEmail = spocUgpassEmail;
    }

    public Date getCertificateIssueDate() {
        return certificateIssueDate;
    }

    public void setCertificateIssueDate(Date certificateIssueDate) {
        this.certificateIssueDate = certificateIssueDate;
    }

    public Date getCertificateExpiryDate() {
        return certificateExpiryDate;
    }

    public void setCertificateExpiryDate(Date certificateExpiryDate) {
        this.certificateExpiryDate = certificateExpiryDate;
    }

    public String getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(String certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String geteSealCreatedDate() {
        return eSealCreatedDate;
    }

    public void seteSealCreatedDate(String eSealCreatedDate) {
        this.eSealCreatedDate = eSealCreatedDate;
    }

    @Override
    public String toString() {
        return "OrganisationView{" +
                "organizationDetailsId=" + organizationDetailsId +
                ", ouid='" + ouid + '\'' +
                ", orgName='" + orgName + '\'' +
                ", organizationEmail='" + organizationEmail + '\'' +
                ", eSealImage='" + eSealImage + '\'' +
                ", authorizedLetterForSignatories='" + authorizedLetterForSignatories + '\'' +
                ", uniqueRegdNo='" + uniqueRegdNo + '\'' +
                ", taxNo='" + taxNo + '\'' +
                ", corporateOfficeAddress='" + corporateOfficeAddress + '\'' +
                ", incorporationFile='" + incorporationFile + '\'' +
                ", taxFile='" + taxFile + '\'' +
                ", otherLegalDocument='" + otherLegalDocument + '\'' +
                ", otherEsealDocument='" + otherEsealDocument + '\'' +
                ", organizationStatus='" + organizationStatus + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", enablePostPaidOption=" + enablePostPaidOption +
                ", spocUgpassEmail='" + spocUgpassEmail + '\'' +
                ", certificateIssueDate=" + certificateIssueDate +
                ", certificateExpiryDate=" + certificateExpiryDate +
                ", certificateStatus='" + certificateStatus + '\'' +
                ", eSealCreatedDate='" + eSealCreatedDate + '\'' +
                ", waletCertificateIssueDate=" + waletCertificateIssueDate +
                ", walletCertificateExpiryDate=" + walletCertificateExpiryDate +
                '}';
    }
}










