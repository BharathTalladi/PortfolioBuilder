package com.investment.employeerecurringplans.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.investment.employeerecurringplans.util.CustomDateDeserializer;
import com.investment.employeerecurringplans.util.CustomYearDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER_RECURRING_PLAN_DETAILS")
public class UserRecurringPlanDetails {

    @Id
    @Column(name = "user_id")
    private String userId; // Unique identifier for the user recurring plan details, Employee ID or user id which is generated while registering for the application
    private Double salary; // Salary of the user
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dob; // Date of birth of the user
    @JsonDeserialize(using = CustomYearDeserializer.class)
    private Date year; // Year associated with the user's recurring plan
    private Double self_contribution_limit_401K; // Self-contribution limit for 401K
    private Double self_contribution_limit_HSA; // Self-contribution limit for HSA
    private Double self_contribution_limit_FSA; // Self-contribution limit for FSA
    private Double self_contribution_limit_ROTHIRA; // Self-contribution limit for ROTH IRA
    @OneToOne
    @JoinColumn(name = "employee_id")
    private EmployerContributionsToUser employerContributions; // Relationship mapping for employer contributions
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // Relationship mapping for user details

}