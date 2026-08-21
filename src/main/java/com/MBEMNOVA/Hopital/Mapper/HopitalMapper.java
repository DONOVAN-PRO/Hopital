package com.MBEMNOVA.Hopital.Mapper;

import com.MBEMNOVA.Hopital.DTOs.HopitalRequestDTO;
import com.MBEMNOVA.Hopital.DTOs.HopitalResponseDTO;
import com.MBEMNOVA.Hopital.Entity.Hopital;
import org.springframework.stereotype.Component;

@Component
public class HopitalMapper {

    public Hopital toEntity(HopitalRequestDTO dto) {
        return Hopital.builder()
                .nom(dto.getNom())
                .localisation(dto.getLocalisation())
                .capacite(dto.getCapacite())
                .build();
    }

    public HopitalResponseDTO toResponseDTO(Hopital hopital, long rdvActifs) {
        return HopitalResponseDTO.builder()
                .id(hopital.getId())
                .nom(hopital.getNom())
                .localisation(hopital.getLocalisation())
                .capacite(hopital.getCapacite())
                .placesRestantes(hopital.getCapacite() - rdvActifs)
                .dateCreation(hopital.getDateCreation())
                .dateDerniereModification(hopital.getDateDerniereModification())
                .build();
    }
}