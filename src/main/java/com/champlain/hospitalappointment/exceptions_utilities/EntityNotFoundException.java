package com.champlain.hospitalappointment.exceptions_utilities;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
