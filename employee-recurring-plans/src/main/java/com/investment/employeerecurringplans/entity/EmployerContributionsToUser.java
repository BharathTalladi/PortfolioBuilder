package com.investment.employeerecurringplans.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="EMPLOYER_CONTRIBUTION_TO_USER")
public class EmployerContributionsToUser {
    @Id
    @Column(name="employee_id")
    private String employeeId;
    private Double employer_contribution_limit_401K;
    private Double employer_contribution_limit_HSA;
    private Double employer_contribution_limit_FSA;
    private Double employer_contribution_limit_ROTHIRA;
    @OneToOne(mappedBy = "employerContributions")
    private UserRecurringPlanDetails userRecurringPlanDetails;
}
