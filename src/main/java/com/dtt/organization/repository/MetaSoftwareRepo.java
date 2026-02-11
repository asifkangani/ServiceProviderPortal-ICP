package com.dtt.organization.repository;

import com.dtt.organization.model.MetaSoftwareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaSoftwareRepo extends JpaRepository<MetaSoftwareEntity, Long> {
}
