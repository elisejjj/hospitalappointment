package com.champlain.hospitalappointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HospitalappointmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(HospitalappointmentApplication.class, args);
	}
}
