package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringPlanResponse {
    // Unique identifier of the user
    private String id;
    // Name of the user
    private String name;
    // Age of the user
    private int age;
    // Year of the recurring plan
    private int year;
    // Original salary of the user
    private double salary;
    // Salary after deductions and contributions
    private double salaryAfterContributions;
    // Object containing details of self-contributed amounts
    private SelfContributionAmount selfContributionAmount;
    // Object containing details of employer-contributed amounts
    private EmployerContributionAmount employerContributionAmount;
    // Object containing total contribution amounts
    private TotalContributionAmount totalContributionAmount;
}
