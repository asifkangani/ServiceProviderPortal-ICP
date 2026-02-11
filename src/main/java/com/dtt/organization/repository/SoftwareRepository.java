package com.dtt.organization.repository;


import com.dtt.organization.model.SoftwareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoftwareRepository extends JpaRepository<SoftwareEntity, Long> {
    List<SoftwareEntity> findByStatusIgnoreCase(String status);
    boolean existsBySoftwareNameIgnoreCaseAndSoftwareVersionIgnoreCase(
            String softwareName,
            String softwareVersion
    );
    boolean existsByStatusIgnoreCase(String status);
}
