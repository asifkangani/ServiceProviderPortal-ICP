package com.dtt.organization.repository;

import com.dtt.organization.model.SpocEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpocRepository extends JpaRepository<SpocEntity, Long> {


    Optional<SpocEntity> findByOrgDetailsId(Long orgDetailsId);

    List<SpocEntity> findAllBySpocOfficalEmail(String spocOfficalEmail);

    boolean existsBySpocOfficalEmailAndOrgDetailsId(
            String spocOfficalEmail,
            Long orgDetailsId
    );

    Optional<SpocEntity>
    findTopBySpocOfficalEmailOrderByCreatedOnDesc(String spocOfficalEmail);


}