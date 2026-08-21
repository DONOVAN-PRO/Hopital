package com.MBEMNOVA.Hopital.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class HopitalResponseDTO {
    private Long id;
    private String nom;
    private String localisation;
    private int capacite;
    private long placesRestantes;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
}