package com.champlain.hospitalappointment.presentation.controller;

import com.champlain.hospitalappointment.business.DoctorService;
import com.champlain.hospitalappointment.presentation.dto.doctor.DoctorRequest;
import com.champlain.hospitalappointment.presentation.dto.doctor.DoctorResponse;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "http://localhost:3000")

public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // ---------- GET ALL ----------
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAll() {
        List<DoctorResponse> doctors = doctorService.getAll();
        if (doctors.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(doctors);
    }

    // ---------- GET BY ID ----------
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getById(id));
    }

    // ---------- CREATE ----------
    @PostMapping
    public ResponseEntity<DoctorResponse> create(@Valid @RequestBody DoctorRequest req) {
        DoctorResponse created = doctorService.create(req);
        return ResponseEntity.created(URI.create("/doctors/" + created.id())).body(created);
    }

    // ---------- UPDATE ----------
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody DoctorRequest req) {
        return ResponseEntity.ok(doctorService.update(id, req));
    }

    // ---------- DELETE ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.ok("Doctor " + id + " deleted successfully.");
    }

    // ---------- GET APPOINTMENTS ----------
    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByDoctor(@PathVariable Long id) {
        List<AppointmentResponse> appointments = doctorService.getAppointmentsByDoctor(id);
        if (appointments.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(appointments);
    }
}
