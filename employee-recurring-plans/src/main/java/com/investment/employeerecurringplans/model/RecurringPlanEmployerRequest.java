package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecurringPlanEmployerRequest {
    // The ID of the user to whom the employer contributions apply
    private String id;

    // The limit for employer contributions to the 401(k) account
    private Double employer_contribution_limit_401k;

    // The limit for employer contributions to the HSA (Health Savings Account)
    private Double employer_contribution_limit_HSA;

    // The limit for employer contributions to the FSA (Flexible Spending Account)
    private Double employer_contribution_limit_FSA;

    // The limit for employer contributions to the ROTH IRA account
    private Double employer_contribution_limit_ROTHIRA;
}
