package com.champlain.hospitalappointment.presentation.controller;

import com.champlain.hospitalappointment.business.AppointmentService;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentRequest;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentResponse;
import com.champlain.hospitalappointment.business.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/Appointments")
@Validated
public class AppointmentController {


    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService service) {
        this.appointmentService = service;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllCars() {
        List<AppointmentResponse> responseBody = AppointmentService.getAll();
        if (responseBody.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseBody);
    }

        @GetMapping("/{id}")
        public ResponseEntity<AppointmentResponse> getCarById (@PathVariable Long id){
            AppointmentResponse responseBody = AppointmentService.getById(id);
            return ResponseEntity.ok(responseBody);
        }
        @PostMapping
        public ResponseEntity<AppointmentResponse> createCar (@Valid @RequestBody AppointmentRequest requestBody){
            AppointmentResponse responseBody = AppointmentService.create(requestBody);
            return ResponseEntity.created(URI.create("/Appointments/" + responseBody.id()))
                    .body(responseBody);
        }
        @PutMapping("/{id}")
        public ResponseEntity<AppointmentResponse> updateCar (@PathVariable Long id,
                @Valid @RequestBody AppointmentRequest requestBody){
            AppointmentResponse responseBody = AppointmentService.update(id, requestBody);
            return ResponseEntity.ok(responseBody);
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteCar (@PathVariable Long id){
            AppointmentService.delete(id);
            return ResponseEntity.ok("Appointment " + id + " deleted.");
        }
        @GetMapping("/search")
        public ResponseEntity<List<AppointmentResponse>> searchCars (
                @RequestParam(required = false) String brand,
                @RequestParam(required = false) Long ownerId, @RequestParam(required = false) String color,
                @RequestParam(required = false) Integer minPrice,
                @RequestParam(required = false) Integer maxPrice,
                @RequestParam(required = false) Integer minYear,
                @RequestParam(required = false) Integer maxYear,
                @RequestParam(required = false) String registrationContains
    ){
            List<AppointmentResponse> responseBody = AppointmentService.search(brand, ownerId, color, minPrice, maxPrice, minYear, maxYear,
                    registrationContains);
            if (responseBody.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(responseBody);
        }
    }


