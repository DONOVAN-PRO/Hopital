package com.MBEMNOVA.Hopital.Mapper;

import com.MBEMNOVA.Hopital.DTOs.RendezVousResponseDTO;
import com.MBEMNOVA.Hopital.Entity.RendezVous;
import org.springframework.stereotype.Component;

@Component
public class RendezVousMapper {

    public RendezVousResponseDTO toResponseDTO(RendezVous rdv) {
        return RendezVousResponseDTO.builder()
                .id(rdv.getId())
                .patientId(rdv.getPatient().getId())
                .patientNomComplet(rdv.getPatient().getPrenom() + " " + rdv.getPatient().getNom())
                .patientTelephone(rdv.getPatient().getTelephone())
                .hopitalId(rdv.getHopital().getId())
                .hopitalNom(rdv.getHopital().getNom())
                .hopitalLocalisation(rdv.getHopital().getLocalisation())
                .date(rdv.getDate())
                .heure(rdv.getHeure())
                .statut(rdv.getStatut().name())
                .dateCreation(rdv.getDateCreation())
                .dateDerniereModification(rdv.getDateDerniereModification())
                .build();
    }
}