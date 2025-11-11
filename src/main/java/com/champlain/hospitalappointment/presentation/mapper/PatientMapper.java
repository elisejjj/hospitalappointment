package com.champlain.hospitalappointment.presentation.mapper;

import com.champlain.hospitalappointment.dataaccess.entity.Patient;
import com.champlain.hospitalappointment.presentation.dto.patient.PatientRequest;
import com.champlain.hospitalappointment.presentation.dto.patient.PatientResponse;

import javax.print.Doc;

public final class PatientMapper {
    private PatientMapper() {
    }
    public static Patient toEntity(PatientRequest req) {
        return Patient.builder()
                .firstName(req.firstName())
                .lastName(req.lastName())
                .email(req.email())
                .phone(req.phone())
                .build();
    }
    public static PatientResponse toResponse(Patient patient) {
        long count = patient.getAppointments() == null ? 0 : patient.getAppointments().size();
        return new PatientResponse(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getPhone()
        );
    }
}
