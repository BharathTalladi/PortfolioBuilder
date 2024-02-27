package com.investment.employeerecurringplans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RecurringPlanContributionException extends RuntimeException{
    public RecurringPlanContributionException(String message) {
        super(message);
    }
}
