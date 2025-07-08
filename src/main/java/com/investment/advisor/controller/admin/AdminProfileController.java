package com.investment.advisor.controller.admin;

import com.investment.advisor.api.admin.AdminProfileApi;
import com.investment.advisor.service.interfaces.AdminProfileService;
import com.investment.common.apiresponse.ApiResponse;
import com.investment.common.enums.InvestorSubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminProfileController implements AdminProfileApi {

    private final AdminProfileService approvalService;

    @PostMapping("/investor/{id}/approve")
    public ResponseEntity<ApiResponse<String>> approveInvestor(
            @PathVariable Long id,
            @RequestParam InvestorSubscriptionType subscription) {

        approvalService.approveInvestor(id, subscription);
        return ResponseEntity.ok(new ApiResponse<>("Investor approved with subscription: " + subscription, HttpStatus.ACCEPTED) );
    }

    @PostMapping("/investor/{id}/reject")
    public ResponseEntity<ApiResponse<String>> rejectInvestor(@PathVariable Long id) {
        approvalService.rejectInvestor(id);
        return ResponseEntity.ok(new ApiResponse<>("Investor rejected.", HttpStatus.ACCEPTED));
    }
}
