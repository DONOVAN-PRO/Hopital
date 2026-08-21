package com.MBEMNOVA.Hopital.DTOs;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class RendezVousUpdateDTO {

    @NotNull(message = "La date est obligatoire")
    @FutureOrPresent(message = "La date du rendez-vous ne peut pas être dans le passé")
    private LocalDate date;

    @NotNull(message = "L'heure est obligatoire")
    private LocalTime heure;
}