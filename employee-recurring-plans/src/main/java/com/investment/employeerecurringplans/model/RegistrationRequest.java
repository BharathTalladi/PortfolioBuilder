package com.investment.employeerecurringplans.model;

import com.investment.employeerecurringplans.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String name;
    private String emailId;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role roles;
}
