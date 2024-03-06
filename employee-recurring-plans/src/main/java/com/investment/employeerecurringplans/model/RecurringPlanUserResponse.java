package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringPlanUserResponse {
    private int age;
    private double salary;
    private double salaryAfterContributions;
    private int year;
    private SelfContributionAmount selfContributionAmount;
}
