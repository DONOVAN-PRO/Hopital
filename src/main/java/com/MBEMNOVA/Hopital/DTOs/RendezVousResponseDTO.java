package com.MBEMNOVA.Hopital.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RendezVousResponseDTO {
    private Long id;

    private Long patientId;
    private String patientNomComplet;
    private String patientTelephone;

    private Long hopitalId;
    private String hopitalNom;
    private String hopitalLocalisation;

    private LocalDate date;
    private LocalTime heure;
    private String statut;

    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
}