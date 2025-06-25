package com.investment.advisor.service;

import com.investment.advisor.entity.InvestorProfile;

import java.util.List;
import java.util.Optional;

/**
 * ✅ Service interface for business logic on Investor Profiles.
 * ✅ Used to keep controller decoupled from DB/repo logic.
 */
public interface InvestorProfileService {

    InvestorProfile saveProfile(InvestorProfile profile);

    Optional<InvestorProfile> getProfileById(Long id);

    List<InvestorProfile> getAllProfiles();

    InvestorProfile updateProfile(Long id, InvestorProfile updatedProfile);

    void deleteProfile(Long id);
}
