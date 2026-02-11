package com.dtt.organization.model;

import jakarta.persistence.*;


@Entity
    @Table(name = "spp_di_softwares")
    public class SoftwareEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "software_id")
        private Long softwareId;

        @Column(name = "file_name")
        private String fileName;

        @Column(name = "software_version")
        private String softwareVersion;

        @Column(name = "download_link")
        private String downloadLink;

        @Column(name = "install_manual")
        private String installManual;

        @Column(name = "software_name")
        private String softwareName;

        @Column(name = "size_of_software")
        private String sizeOfSoftware;

        @Column(name = "status")
        private String status;

        @Column(name = "created_on")
        private String createdOn;

        @Column(name = "updated_on")
        private String updatedOn;

        @Column(name = "published_on")
        private String publishedOn;

        public Long getSoftwareId() {
            return softwareId;
        }

        public void setSoftwareId(Long softwareId) {
            this.softwareId = softwareId;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getSoftwareVersion() {
            return softwareVersion;
        }

        public void setSoftwareVersion(String softwareVersion) {
            this.softwareVersion = softwareVersion;
        }

        public String getDownloadLink() {
            return downloadLink;
        }

        public void setDownloadLink(String downloadLink) {
            this.downloadLink = downloadLink;
        }

        public String getInstallManual() {
            return installManual;
        }

        public void setInstallManual(String installManual) {
            this.installManual = installManual;
        }

        public String getSoftwareName() {
            return softwareName;
        }

        public void setSoftwareName(String softwareName) {
            this.softwareName = softwareName;
        }

        public String getSizeOfSoftware() {
            return sizeOfSoftware;
        }

        public void setSizeOfSoftware(String sizeOfSoftware) {
            this.sizeOfSoftware = sizeOfSoftware;
        }

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

        public String getPublishedOn() {
            return publishedOn;
        }

        public void setPublishedOn(String publishedOn) {
            this.publishedOn = publishedOn;
        }

        @Override
        public String toString() {
            return "SoftwareEntity{" +
                    "softwareId=" + softwareId +
                    ", softwareName='" + softwareName + '\'' +
                    ", softwareVersion='" + softwareVersion + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

