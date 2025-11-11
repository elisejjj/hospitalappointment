package com.champlain.hospitalappointment.dataaccess.repository;
import com.champlain.hospitalappointment.dataaccess.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByOwnerId(Long ownerId);
    boolean existsByRegistrationNumber(String registrationNumber);
    @Query("""
SELECT c
FROM Appointment c
WHERE (:id IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
AND (:patientId IS NULL OR c.patient.id = :patientId)
AND (:doctorId IS NULL OR c.doctor.id = :doctorId)
AND (:date IS NULL OR c.date = :date)
AND (:subject IS NULL OR c.subject = LIKE LOWER(CONCAT('%', :subject, '%'))))
AND (:duration IS NULL OR c.duration = :duration))
""")
    List<Appointment> searchAll(
            @Param("id") Long id,
            @Param("patientId") Long patientId,
            @Param("doctorId") Long doctorId,
            @Param("date") Date date,
            @Param("subject") String subject,
            @Param("duration") Integer duration
    );
}