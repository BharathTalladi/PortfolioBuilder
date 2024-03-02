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

    @ExceptionHandler(RecurringPlanContributionException.class)
    public ResponseEntity<?> handleRecurringPlanContributionException(RecurringPlanContributionException exception, WebRequest request){
        ErrorResponse errorDetails =
                new ErrorResponse(
                        request.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserPlanDetailsNotFoundException.class)
    public ResponseEntity<?> handleUserPlanDetailsNotFoundException(UserPlanDetailsNotFoundException exception, WebRequest request){
        ErrorResponse errorDetails =
                new ErrorResponse(
                        request.getDescription(false),
                        HttpStatus.OK,
                        exception.getMessage(),
                        LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.OK
        );
    }

    // handling global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
        ErrorResponse errorDetails =
                new ErrorResponse(request.getDescription(false), HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}