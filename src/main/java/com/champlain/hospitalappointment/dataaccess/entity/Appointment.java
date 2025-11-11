package com.champlain.hospitalappointment.dataaccess.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(
        name = "appointment_inventory",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_appointment_number", columnNames = "appointment_number")
        },
        indexes = {
                @Index(name = "idx_brand", columnList = "brand"),
                @Index(name = "idx_model_year", columnList = "model_year"),
                @Index(name = "idx_owner_id", columnList = "owner_id")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=80)
    private Date date;
    @Column(name = "appointment_subject", nullable=false, length=120)
    private String subject;
    @Column(nullable=false, length=40)
    private int duration;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_doctor")
    )
    private Doctor doctor;
    @JoinColumn(
            name = "patient_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_patient")
    )
    private Patient patient;
    @CreatedDate
    @Column(nullable=false, updatable=false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(nullable=false)
    private Instant updatedAt;
}