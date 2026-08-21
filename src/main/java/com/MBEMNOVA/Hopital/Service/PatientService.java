package com.MBEMNOVA.Hopital.Service;

import com.MBEMNOVA.Hopital.DTOs.PatientRequestDTO;
import com.MBEMNOVA.Hopital.DTOs.PatientResponseDTO;
import com.MBEMNOVA.Hopital.Entity.Patient;
import com.MBEMNOVA.Hopital.Exception.RessourceNotFoundException;
import com.MBEMNOVA.Hopital.Mapper.PatientMapper;
import com.MBEMNOVA.Hopital.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Transactional
    public PatientResponseDTO creer(PatientRequestDTO dto) {
        Patient patient = patientMapper.toEntity(dto);
        Patient saved = patientRepository.save(patient);
        return patientMapper.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<PatientResponseDTO> listerTous() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PatientResponseDTO recupererParId(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Patient introuvable avec l'id : " + id));
        return patientMapper.toResponseDTO(patient);
    }

    // TODO : modification et suppression, à compléter plus tard
}