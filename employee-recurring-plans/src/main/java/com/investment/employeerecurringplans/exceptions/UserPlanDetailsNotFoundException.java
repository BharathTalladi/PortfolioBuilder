package com.investment.employeerecurringplans.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class UserPlanDetailsNotFoundException extends  RuntimeException{
    public UserPlanDetailsNotFoundException(String message) {
        super(message);
    }
}
