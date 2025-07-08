package com.investment.common.enums;

public enum InvestorSubscriptionType {

        SILVER("Basic portfolio analysis"),
        GOLD("Includes SILVER + Monthly report"),
        PLATINUM("Includes GOLD + Personalized recommendations"),
        DIAMOND("All PLATINUM + Dedicated advisor support");

        private final String description;

    InvestorSubscriptionType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

}
