package com.dtt.organization.dto;

public class WalletIssueDTO {


        private String organizationUid;
        private boolean isPostPaid;
        private String transactionReferenceId;

        public String getOrganizationUid() {
            return organizationUid;
        }

        public void setOrganizationUid(String organizationUid) {
            this.organizationUid = organizationUid;
        }

        public boolean isPostPaid() {
            return isPostPaid;
        }

        public void setPostPaid(boolean isPostPaid) {
            this.isPostPaid = isPostPaid;
        }

        public String getTransactionReferenceId() {
            return transactionReferenceId;
        }

        public void setTransactionReferenceId(String transactionReferenceId) {
            this.transactionReferenceId = transactionReferenceId;
        }

        @Override
        public String toString() {
            return "OrganizationIssueCetificatesDto [organizationUid=" + organizationUid + ", isPostPaid=" + isPostPaid
                    + ", transactionReferenceId=" + transactionReferenceId + "]";
        }

    }



