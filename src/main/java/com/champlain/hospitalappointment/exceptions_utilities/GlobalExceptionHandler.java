package com.champlain.hospitalappointment.exceptions_utilities;


import com.champlain.hospitalappointment.dataaccess.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // -------------------- 404: Not Found --------------------
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleCarNotFound(AppointmentNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Appointment not found");
        pd.setDetail(ex.getMessage());
        pd.setProperty("errors", List.of(Map.of("field", "id", "message", ex.getLocalizedMessage())));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleDoctorNotFound(DoctorNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Doctor Not Found");
        pd.setDetail(ex.getMessage());
        pd.setProperty("errors", List.of(Map.of("field", "DoctorID", "message", ex.getLocalizedMessage())));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);

    }


    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ProblemDetail> handlePatientNotFound(PatientNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Patient Not Found");
        pd.setDetail(ex.getMessage());
        pd.setProperty("errors", List.of(Map.of("field", "PatientID", "message", ex.getLocalizedMessage())));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);

    }


    // ------------- 400: Bad Request (validation & parsing) -------------
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ProblemDetail> handleDuplicate(DuplicateResourceException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Duplicate Resource");
        pd.setDetail(ex.getMessage());
        pd.setProperty("errors", List.of(Map.of(
                "field", "duplicate",
                "message", ex.getLocalizedMessage()
        )));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);

        if (PatientRepository.existsByEmailIgnoreCase(req.getEmail())) {
            throw new DuplicateResourceException("A patient with this email already exists.");
        }


    }

















}