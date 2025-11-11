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
@Transactional


    public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final appointmentRepository appointmentRepository

public DoctorService(DoctorRepository doctorRepository, AppointmentReposoitory appointmentReposoitory){
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentReposoitory;

}

@Transactional(readOnly = true)
public DoctorResponse getById(Long id) {
    Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new DoctorNotFoundException(id));
    return DoctorMapper.toResponse(doctor);
    }

