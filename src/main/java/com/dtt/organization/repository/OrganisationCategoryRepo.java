package com.dtt.organization.repository;

import com.dtt.organization.model.OrganisationCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OrganisationCategoryRepo
        extends JpaRepository<OrganisationCategories, Integer> {

    Optional<OrganisationCategories> findByCategoryName(String categoryName);

    @Modifying
    @Transactional
    @Query("UPDATE OrganisationCategories c " +
            "SET c.labelName = :labelName, c.updatedOn = :updatedOn " +
            "WHERE c.id = :id")
    int updateLabelNameById(
            @Param("id") int id,
            @Param("labelName") String labelName,
            @Param("updatedOn") String updatedOn
    );
}