package com.champlain.hospitalappointment.dataaccess.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(
        name = "patients",
        uniqueConstraints = { @UniqueConstraint(name = "uk_patient_email", columnNames = "email") }
)
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=80)
    private String firstName;
    @Column(nullable=false, length=80)
    private String lastName;
    @Column(nullable=false, length=255)
    private String email;
    @Column(length=40)
    private String phone;
    @CreatedDate
    @Column(nullable=false, updatable=false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(nullable=false)
    private Instant updatedAt;
    // Keep the OneToMany simple. No cascade, no orphanRemoval.
// Manage the association from Car.owner (@ManyToOne) side.
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();
}