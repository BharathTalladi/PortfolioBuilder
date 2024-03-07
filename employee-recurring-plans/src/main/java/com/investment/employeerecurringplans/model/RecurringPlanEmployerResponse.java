package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringPlanEmployerResponse {
    // Object containing employer contribution amounts
    private EmployerContributionAmount employerContributionAmount;
}
