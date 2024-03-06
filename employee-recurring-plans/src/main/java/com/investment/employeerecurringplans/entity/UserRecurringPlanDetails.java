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
    private String userId;
    private Double salary;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dob;
    @JsonDeserialize(using = CustomYearDeserializer.class)
    private Date year;
    private Double self_contribution_limit_401K;
    private Double self_contribution_limit_HSA;
    private Double self_contribution_limit_FSA;
    private Double self_contribution_limit_ROTHIRA;
    @OneToOne
    @JoinColumn(name = "employee_id")
    private EmployerContributionsToUser employerContributions;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}