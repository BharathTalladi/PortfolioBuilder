package com.investment.employeerecurringplans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Define the custom exception and specify the HTTP status code
@ResponseStatus(value= HttpStatus.CONTINUE)
public class CurrentYearRecurringPlanNotFoundException extends  RuntimeException{

    // Constructor to initialize the exception with a custom message
    public CurrentYearRecurringPlanNotFoundException(String message) {
        super(message);
    }
}
