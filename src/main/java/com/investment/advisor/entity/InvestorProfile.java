package com.investment.advisor.entity;

import jakarta.persistence.*;
        import lombok.*;

        import java.time.LocalDateTime;

/**
 * ✅ Java 17 + JPA entity for storing investor profile details.
 * ✅ Will be used for risk profiling and personalized recommendation generation.
 */
@Entity
@Table(name = "investor_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private double income;

    private double savings;

    @Column(name = "risk_tolerance")
    private String riskTolerance; // e.g. "Low", "Medium", "High"

    @Column(name = "investment_goal")
    private String investmentGoal; // e.g. "Retirement", "Wealth Building", etc.

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

