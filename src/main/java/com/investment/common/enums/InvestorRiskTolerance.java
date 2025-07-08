package com.investment.common.enums;

public enum InvestorRiskTolerance {

    LOW("Low risk tolerance, prefers stable investments with minimal volatility"),
    MEDIUM("Moderate risk tolerance, willing to accept some volatility for potential growth"),
    HIGH("High risk tolerance, seeks maximum returns and is comfortable with significant market fluctuations");

    private final String description;

    InvestorRiskTolerance(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
