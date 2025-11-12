package com.champlain.hospitalappointment.dataaccess.repository;

import com.champlain.hospitalappointment.dataaccess.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByEmailIgnoreCase(String email);

    @Query("""
        SELECT d
        FROM Doctor d
        WHERE (:firstName IS NULL OR LOWER(d.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')))
          AND (:lastName IS NULL OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))
          AND (:emailPart IS NULL OR LOWER(d.email) LIKE LOWER(CONCAT('%', :emailPart, '%')))
          AND (:phonePart IS NULL OR (d.phone IS NOT NULL AND LOWER(d.phone) LIKE LOWER(CONCAT('%', :phonePart, '%'))))
          AND (:minCreated IS NULL OR d.createdAt >= :minCreated)
          AND (:maxCreated IS NULL OR d.createdAt <= :maxCreated)
          AND (:minUpdated IS NULL OR d.updatedAt >= :minUpdated)
          AND (:maxUpdated IS NULL OR d.updatedAt <= :maxUpdated)
        """)
    List<Doctor> searchAll(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("emailPart") String emailPart,
            @Param("phonePart") String phonePart,
            @Param("minCreated") Instant minCreated,
            @Param("maxCreated") Instant maxCreated,
            @Param("minUpdated") Instant minUpdated,
            @Param("maxUpdated") Instant maxUpdated
    );
}
