package com.champlain.hospitalappointment.presentation.mapper;

import com.champlain.hospitalappointment.dataaccess.entity.Doctor;
import com.champlain.hospitalappointment.presentation.dto.doctor.DoctorRequest;
import com.champlain.hospitalappointment.presentation.dto.doctor.DoctorResponse;


import javax.print.Doc;

public final class DoctorMapper {
    private DoctorMapper() {
    }
    public static Doctor toEntity(DoctorRequest req) {
        return Doctor.builder()
                .firstName(req.firstName())
                .lastName(req.lastName())
                .email(req.email())
                .phone(req.phone())
                .build();
    }
    public static DoctorResponse toResponse(Doctor doctor) {
        long count = doctor.getAppointments() == null ? 0 : doctor.getAppointments().size();
        return new DoctorResponse(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getEmail(),
                doctor.getPhone()
        );
    }
}
