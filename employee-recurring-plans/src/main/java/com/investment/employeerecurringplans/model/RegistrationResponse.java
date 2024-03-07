package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegistrationResponse {
    // User ID
    private String id;

    // Message indicating the result of the registration process
    private String message;
}
