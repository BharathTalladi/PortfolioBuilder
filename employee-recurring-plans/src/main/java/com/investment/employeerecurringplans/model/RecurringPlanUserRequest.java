package com.investment.employeerecurringplans.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.investment.employeerecurringplans.util.CustomDateDeserializer;
import com.investment.employeerecurringplans.util.CustomYearDeserializer;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecurringPlanUserRequest {
    // Unique identifier of the user
    private String id;

    // Salary of the user
    private Double salary;

    // Date of birth of the user
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dob;

    // Year of the recurring plan
    @JsonDeserialize(using = CustomYearDeserializer.class)
    private Date year;

    // Maximum contribution limit for 401K
    private Double self_contribution_limit_401K;

    // Maximum contribution limit for HSA
    private Double self_contribution_limit_HSA;

    // Maximum contribution limit for FSA
    private Double self_contribution_limit_FSA;

    // Maximum contribution limit for ROTH IRA
    private Double self_contribution_limit_ROTHIRA;
}
