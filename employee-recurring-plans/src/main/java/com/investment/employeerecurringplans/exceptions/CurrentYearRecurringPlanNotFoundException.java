package com.investment.employeerecurringplans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONTINUE)
public class CurrentYearRecurringPlanNotFoundException extends  RuntimeException{
    public CurrentYearRecurringPlanNotFoundException(String message) {
        super(message);
    }
}
