package com.champlain.hospitalappointment.presentation.dto.appointment;
import jakarta.validation.constraints.*;

import java.util.Date;

public record AppointmentRequest(

        @NotBlank Long id,
        @NotBlank String subject,
        @NotBlank Date date,
        @NotBlank int duration,
        @NotNull @Positive Long patientId,
        @NotNull @Positive Long doctorId
) {}