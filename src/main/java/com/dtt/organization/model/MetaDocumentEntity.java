package com.dtt.organization.model;

import jakarta.persistence.*;

@Entity
@Table(name = "spp_di_meta_documents")
public class MetaDocumentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_label")
    private String documentLabel;

    @Column(name = "document_type")
    private String documentType;// comma separated extensions

    @Column(name = "document_size_kb")
    private Long documentSizeKb;

    @Column(name = "is_mandatory")
    private boolean isMandatory;

    @Column(name = "document_name")
    private String documentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentLabel() {
        return documentLabel;
    }

    public void setDocumentLabel(String documentLabel) {
        this.documentLabel = documentLabel;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Long getDocumentSizeKb() {
        return documentSizeKb;
    }

    public void setDocumentSizeKb(Long documentSizeKb) {
        this.documentSizeKb = documentSizeKb;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public String toString() {
        return "MetaDocumentEntity{" +
                "id=" + id +
                ", documentLabel='" + documentLabel + '\'' +
                ", documentType='" + documentType + '\'' +
                ", documentSizeKb=" + documentSizeKb +
                ", isMandatory=" + isMandatory +
                ", documentName='" + documentName + '\'' +
                '}';
    }
}