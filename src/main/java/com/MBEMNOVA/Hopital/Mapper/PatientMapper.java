package com.MBEMNOVA.Hopital.Mapper;

import com.MBEMNOVA.Hopital.DTOs.PatientRequestDTO;
import com.MBEMNOVA.Hopital.DTOs.PatientResponseDTO;
import com.MBEMNOVA.Hopital.Entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequestDTO dto) {
        return Patient.builder()
                .nom(dto.getNom())
                .localisation(dto.getLocalisation())
                .build();
    }

    public PatientResponseDTO toResponseDTO(Patient patient) {
        return PatientResponseDTO.builder()
                .id(patient.getId())
                .nom(patient.getNom())
                .localisation(patient.getLocalisation())
                .dateCreation(patient.getDateCreation())
                .dateDerniereModification(patient.getDateDerniereModification())
                .build();
    }
}