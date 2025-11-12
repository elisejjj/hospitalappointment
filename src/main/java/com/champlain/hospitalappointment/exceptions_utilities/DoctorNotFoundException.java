package com.champlain.hospitalappointment.exceptions_utilities;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(Long doctorId) {
        super("Doctor has not been found with" + doctorId);
    }
}
