package com.bogdan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHand extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeCreateException.class)
    public ResponseEntity<Map<String, String>> exception(EmployeeCreateException exception) {
        return errorCreateEmployee(exception.getMessage());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> exception(EmployeeNotFoundException exception) {
        return notFoundEmployee(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception exception) {
        Map<String, String> response = prepareResponse(
                exception.getMessage(),
                "Something went wrong. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR.toString()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, String>> errorCreateEmployee(String message) {
        Map<String, String> response = prepareResponse(
                message,
                "Please enter valid data: Name, Email(unique), Salary.",
                HttpStatus.METHOD_NOT_ALLOWED.toString()
        );
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<Map<String, String>> notFoundEmployee(String message) {
        Map<String, String> response = prepareResponse(
                message,
                "Please make sure the user you are looking for (id, name, email) exists.",
                HttpStatus.NOT_FOUND.toString()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private Map<String, String> prepareResponse(String error, String solution, String status) {
        Map<String, String> response = new HashMap<>();
        response.put("Cause", error);
        response.put("Solution", solution);
        response.put("Status", status);
        return response;
    }
}
