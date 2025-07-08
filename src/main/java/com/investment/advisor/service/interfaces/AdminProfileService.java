package com.investment.advisor.service.interfaces;


import com.investment.common.enums.InvestorSubscriptionType;

public interface AdminProfileService {
    void approveInvestor(Long investorId, InvestorSubscriptionType subscription);

    void rejectInvestor(Long investorId);
}
