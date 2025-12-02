package com.champlain.hospitalappointment.presentation.controller;

import com.champlain.hospitalappointment.business.AppointmentService;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentRequest;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "http://localhost:3000")

@Validated
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // ---------- GET ALL ----------
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAll() {
        List<AppointmentResponse> responseBody = appointmentService.getAll();
        if (responseBody.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseBody);
    }

    // ---------- GET BY ID ----------
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) {
        AppointmentResponse responseBody = appointmentService.getById(id);
        return ResponseEntity.ok(responseBody);
    }

    // ---------- CREATE ----------
    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentRequest requestBody) {
        AppointmentResponse responseBody = appointmentService.create(requestBody);
        return ResponseEntity.created(URI.create("/appointments/" + responseBody.id()))
                .body(responseBody);
    }

    // ---------- UPDATE ----------
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable Long id,
                                                      @Valid @RequestBody AppointmentRequest requestBody) {
        AppointmentResponse responseBody = appointmentService.update(id, requestBody);
        return ResponseEntity.ok(responseBody);
    }

    // ---------- DELETE ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.ok("Appointment " + id + " deleted successfully.");
    }
}
