package com.champlain.hospitalappointment.presentation.controller;

import com.champlain.hospitalappointment.business.PatientService;
import com.champlain.hospitalappointment.presentation.dto.patient.PatientRequest;
import com.champlain.hospitalappointment.presentation.dto.patient.PatientResponse;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients")
@Validated
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // ---------- GET ALL ----------
    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAll() {
        List<PatientResponse> patients = patientService.getAll();
        if (patients.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(patients);
    }

    // ---------- GET BY ID ----------
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    // ---------- CREATE ----------
    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientRequest req) {
        PatientResponse created = patientService.create(req);
        return ResponseEntity.created(URI.create("/patients/" + created.id())).body(created);
    }

    // ---------- UPDATE ----------
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody PatientRequest req) {
        return ResponseEntity.ok(patientService.update(id, req));
    }

    // ---------- DELETE ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.ok("Patient " + id + " deleted successfully.");
    }

    // ---------- GET APPOINTMENTS ----------
    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByPatient(@PathVariable Long id) {
        List<AppointmentResponse> appointments = patientService.getAppointmentsByPatient(id);
        if (appointments.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(appointments);
    }
}
