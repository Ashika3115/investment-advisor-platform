package com.investment.advisor.api;

import com.investment.common.apiresponse.ApiResponse;
import com.investment.advisor.entity.InvestorProfile;
import com.investment.advisor.request.InvestorProfileDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Investor Profile API", description = "Operations for managing investor profiles")
@RequestMapping("/api/investors")
public interface InvestorProfileApi {

    @Operation(summary = "Create a new investor profile")
    @PostMapping
    ResponseEntity<ApiResponse<InvestorProfileDTO>> createProfile(@RequestBody InvestorProfileDTO request);

    @Operation(summary = "Retrieve all investor profiles")
    @GetMapping
    ResponseEntity<ApiResponse<List<InvestorProfileDTO>>> getAllProfiles();

    @Operation(summary = "Get investor profile by ID")
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<InvestorProfileDTO>>getProfileById(@PathVariable Long id);

    @Operation(summary = "Update an existing investor profile")
    @PatchMapping("/{id}")
    ResponseEntity<ApiResponse<InvestorProfileDTO>> updateProfile(@PathVariable Long id, @RequestBody InvestorProfile profile);

    @Operation(summary = "Delete an investor profile")
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Void>> deleteProfile(@PathVariable Long id);
}
