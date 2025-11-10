package com.champlain.hospitalappointment.exceptions_utilities;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
