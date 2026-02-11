package com.dtt.organization.repository;

import com.dtt.organization.model.AuditorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditorRepository extends JpaRepository<AuditorEntity, Long> {
    Optional<AuditorEntity> findByOrgDetailsId(Long orgDetailsId);
}
