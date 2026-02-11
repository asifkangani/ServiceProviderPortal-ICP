package com.dtt.organization.repository;


import com.dtt.organization.model.OrganizationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
    boolean existsByRegNo(String regNo);
    boolean existsByTaxNumber(String taxNumber);
    boolean existsByOrgNameIgnoreCase(String orgName);
    Page<OrganizationEntity> findByIdIn(List<Long> ids, Pageable pageable);

    @Query("SELECT o FROM OrganizationEntity o WHERE o.orgAddedByAdmin = false ORDER BY o.createdOn DESC")
    List<OrganizationEntity> findAllOrgs();

    @Query(" SELECT COUNT(o) FROM OrganizationEntity o JOIN SpocEntity s ON s.orgDetailsId = o.id WHERE s.spocOfficalEmail = :email AND o.status = 'ACTIVE' ")
    long countActiveOrganizationsBySpocEmail(@Param("email") String email);

    @Query(" SELECT COUNT(o) FROM OrganizationEntity o JOIN SpocEntity s ON s.orgDetailsId = o.id WHERE s.spocOfficalEmail = :email ")
    long countApplicationsBySpocEmail(@Param("email") String email);

//    @Query(" SELECT o FROM OrganizationEntity o JOIN SpocEntity s ON o.ouid IS NOT NULL AND s.spocOfficalEmail = :email ")
//    List<OrganizationEntity> OrganizationsBySpocEmail(@Param("email") String email);

    @Query(" SELECT o FROM OrganizationEntity o, SpocEntity s WHERE s.orgDetailsId = o.id AND s.spocOfficalEmail = :email AND o.ouid IS NOT NULL")
    List<OrganizationEntity> OrganizationsBySpocEmail(@Param("email") String email);



    @Query("SELECT o.ouid, o.status, o.orgType " +
            "FROM OrganizationEntity o " +
            "WHERE o.status NOT IN ('NOT_SUBMITTED_FOR_APPROVAL', 'PENDING', 'REJECTED')")
    List<Object[]> findAllApprovedOrganizations();


    @Query("SELECT o FROM OrganizationEntity o WHERE o.ouid = ?1")
    OrganizationEntity allFormByOrgUid(String orgUid);


    @Query(" SELECT o FROM OrganizationEntity o " +
            "JOIN SpocEntity s ON s.orgDetailsId = o.id " +
            "WHERE s.spocOfficalEmail = :email ")
    List<OrganizationEntity> getAllOrgsBySpocEmail(@Param("email") String email);


}

