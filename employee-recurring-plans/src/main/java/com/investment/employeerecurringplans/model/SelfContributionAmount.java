package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelfContributionAmount {
    private Double self_contribution_amount_401K;
    private Double self_contribution_amount_HSA;
    private Double self_contribution_amount_FSA;
    private Double self_contribution_amount_ROTHIRA;
}
