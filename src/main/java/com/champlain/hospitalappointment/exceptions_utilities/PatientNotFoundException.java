package com.champlain.hospitalappointment.exceptions_utilities;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long patientId) {
        super("Patient has not been found with patient id" + patientId);
    }
}
