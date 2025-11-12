package com.champlain.hospitalappointment.business;


import com.champlain.hospitalappointment.dataaccess.entity.Patient;
import com.champlain.hospitalappointment.dataaccess.entity.Appointment;
import com.champlain.hospitalappointment.dataaccess.repository.PatientRepository;
import com.champlain.hospitalappointment.dataaccess.repository.AppointmentRepository;
import com.champlain.hospitalappointment.presentation.dto.patient.PatientRequest;
import com.champlain.hospitalappointment.presentation.dto.patient.PatientResponse;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentResponse;
import com.champlain.hospitalappointment.presentation.mapper.PatientMapper;
import com.champlain.hospitalappointment.presentation.mapper.AppointmentMapper;
import com.champlain.hospitalappointment.exceptions_utilities.PatientNotFoundException;
import com.champlain.hospitalappointment.exceptions_utilities.DuplicateResourceException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public PatientService(PatientRepository patientRepository,
                          AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> getAll() {
        return patientRepository.findAll().stream().map(PatientMapper::toResponse).toList();
    }


    @Transactional(readOnly = true)
    public PatientResponse getById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        return PatientMapper.toResponse(patient);
    }


    @Transactional
    public PatientResponse create(PatientRequest req) {
        // Prevent duplicate email
        if (patientRepository.existsByEmailIgnoreCase(req.email())) {
            throw new DuplicateResourceException("Email already in use: " + req.email());
        }


        Patient entity = PatientMapper.toEntity(req);
        Patient saved = patientRepository.save(entity);
        return PatientMapper.toResponse(saved);
    }


    @Transactional
    public PatientResponse update(Long id, PatientRequest req) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        boolean emailChanging = !patient.getEmail().equalsIgnoreCase(req.email());
        if (emailChanging && patientRepository.existsByEmailIgnoreCase(req.email())) {
            throw new DuplicateResourceException("Email already in use: " + req.email());
        }


        patient.setFirstName(req.firstName());
        patient.setLastName(req.lastName());
        patient.setEmail(req.email());
        patient.setPhone(req.phone());

        Patient updated = patientRepository.save(patient);
        return PatientMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        patientRepository.delete(patient);
    }


    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(patientId));

        return appointmentRepository.findAll().stream()
                .filter(a -> a.getPatient() != null && a.getPatient().getId().equals(patientId))
                .map(AppointmentMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Patient getEntityById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }


}
