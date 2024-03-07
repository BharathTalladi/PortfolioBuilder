package com.investment.employeerecurringplans.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.investment.employeerecurringplans.util.CustomYearDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="EMPLOYER_CONTRIBUTION_TO_USER")
public class EmployerContributionsToUser {
    @Id
    @Column(name="employee_id")
    private String employeeId; // Unique identifier for the employee's contribution details.
    @JsonDeserialize(using = CustomYearDeserializer.class)
    private Date year; // Year associated with the employer contributions
    private Double employer_contribution_limit_401K; // Employer contribution limit for 401K
    private Double employer_contribution_limit_HSA; // Employer contribution limit for HSA
    private Double employer_contribution_limit_FSA; // Employer contribution limit for FSA
    private Double employer_contribution_limit_ROTHIRA; // Employer contribution limit for ROTH IRA
    @OneToOne(mappedBy = "employerContributions")
    private UserRecurringPlanDetails userRecurringPlanDetails; // Relationship mapping with user recurring plan details
}
