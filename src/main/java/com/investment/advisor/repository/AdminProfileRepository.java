package com.investment.advisor.repository;

import com.investment.advisor.entity.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminProfileRepository extends JpaRepository<AdminProfile, Long> {
    Optional<AdminProfile> findByAdminEmail(String email);

    Optional<AdminProfile> findByAdminUsername(String username);
}
