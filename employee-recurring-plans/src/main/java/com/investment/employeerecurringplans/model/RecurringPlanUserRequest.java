package com.investment.employeerecurringplans.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.investment.employeerecurringplans.util.CustomDateDeserializer;
import com.investment.employeerecurringplans.util.CustomYearDeserializer;
import lombok.*;

import java.util.Date;
import java.util.Optional;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecurringPlanUserRequest {

    private String id;
    private Double salary;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dob;
    @JsonDeserialize(using = CustomYearDeserializer.class)
    private Date year;
    private Double self_contribution_limit_401K;
    private Double self_contribution_limit_HSA;
    private Double self_contribution_limit_FSA;
    private Double self_contribution_limit_ROTHIRA;

}
