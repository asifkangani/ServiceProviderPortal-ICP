package com.dtt.organization.model;
import jakarta.persistence.*;
@Entity
@Table(name = "spp_di_auditor_details")
public class AuditorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "org_details_id")
    private Long orgDetailsId;

    @Column(name = "auditor_name")
    private String auditorName;

    @Column(name = "auditor_document_number")
    private String auditorDocumentNumber;

    @Column(name = "auditor_official_email")
    private String auditorOfficialEmail;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_on")
    private String updatedOn;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgDetailsId() {
        return orgDetailsId;
    }

    public void setOrgDetailsId(Long orgDetailsId) {
        this.orgDetailsId = orgDetailsId;
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

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "AuditorEntity{" +
                "id=" + id +
                ", orgDetailsId=" + orgDetailsId +
                ", auditorName='" + auditorName + '\'' +
                ", auditorDocumentNumber='" + auditorDocumentNumber + '\'' +
                ", auditorOfficialEmail='" + auditorOfficialEmail + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", updatedOn='" + updatedOn + '\'' +
                '}';
    }
}
