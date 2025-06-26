package com.investment.advisor.request;

import java.util.List;

public record InvestorProfileDTO(
    Long id,
    String name,
    Integer age,
    Double income,
    Double savings,
    String riskTolerance,
    List<String> investmentGoal,
    String createdAt
) {}