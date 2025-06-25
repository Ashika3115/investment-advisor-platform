package com.investment.advisor.controller;

import com.investment.advisor.api.InvestorProfileApi;
import com.investment.advisor.entity.InvestorProfile;
import com.investment.advisor.service.InvestorProfileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InvestorProfileApiController implements InvestorProfileApi {

    private final InvestorProfileService service;

    public InvestorProfileApiController(InvestorProfileService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<InvestorProfile> createProfile(InvestorProfile profile) {
        return ResponseEntity.ok(service.saveProfile(profile));
    }

    @Override
    public ResponseEntity<List<InvestorProfile>> getAllProfiles() {
        return ResponseEntity.ok(service.getAllProfiles());
    }

    @Override
    public ResponseEntity<InvestorProfile> getProfileById(Long id) {
        return service.getProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<InvestorProfile> updateProfile(Long id, InvestorProfile profile) {
        return ResponseEntity.ok(service.updateProfile(id, profile));
    }

    @Override
    public ResponseEntity<Void> deleteProfile(Long id) {
        service.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
