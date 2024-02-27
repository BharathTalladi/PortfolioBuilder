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
    private String id;
    private Double employer_contribution_limit_401k;
    private Double employer_contribution_limit_HSA;
    private Double employer_contribution_limit_FSA;
    private Double employer_contribution_limit_ROTHIRA;
}
