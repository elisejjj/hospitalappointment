package com.champlain.hospitalappointment.presentation.dto.appointment;

import com.champlain.hospitalappointment.presentation.dto.doctor.DoctorResponse;
import com.champlain.hospitalappointment.presentation.dto.patient.PatientResponse;

import java.util.Date;

public record AppointmentResponse(
        Long id,
        String subject,
        Date date,
        int duration,
        PatientResponse patient,
        DoctorResponse doctor
) {
}