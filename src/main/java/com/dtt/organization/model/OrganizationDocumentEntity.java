package com.dtt.organization.model;

import jakarta.persistence.*;

@Entity
@Table(name = "spp_di_documents")
public class OrganizationDocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "meta_document_id")
    private Long metaDocumentId;

    @Column(name = "document_name")
    private String documentName;

    @Lob
    @Column(name = "document_data")
    private String documentData;

    @Column(name = "content_type")
    private String contentType;


    @Column(name = "created_on")
    private String createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }



    public Long getMetaDocumentId() {
        return metaDocumentId;
    }

    public void setMetaDocumentId(Long metaDocumentId) {
        this.metaDocumentId = metaDocumentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentData() {
        return documentData;
    }

    public void setDocumentData(String documentData) {
        this.documentData = documentData;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "OrganizationDocumentEntity{" +
                "id=" + id +
                ", orgId=" + orgId +
                ", metaDocumentId=" + metaDocumentId +
                ", documentName='" + documentName + '\'' +
                ", documentData='" + documentData + '\'' +
                ", contentType='" + contentType + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}
