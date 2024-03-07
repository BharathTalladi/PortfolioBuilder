package com.investment.employeerecurringplans.exceptions;

import com.investment.employeerecurringplans.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Exception handler for RecurringPlanContributionException
    @ExceptionHandler(RecurringPlanContributionException.class)
    public ResponseEntity<?> handleRecurringPlanContributionException(RecurringPlanContributionException exception, WebRequest request){
        ErrorResponse errorDetails =
                new ErrorResponse(
                        request.getDescription(false), // Description of the error request
                        HttpStatus.BAD_REQUEST, // HTTP status code
                        exception.getMessage(), // Error message from the exception
                        LocalDateTime.now()); // Timestamp of the error
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND); // Returning error response with HTTP status code
    }

    // Exception handler for UserPlanDetailsNotFoundException
    @ExceptionHandler(UserPlanDetailsNotFoundException.class)
    public ResponseEntity<?> handleUserPlanDetailsNotFoundException(UserPlanDetailsNotFoundException exception, WebRequest request){
        ErrorResponse errorDetails =
                new ErrorResponse(
                        request.getDescription(false), // Description of the error request
                        HttpStatus.OK, // HTTP status code
                        exception.getMessage(), // Error message from the exception
                        LocalDateTime.now()); // Timestamp of the error
        return new ResponseEntity<>(errorDetails, HttpStatus.OK); // Returning error response with HTTP status code
    }

    // Exception handler for CurrentYearRecurringPlanNotFoundException
    @ExceptionHandler(CurrentYearRecurringPlanNotFoundException.class)
    public ResponseEntity<?> handleCurrentYearRecurringPlanNotFoundException(CurrentYearRecurringPlanNotFoundException exception, WebRequest request){
        ErrorResponse errorDetails =
                new ErrorResponse(
                        request.getDescription(false), // Description of the error request
                        HttpStatus.CONTINUE, // HTTP status code
                        exception.getMessage(), // Error message from the exception
                        LocalDateTime.now()); // Timestamp of the error
        return new ResponseEntity<>(errorDetails, HttpStatus.OK); // Returning error response with HTTP status code
    }

    // Exception handler for other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
        ErrorResponse errorDetails =
                new ErrorResponse(
                        request.getDescription(false), // Description of the error request
                        HttpStatus.BAD_REQUEST, // HTTP status code
                        exception.getMessage(), // Error message from the exception
                        LocalDateTime.now()); // Timestamp of the error
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR); // Returning error response with HTTP status code
    }
}
