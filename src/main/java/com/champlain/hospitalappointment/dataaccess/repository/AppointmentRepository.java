package com.champsoft.cardatabasev2.DataAccessLayer;
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
FROM Car c
WHERE (:brand IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
AND (:ownerId IS NULL OR c.owner.id = :ownerId)
AND (:color IS NULL OR LOWER(c.color) = LOWER(:color))
AND (:minPrice IS NULL OR c.price >= :minPrice)
AND (:maxPrice IS NULL OR c.price <= :maxPrice)
AND (:minYear IS NULL OR c.modelYear >= :minYear)
AND (:maxYear IS NULL OR c.modelYear <= :maxYear)
AND (:regPart IS NULL OR LOWER(c.registrationNumber) LIKE LOWER(CONCAT('%', :regPart, '%')))
""")
    List<Appointment> searchAll(
            @Param("brand") String brand,
            @Param("patientId") Long ownerId,
            @Param("doctorId") Long doctorId,
            @Param("date") Date date,
            @Param("subject") String subject,
            @Param("duration") Integer duration
    );
}