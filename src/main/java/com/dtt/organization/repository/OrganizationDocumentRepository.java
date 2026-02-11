package com.dtt.organization.repository;


import com.dtt.organization.model.OrganizationDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationDocumentRepository extends JpaRepository<OrganizationDocumentEntity, Long> {

    List<OrganizationDocumentEntity> findByOrgId(Long orgId);
    @Query("SELECT d FROM OrganizationDocumentEntity d WHERE d.orgId = :orgId AND d.metaDocumentId = :metaDocumentId")
    OrganizationDocumentEntity findByOrgIdAndMetaDocumentId(@Param("orgId") Long orgId, @Param("metaDocumentId") Long metaDocumentId);
}
