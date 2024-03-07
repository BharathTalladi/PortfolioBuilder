package com.investment.employeerecurringplans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Indicates that this exception should result in an OK HTTP response
@ResponseStatus(value = HttpStatus.OK)
public class UserPlanDetailsNotFoundException extends RuntimeException {

    // Constructor with a message parameter
    public UserPlanDetailsNotFoundException(String message) {
        super(message); // Passes the message to the superclass constructor
    }
}
