package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringPlanUserResponse {
    // Age of the user
    private int age;

    // Salary of the user
    private double salary;

    // Salary after contributions
    private double salaryAfterContributions;

    // Year of the recurring plan
    private int year;

    // Self contribution amount details
    private SelfContributionAmount selfContributionAmount;
}
