package com.investment.advisor.entity;

import com.investment.common.convertor.StringListConverter;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "income")
    private Double income;

    @Column(name = "savings")
    private Double savings;

    @Column(name = "risk_tolerance")
    private String riskTolerance; // e.g. "Low", "Medium", "High"

    @Convert(converter = StringListConverter.class)
    @Column(name = "investment_goal")
    private List<String> investmentGoal; // e.g. "Retirement", "Wealth Building", etc.

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

