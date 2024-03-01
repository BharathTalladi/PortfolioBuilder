package com.investment.employeerecurringplans.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringPlanResponse {
    private String id;
    private int age;
    private double salaryAfterContributions;
    private SelfContributionAmount selfContributionAmount;
    private EmployerContributionAmount employerContributionAmount;
    private TotalContributionAmount totalContributionAmount;
}
