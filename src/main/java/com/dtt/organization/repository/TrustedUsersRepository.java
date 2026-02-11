package com.dtt.organization.repository;


import com.dtt.organization.model.TrustedUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrustedUsersRepository extends JpaRepository<TrustedUsersEntity, Long> {

    TrustedUsersEntity findByEmail(String email);
}

