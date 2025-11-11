package com.champlain.hospitalappointment.presentation.dto.appointment;
public record AppointmentResponse(
        Long id,
        String subject,
        String model,
        String color,
        String registrationNumber,
        int modelYear,
        int price,
        OwnerSummary owner
) {
}