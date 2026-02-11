package com.dtt.organization.repository;

import com.dtt.organization.model.OrganisationCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrganisationCategoryRepo
        extends JpaRepository<OrganisationCategories, Integer> {

    Optional<OrganisationCategories> findByCategoryName(String categoryName);
}