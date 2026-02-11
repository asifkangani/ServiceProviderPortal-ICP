package com.dtt.organization.model;
import jakarta.persistence.*;

@Entity
@Table(name = "spp_di_spoc_details")
public class SpocEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "org_details_id")
    private Long orgDetailsId;


    @Column(name = "spoc_name")
    private String spocName;

    @Column(name = "spoc_official_email")
    private String spocOfficalEmail;

    @Column(name = "spoc_document_number")
    private String spocDocumentNumber;

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

    public String getSpocDocumentNumber() {
        return spocDocumentNumber;
    }

    public void setSpocDocumentNumber(String spocDocumentNumber) {
        this.spocDocumentNumber = spocDocumentNumber;
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
        return "SpocEntity{" +
                "id=" + id +
                ", orgDetailsId=" + orgDetailsId +
                ", spocName='" + spocName + '\'' +
                ", spocOfficalEmail='" + spocOfficalEmail + '\'' +
                ", spocDocumentNumber='" + spocDocumentNumber + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", updatedOn='" + updatedOn + '\'' +
                '}';
    }
}

