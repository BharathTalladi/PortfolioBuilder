package com.investment.employeerecurringplans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Indicates that this exception should result in a BAD_REQUEST HTTP response
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RecurringPlanContributionException extends RuntimeException {

    // Constructor with a message parameter
    public RecurringPlanContributionException(String message) {
        super(message); // Passes the message to the superclass constructor
    }
}
