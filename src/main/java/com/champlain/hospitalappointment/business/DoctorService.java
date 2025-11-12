package com.champlain.hospitalappointment.business;
import com.champlain.hospitalappointment.dataaccess.entity.Doctor;
import com.champlain.hospitalappointment.dataaccess.entity.Appointment;
import com.champlain.hospitalappointment.dataaccess.repository.DoctorRepository;
import com.champlain.hospitalappointment.dataaccess.repository.AppointmentRepository;
import com.champlain.hospitalappointment.presentation.dto.doctor.DoctorRequest;
import com.champlain.hospitalappointment.presentation.dto.doctor.DoctorResponse;
import com.champlain.hospitalappointment.presentation.dto.appointment.AppointmentResponse;
import com.champlain.hospitalappointment.presentation.mapper.DoctorMapper;
import com.champlain.hospitalappointment.presentation.mapper.AppointmentMapper;
import com.champlain.hospitalappointment.exceptions_utilities.DoctorNotFoundException;
import com.champlain.hospitalappointment.exceptions_utilities.DuplicateResourceException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

    public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

public DoctorService(DoctorRepository doctorRepository, AppointmentRepository appointmentReposoitory){
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentReposoitory;

}

@Transactional(readOnly = true)
public DoctorResponse getById(Long id) {
    Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new DoctorNotFoundException(id));
    return DoctorMapper.toResponse(doctor);
    }


    @Transactional(readOnly = true)
    public List<DoctorResponse> getAll() {
        return doctorRepository.findAll()
                .stream().map(DoctorMapper::toResponse).toList();
    }



    @Transactional
    public DoctorResponse create(DoctorRequest req) {
        if (doctorRepository.existsByEmailIgnoreCase(req.email())) {
            throw new DuplicateResourceException("Email already in use: " + req.email());
        }

        Doctor doctor = DoctorMapper.toEntity(req);
        Doctor saved = doctorRepository.save(doctor);
        return DoctorMapper.toResponse(saved);
    }

    @Transactional
    public DoctorResponse update(Long id, DoctorRequest req) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));

        boolean emailChanging = !doctor.getEmail().equalsIgnoreCase(req.email());
        if (emailChanging && doctorRepository.existsByEmailIgnoreCase(req.email())) {
            throw new DuplicateResourceException("Email already in use: " + req.email());
        }

        doctor.setFirstName(req.firstName());
        doctor.setLastName(req.lastName());
        doctor.setEmail(req.email());
        doctor.setPhone(req.phone());

        Doctor updated = doctorRepository.save(doctor);
        return DoctorMapper.toResponse(updated);
    }

    @Transactional
public void delete(Long id){
    Doctor doctor = doctorRepository.findById(id)
            .orElseThrow (() -> new DoctorNotFoundExceotion(id));
    doctorRepository.delete(doctor);
    }

//this helps to get all the appoinments by the doctor :)
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException(doctorId));

        return appointmentRepository.findAll().stream()
                .filter(a -> a.getDoctor() != null && a.getDoctor().getId().equals(doctorId))
                .map(AppointmentMapper::toResponse).toList();



}