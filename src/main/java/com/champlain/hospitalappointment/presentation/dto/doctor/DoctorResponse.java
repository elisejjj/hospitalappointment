package com.champlain.hospitalappointment.presentation.dto.doctor;
public record DoctorResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}