package com.bogdan.exception;

public class EmployeeCreateException extends RuntimeException {
    public EmployeeCreateException() {
        super();
    }

    public EmployeeCreateException(String message) {
        super(message);
    }
}
