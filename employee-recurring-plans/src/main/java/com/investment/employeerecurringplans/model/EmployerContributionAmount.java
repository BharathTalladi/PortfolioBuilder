package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerContributionAmount {
    private String message;
    private Double employer_contribution_amount_401k;
    private Double employer_contribution_amount_HSA;
    private Double employer_contribution_amount_FSA;
    private Double employer_contribution_amount_ROTHIRA;
}
