package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelfContributionAmount {
    // Amount of self-contribution to 401K
    private Double self_contribution_amount_401K;

    // Amount of self-contribution to HSA
    private Double self_contribution_amount_HSA;

    // Amount of self-contribution to FSA
    private Double self_contribution_amount_FSA;

    // Amount of self-contribution to ROTH IRA
    private Double self_contribution_amount_ROTHIRA;
}
