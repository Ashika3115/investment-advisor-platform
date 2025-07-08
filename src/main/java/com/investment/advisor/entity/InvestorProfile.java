package com.investment.advisor.entity;

import com.investment.common.convertor.StringListConverter;
import com.investment.common.enums.InvestorApprovalStatus;
import com.investment.common.enums.InvestorSubscriptionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "aadhaar_number", unique = true, nullable = false, length = 12)
    @Pattern(regexp = "\\d{12}", message = "Aadhaar must be a 12-digit number")
    private String aadhaarNumber;

    @Column(name = "income")
    private Double income;

    @Column(name = "savings")
    private Double savings;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_tolerance")
    private String riskTolerance; // e.g. "Low", "Medium", "High"

    @Convert(converter = StringListConverter.class)
    @Column(name = "investment_goal")
    private List<String> investmentGoal; // e.g. "Retirement", "Wealth Building", etc.

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private InvestorApprovalStatus approvalStatus = InvestorApprovalStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type")
    private InvestorSubscriptionType subscriptionType;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

