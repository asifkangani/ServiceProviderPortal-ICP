package com.dtt.organization.dto;

public class ApplyLicenseDTO {


        private String id;
        private String ouid;
        private String appid;
        private String createdOn;
        private String updatedOn;
        private String licenseInfo;
        private String issuedOn;
        private String validUpto;
        private String licenseType;
        private String applicationType;
        private String privateKey;
        private String clientAssertionType;
        private String clientId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOuid() {
            return ouid;
        }

        public void setOuid(String ouid) {
            this.ouid = ouid;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
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

        public String getLicenseInfo() {
            return licenseInfo;
        }

        public void setLicenseInfo(String licenseInfo) {
            this.licenseInfo = licenseInfo;
        }

        public String getIssuedOn() {
            return issuedOn;
        }

        public void setIssuedOn(String issuedOn) {
            this.issuedOn = issuedOn;
        }

        public String getValidUpto() {
            return validUpto;
        }

        public void setValidUpto(String validUpto) {
            this.validUpto = validUpto;
        }

        public String getLicenseType() {
            return licenseType;
        }

        public void setLicenseType(String licenseType) {
            this.licenseType = licenseType;
        }

        public String getApplicationType() {
            return applicationType;
        }

        public void setApplicationType(String applicationType) {
            this.applicationType = applicationType;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getClientAssertionType() {
            return clientAssertionType;
        }

        public void setClientAssertionType(String clientAssertionType) {
            this.clientAssertionType = clientAssertionType;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        @Override
        public String toString() {
            return "DownloadLicenseDto{" +
                    "id='" + id + '\'' +
                    ", ouid='" + ouid + '\'' +
                    ", appid='" + appid + '\'' +
                    ", createdOn='" + createdOn + '\'' +
                    ", updatedOn='" + updatedOn + '\'' +
                    ", licenseInfo='" + licenseInfo + '\'' +
                    ", issuedOn='" + issuedOn + '\'' +
                    ", validUpto='" + validUpto + '\'' +
                    ", licenseType='" + licenseType + '\'' +
                    ", applicationType='" + applicationType + '\'' +
                    ", privateKey='" + privateKey + '\'' +
                    ", clientAssertionType='" + clientAssertionType + '\'' +
                    ", clientId='" + clientId + '\'' +
                    '}';
        }


}
