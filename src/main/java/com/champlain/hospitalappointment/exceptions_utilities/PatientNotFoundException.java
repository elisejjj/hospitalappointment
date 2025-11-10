package com.champlain.hospitalappointment.exceptions_utilities;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
