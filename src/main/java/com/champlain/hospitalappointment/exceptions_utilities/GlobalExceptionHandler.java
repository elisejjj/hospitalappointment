package com.champlain.hospitalappointment.exceptions_utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ---------- 404 NOT FOUND ----------
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleAppointmentNotFound(AppointmentNotFoundException ex) {
        return buildProblem(HttpStatus.NOT_FOUND, "Appointment Not Found", ex.getMessage(), "appointmentId");
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleDoctorNotFound(DoctorNotFoundException ex) {
        return buildProblem(HttpStatus.NOT_FOUND, "Doctor Not Found", ex.getMessage(), "doctorId");
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ProblemDetail> handlePatientNotFound(PatientNotFoundException ex) {
        return buildProblem(HttpStatus.NOT_FOUND, "Patient Not Found", ex.getMessage(), "patientId");
    }

    // ---------- 400 BAD REQUEST ----------
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ProblemDetail> handleDuplicate(DuplicateResourceException ex) {
        return buildProblem(HttpStatus.BAD_REQUEST, "Duplicate Resource", ex.getMessage(), "duplicate");
    }

    // ---------- Helper ----------
    private ResponseEntity<ProblemDetail> buildProblem(HttpStatus status, String title, String message, String field) {
        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle(title);
        pd.setDetail(message);
        pd.setProperty("errors", List.of(Map.of("field", field, "message", message)));
        return ResponseEntity.status(status).body(pd);
    }
}
