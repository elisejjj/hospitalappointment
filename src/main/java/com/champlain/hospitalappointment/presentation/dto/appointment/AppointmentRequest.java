package com.champlain.hospitalappointment.presentation.dto.appointment;
import jakarta.validation.constraints.*;

import java.util.Date;

public record AppointmentRequest(


        @NotBlank String subject,
        @NotNull Date date,
        @Positive int duration,
        @NotNull @Positive Long patientId,
        @NotNull @Positive Long doctorId
) {}