package com.investment.advisor.service.impl;

import com.investment.advisor.entity.InvestorProfile;
import com.investment.advisor.mapper.InvestorProfileMapper;
import com.investment.advisor.repository.InvestorProfileRepository;
import com.investment.advisor.service.InvestorProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ✅ Java 17 service implementation with optional handling and clean logic.
 * ✅ Delegates database work to the repository layer.
 */

@Service
public class InvestorProfileServiceImpl implements InvestorProfileService {

    private final InvestorProfileRepository repository;
    private final InvestorProfileMapper mapper;

    @Autowired
    public InvestorProfileServiceImpl(InvestorProfileRepository repository, InvestorProfileMapper mapper, InvestorProfileMapper mapper1) {
        this.repository = repository;
        this.mapper = mapper1;
    }

    @Override
    public InvestorProfile saveProfile(InvestorProfile profile) {
        return repository.save(profile);
    }

    @Override
    public Optional<InvestorProfile> getProfileById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<InvestorProfile> getAllProfiles() {
        return repository.findAll();
    }

    @Override
    public InvestorProfile updateProfile(Long id, InvestorProfile updatedProfile) {
        return repository.findById(id).map(existing -> {
            mapper.updateInvestorProfile(existing, updatedProfile);
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("InvestorProfile not found with ID: " + id));
    }

    @Override
    public void deleteProfile(Long id) {
        repository.deleteById(id);
    }
}
