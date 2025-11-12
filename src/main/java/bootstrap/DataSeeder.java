package bootstrap;


import com.champlain.hospitalappointment.dataaccess.entity.Doctor;
import com.champlain.hospitalappointment.dataaccess.entity.Patient;
import com.champlain.hospitalappointment.dataaccess.entity.Appointment;
import com.champlain.hospitalappointment.dataaccess.repository.DoctorRepository;
import com.champlain.hospitalappointment.dataaccess.repository.PatientRepository;
import com.champlain.hospitalappointment.dataaccess.repository.AppointmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner loadData(DoctorRepository doctorRepo,
                               PatientRepository patientRepo,
                               AppointmentRepository appointmentRepo) {
        return args -> {
            if (doctorRepo.count() == 0) {
                Doctor d1 = new Doctor(null, "John", "Smith", "john.smith@hospital.com", "5145551111", null, null, null);
                Doctor d2 = new Doctor(null, "Alice", "Brown", "alice.brown@hospital.com", "5145552222", null, null, null);
                doctorRepo.save(d1);
                doctorRepo.save(d2);
            }
            if (patientRepo.count() == 0) {
                Patient p1 = new Patient(null, "Michael", "Green", "michael.green@mail.com", "4385551111", null, null, null);
                Patient p2 = new Patient(null, "Sarah", "White", "sarah.white@mail.com", "4385552222", null, null, null);
                patientRepo.save(p1);
                patientRepo.save(p2);
            }
            if (appointmentRepo.count() == 0) {
                Doctor doctor1 = doctorRepo.findAll().get(0);
                Patient patient1 = patientRepo.findAll().get(0);
                Appointment a1 = new Appointment(
                        null,
                        Timestamp.valueOf(LocalDateTime.now().plusDays(1)),
                        "General Check-up", 30, doctor1, patient1, null, null
                );

                Appointment a2 = new Appointment(
                        null, Timestamp.valueOf(LocalDateTime.now().plusDays(3)), "Follow-up Consultation", 45, doctor1,
                        patient1, null, null
                );

                appointmentRepo.save(a1);
                appointmentRepo.save(a2);
            }

            System.out.println("Demo doctors, patients, and appointments added successfully!YAAAYYYYY!");
        };
    }

}
