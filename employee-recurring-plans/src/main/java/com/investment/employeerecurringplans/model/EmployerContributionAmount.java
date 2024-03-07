package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerContributionAmount {

    // Message indicating the status or additional information
    private String message;

    // Amount of employer contribution for 401k plan
    private Double employer_contribution_amount_401k;

    // Amount of employer contribution for HSA plan
    private Double employer_contribution_amount_HSA;

    // Amount of employer contribution for FSA plan
    private Double employer_contribution_amount_FSA;

    // Amount of employer contribution for ROTH IRA plan
    private Double employer_contribution_amount_ROTHIRA;
}
