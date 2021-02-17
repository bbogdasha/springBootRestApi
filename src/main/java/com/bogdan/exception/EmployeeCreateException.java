package com.bogdan.exception;

public class EmployeeCreateException extends RuntimeException {
    public EmployeeCreateException(String message) {
        super(message);
    }

    public EmployeeCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
