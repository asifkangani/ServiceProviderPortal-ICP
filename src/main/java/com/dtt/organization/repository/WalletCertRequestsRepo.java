package com.dtt.organization.repository;

import com.dtt.organization.model.WalletCertApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletCertRequestsRepo extends JpaRepository<WalletCertApprovalEntity,Long> {

    Optional<WalletCertApprovalEntity> findTopByOrgDetailsIdOrderByCreatedOnDesc(Long orgDetailsId);

    @Query("SELECT w FROM WalletCertApprovalEntity w WHERE w.orgDetailsId = :orgDetailsID")
    WalletCertApprovalEntity findByOrgDetailsID(@Param("orgDetailsID") Long orgDetailsID);

}
