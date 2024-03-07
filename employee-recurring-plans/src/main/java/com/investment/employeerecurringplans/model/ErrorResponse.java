package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    // The path of the API where the error occurred
    private String apiPath;

    // The HTTP status code indicating the type of error
    private HttpStatus errorCode;

    // A message describing the error in detail
    private String errorMessage;

    // The timestamp when the error occurred
    private LocalDateTime errorTime;

}
