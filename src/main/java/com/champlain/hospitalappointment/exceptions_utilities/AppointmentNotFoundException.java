package com.champlain.hospitalappointment.exceptions_utilities;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
