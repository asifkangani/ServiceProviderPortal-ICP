package com.dtt.organization.model;

import jakarta.persistence.*;

@Entity
@Table(name = "spp_di_wallet_certificate_requests")
public class WalletCertApprovalEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "org_name")
        private String orgName;

        @Column(name = "org_details_id")
        private Long orgDetailsId;

        @Column(name = "spoc_name")
        private String spocName;

        @Column(name = "payment_transaction_no")
        private String paymentTransactionNo;

        @Column(name = "status")
        private String status;

        @Column(name="created_on")
        private String createdOn;

        @Column(name="updated_on")
        private String updatedOn;

    public Long getOrgDetailsId() {
        return orgDetailsId;
    }

    public void setOrgDetailsId(Long orgDetailsId) {
        this.orgDetailsId = orgDetailsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSpocName() {
        return spocName;
    }

    public void setSpocName(String spocName) {
        this.spocName = spocName;
    }

    public String getPaymentTransactionNo() {
        return paymentTransactionNo;
    }

    public void setPaymentTransactionNo(String paymentTransactionNo) {
        this.paymentTransactionNo = paymentTransactionNo;
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

    @Override
    public String toString() {
        return "WalletCertApprovalEntity{" +
                "id=" + id +
                ", orgName='" + orgName + '\'' +
                ", orgDetailsId='" + orgDetailsId + '\'' +
                ", spocName='" + spocName + '\'' +
                ", paymentTransactionNo='" + paymentTransactionNo + '\'' +
                ", status='" + status + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", updatedOn='" + updatedOn + '\'' +
                '}';
    }
}
