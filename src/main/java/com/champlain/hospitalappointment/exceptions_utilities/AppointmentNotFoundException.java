package com.champlain.hospitalappointment.exceptions_utilities;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(Long id) {
        super("Appointment not found with id" + id);
    }
}
