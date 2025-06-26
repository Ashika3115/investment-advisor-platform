package com.investment.advisor.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestorProfileDTO {
    private Long id;
    private String name;
    private Integer age;
    private Double income;
    private Double savings;
    private String riskTolerance;
    private List<String> investmentGoal;
    private String createdAt;

}
