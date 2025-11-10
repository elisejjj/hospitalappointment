package com.champlain.hospitalappointment.exceptions_utilities;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
