package com.investment.advisor.service.impl;
import com.investment.advisor.entity.InvestorProfile;
import com.investment.advisor.repository.InvestorProfileRepository;
import com.investment.advisor.service.interfaces.AdminProfileService;
import com.investment.common.enums.InvestorApprovalStatus;
import com.investment.common.enums.InvestorSubscriptionType;
import com.investment.common.exception.custom.InvestorProfileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminApprovalServiceImpl implements AdminProfileService {

    private final InvestorProfileRepository investorRepository;

    @Override
    public void approveInvestor(Long investorId, InvestorSubscriptionType subscription) {
        InvestorProfile investor = getInvestorProfile(investorId);

        investor.setApprovalStatus(InvestorApprovalStatus.APPROVED);
        investor.setSubscriptionType(subscription);
        investorRepository.save(investor);
    }

    @Override
    public void rejectInvestor(Long investorId) {
        InvestorProfile investor = getInvestorProfile(investorId);
        investor.setApprovalStatus(InvestorApprovalStatus.REJECTED);
        investorRepository.save(investor);
    }

    private InvestorProfile getInvestorProfile(Long investorId) {
        return investorRepository.findById(investorId)
                .orElseThrow(() -> new InvestorProfileNotFoundException("Investor not found"));
    }
}

