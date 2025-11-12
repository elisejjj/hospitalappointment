package com.champlain.hospitalappointment.dataaccess.repository;

import com.champlain.hospitalappointment.dataaccess.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByEmailIgnoreCase(String email);

    @Query("""
        SELECT p
        FROM Patient p
        WHERE (:firstName IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')))
          AND (:lastName IS NULL OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))
          AND (:emailPart IS NULL OR LOWER(p.email) LIKE LOWER(CONCAT('%', :emailPart, '%')))
          AND (:phonePart IS NULL OR (p.phone IS NOT NULL AND LOWER(p.phone) LIKE LOWER(CONCAT('%', :phonePart, '%'))))
          AND (:minCreated IS NULL OR p.createdAt >= :minCreated)
          AND (:maxCreated IS NULL OR p.createdAt <= :maxCreated)
          AND (:minUpdated IS NULL OR p.updatedAt >= :minUpdated)
          AND (:maxUpdated IS NULL OR p.updatedAt <= :maxUpdated)
        """)
    List<Patient> searchAll(
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
