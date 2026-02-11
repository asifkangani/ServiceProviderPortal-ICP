package com.dtt.organization.dto;

public class DocumentResponseDTO {

    private String documentName;
    private String documentType;
    private String documentData;

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentData() {
        return documentData;
    }

    public void setDocumentData(String documentData) {
        this.documentData = documentData;
    }

    @Override
    public String toString() {
        return "DocumentResponseDTO{" +
                "documentLabel='" + documentName + '\'' +
                ", documentType='" + documentType + '\'' +
                ", documentData='" + documentData + '\'' +
                '}';
    }
}
