package com.investment.employeerecurringplans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONTINUE)
public class RecurringPlanLimitsException extends  RuntimeException{
    // Constructor to initialize the exception with a custom message
    public RecurringPlanLimitsException(String message) {
        super(message);
    }
}
