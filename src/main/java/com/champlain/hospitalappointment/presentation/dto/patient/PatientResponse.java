package com.champlain.hospitalappointment.presentation.dto.patient;
public record PatientResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}