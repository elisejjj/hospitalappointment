package com.champlain.hospitalappointment.presentation.mapper;

import com.champlain.hospitalappointment.dataaccess.entity.Appointment;
import com.champlain.hospitalappointment.dataaccess.entity.Doctor;
import com.champlain.hospitalappointment.dataaccess.entity.Patient;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentRequest;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentResponse;
import com.champlain.hospitalappointment.presentation.dto.doctor.DoctorResponse;
import com.champlain.hospitalappointment.presentation.dto.patient.PatientResponse;
import com.champlain.hospitalappointment.dataaccess.repository.AppointmentRepository;
import javax.print.Doc;

public final class AppointmentMapper {
    private AppointmentMapper() {
    }
    public static Appointment toEntity(AppointmentRequest req, Doctor doctor, Patient patient) {
        return Appointment.builder()
                .id(req.id())
                .date(req.date())
                .subject(req.subject())
                .duration(req.duration())
                .patient(patient)
                .doctor(doctor)
                .build();
    }
    public static AppointmentResponse toResponse(Appointment appointment) {
        Doctor o = appointment.getDoctor();
        Patient i = appointment.getPatient();
        DoctorResponse doctorDto = (o == null) ? null : new DoctorResponse(
                o.getId(),
                o.getFirstName(),
                o.getLastName(),
                o.getEmail(),
                o.getPhone()
        );
        PatientResponse patientDto = (i == null) ? null : new PatientResponse(
                i.getId(),
                i.getFirstName(),
                i.getLastName(),
                i.getEmail(),
                i.getPhone()
        );
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getSubject(),
                appointment.getDate(),
                appointment.getDuration(),
                patientDto,
                doctorDto
        );
    }
}