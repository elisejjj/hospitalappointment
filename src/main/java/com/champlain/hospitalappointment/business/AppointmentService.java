package com.champlain.hospitalappointment.business;

import com.champlain.hospitalappointment.dataaccess.entity.Appointment;
import com.champlain.hospitalappointment.dataaccess.entity.Doctor;
import com.champlain.hospitalappointment.dataaccess.entity.Patient;
import com.champlain.hospitalappointment.dataaccess.repository.AppointmentRepository;
import com.champlain.hospitalappointment.exceptions_utilities.AppointmentNotFoundException;
import com.champlain.hospitalappointment.exceptions_utilities.DuplicateResourceException;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentRequest;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentResponse;
import com.champlain.hospitalappointment.presentation.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    // ---------- READ ----------
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAll() {
        return appointmentRepository.findAll()
                .stream().map(AppointmentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        return AppointmentMapper.toResponse(appointment);
    }

    // ---------- CREATE ----------
    @Transactional
    public AppointmentResponse create(AppointmentRequest req) {
        // Duplicate check (patient + date)
        boolean duplicate = appointmentRepository.existsByPatientIdAndDate(req.patientId(), req.date());
        if (duplicate) {
            throw new DuplicateResourceException(
                    "This patient already has an appointment on " + req.date()
            );
        }

        Doctor doctor = doctorService.getEntityById(req.doctorId());
        Patient patient = patientService.getEntityById(req.patientId());

        Appointment appointment = AppointmentMapper.toEntity(req, doctor, patient);
        Appointment saved = appointmentRepository.save(appointment);
        return AppointmentMapper.toResponse(saved);
    }

    // ---------- UPDATE ----------
    @Transactional
    public AppointmentResponse update(Long id, AppointmentRequest req) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        boolean duplicate = appointmentRepository.existsByPatientIdAndDate(req.patientId(), req.date());
        if (duplicate && !existing.getId().equals(id)) {
            throw new DuplicateResourceException(
                    "Another appointment already exists for this patient at the same date."
            );
        }

        Doctor doctor = doctorService.getEntityById(req.doctorId());
        Patient patient = patientService.getEntityById(req.patientId());

        existing.setDate(req.date());
        existing.setSubject(req.subject());
        existing.setDuration(req.duration());
        existing.setDoctor(doctor);
        existing.setPatient(patient);

        Appointment updated = appointmentRepository.save(existing);
        return AppointmentMapper.toResponse(updated);
    }

    // ---------- DELETE ----------
    @Transactional
    public void delete(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        appointmentRepository.delete(appointment);
    }
}
