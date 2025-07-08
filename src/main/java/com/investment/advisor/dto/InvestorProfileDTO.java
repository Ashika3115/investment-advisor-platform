package com.investment.advisor.dto;

import java.util.List;

public record InvestorProfileDTO(
    Long id,
    String name,
    Integer age,
    String aadhaarNumber,
    Double income,
    Double savings,
    String riskTolerance,
    List<String> investmentGoal,
    String approvalStatus,
    String subscriptionType,
    String createdAt
) {}