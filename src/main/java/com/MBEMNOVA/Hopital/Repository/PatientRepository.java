package com.MBEMNOVA.Hopital.Repository;

import com.MBEMNOVA.Hopital.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}