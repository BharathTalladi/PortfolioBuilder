package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalContributionAmount {
    private String message;
    private Double total_contribution_amount_401k;
    private Double total_contribution_amount_HSA;
    private Double total_contribution_amount_FSA;
    private Double total_contribution_amount_ROTHIRA;
}
