package com.investment.employeerecurringplans.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class UserDetailsNotFoundException extends  RuntimeException{
    public UserDetailsNotFoundException(String message) {
        super(message);
    }
}
