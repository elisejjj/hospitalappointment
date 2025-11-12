package com.champlain.hospitalappointment.dataaccess.repository;

import com.champlain.hospitalappointment.dataaccess.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByPatientIdAndDate(Long patientId, Date date);

    @Query("""
        SELECT a FROM Appointment a
        WHERE (:patientId IS NULL OR a.patient.id = :patientId)
          AND (:doctorId IS NULL OR a.doctor.id = :doctorId)
          AND (:date IS NULL OR a.date = :date)
          AND (:subject IS NULL OR LOWER(a.subject) LIKE LOWER(CONCAT('%', :subject, '%')))
          AND (:duration IS NULL OR a.duration = :duration)
        """)
    List<Appointment> searchAll(
            @Param("patientId") Long patientId,
            @Param("doctorId") Long doctorId,
            @Param("date") Date date,
            @Param("subject") String subject,
            @Param("duration") Integer duration
    );
}
