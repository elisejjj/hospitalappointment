package com.champlain.hospitalappointment.presentation.dto.appointment;
import jakarta.validation.constraints.*;
public record AppointmentRequest(
        @NotBlank String brand,
        @NotBlank String model,
        @NotBlank String color,
        @NotNull @Positive Long patientId,
        @NotNull @Positive Long doctorId
) {}