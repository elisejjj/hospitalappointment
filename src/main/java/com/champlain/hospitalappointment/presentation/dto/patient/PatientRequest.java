package com.champlain.hospitalappointment.presentation.dto.patient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record PatientRequest(
        @NotBlank @Size(max=80) String firstName,
        @NotBlank @Size(max=80) String lastName,
        @NotBlank @Email String email,
        @Size(max=40) String phone
) {
}