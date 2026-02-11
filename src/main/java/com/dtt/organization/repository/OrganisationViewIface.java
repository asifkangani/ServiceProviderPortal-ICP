package com.dtt.organization.repository;


import com.dtt.organization.model.OrganisationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OrganisationViewIface extends JpaRepository<OrganisationView, Integer> {
    @Query("SELECT o FROM OrganisationView o WHERE o.ouid = ?1")
    List<OrganisationView> getAllDetailsByOuid(String ouid);

    @Query("SELECT o FROM OrganisationView o WHERE o.ouid = ?1")
    OrganisationView getESealDetails(String ouid);

    @Query("SELECT o FROM OrganisationView o WHERE o.spocUgpassEmail = ?1")
    List<OrganisationView> getDetailsByEmail(String email);

}
