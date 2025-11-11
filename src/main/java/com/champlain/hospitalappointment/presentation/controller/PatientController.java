package com.champlain.hospitalappointment.presentation.controller;

import com.champlain.hospitalappointment.business.AppointmentService;
import com.champlain.hospitalappointment.business.PatientService;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentRequest;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

public class PatientController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;


    public PatientController(AppointmentService appointmentService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> responseBody = AppointmentService.getAll();
        if (responseBody.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseBody);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getOwnerById(@PathVariable Long id) {
        AppointmentService responseBody = AppointmentService.getById(id);
        return ResponseEntity.ok(responseBody);
    }
    @PostMapping
    public ResponseEntity<AppointmentResponse> createOwner(@Valid @RequestBody AppointmentRequest requestBody) {
        AppointmentResponse responseBody = AppointmentService.create(requestBody);
        return ResponseEntity.created(URI.create("/Appointments/" + responseBody.id()))
                .body(responseBody);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateOwner(@PathVariable Long id,
                                                           @Valid @RequestBody AppointmentRequest requestBody) {
        AppointmentResponse responseBody = AppointmentService.update(id, requestBody);
        return ResponseEntity.ok(responseBody);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOwner(@PathVariable Long id) {
        AppointmentService.delete(id);
        return ResponseEntity.ok("Appointment " + id + " deleted.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<AppointmentResponse>> searchOwners(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String emailContains,
            @RequestParam(required = false) String phoneContains,
            @RequestParam(required = false) Instant minCreated,
            @RequestParam(required = false) Instant maxCreated,
            @RequestParam(required = false) Instant minUpdated,
            @RequestParam(required = false) Instant maxUpdated
    ) {
        List<AppointmentResponse> responseBody = AppointmentService.search(
                firstName, lastName, emailContains, phoneContains,
                minCreated, maxCreated, minUpdated, maxUpdated
        );
        if (responseBody.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseBody);
    }
    @GetMapping("/{id}/Appointment")
    public ResponseEntity<List<AppointmentResponse>> getCarsByOwner(@PathVariable Long id) {
        List<AppointmentResponse> responseBody = AppointmentService.getAppointmentByPatient(id);
        if (responseBody.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseBody);
    }
}





}



