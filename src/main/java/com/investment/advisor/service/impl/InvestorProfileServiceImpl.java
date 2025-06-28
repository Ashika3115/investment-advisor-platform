package com.investment.advisor.service.impl;

import com.investment.advisor.entity.InvestorProfile;
import com.investment.advisor.mapper.InvestorProfileMapper;
import com.investment.advisor.repository.InvestorProfileRepository;
import com.investment.advisor.service.interfaces.InvestorProfileService;
import com.investment.common.exception.custom.InvestorAlreadyExistsException;
import com.investment.common.exception.custom.InvestorDetailsIncompleteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ✅ Java 17 service implementation with optional handling and clean logic.
 * ✅ Delegates database work to the repository layer.
 */

@Service
@RequiredArgsConstructor
public class InvestorProfileServiceImpl implements InvestorProfileService {

    private final InvestorProfileRepository repository;
    private final InvestorProfileMapper mapper;

    public InvestorProfile saveProfile(InvestorProfile profile) {
        if(profile.getAadhaarNumber() == null || profile.getAadhaarNumber().isEmpty()) {
            throw new InvestorDetailsIncompleteException("Please provide Aadhar Number for further record.");
        }
        Optional<InvestorProfile> existing = repository.findByAadhaarNumber(profile.getAadhaarNumber());
        if (existing.isPresent()) {
            throw new InvestorAlreadyExistsException("Investor with Aadhaar " + profile.getAadhaarNumber() + " already exists.");
        }
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
