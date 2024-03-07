package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalContributionAmount {
    // Message indicating the status or result of the total contribution calculation
    private String message;

    // Total contribution amount to 401K
    private Double total_contribution_amount_401k;

    // Total contribution amount to HSA
    private Double total_contribution_amount_HSA;

    // Total contribution amount to FSA
    private Double total_contribution_amount_FSA;

    // Total contribution amount to ROTH IRA
    private Double total_contribution_amount_ROTHIRA;
}
