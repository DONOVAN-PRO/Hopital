package com.MBEMNOVA.Hopital.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Données envoyées pour créer un hôpital")
public class HopitalRequestDTO {

    @Schema(description = "Nom de l'hôpital", example = "CHU Douala")
    @NotBlank(message = "Le nom de l'hôpital est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    @Schema(description = "Localisation de l'hôpital", example = "Douala")
    @NotBlank(message = "La localisation est obligatoire")
    @Size(min = 2, max = 100, message = "La localisation doit contenir entre 2 et 100 caractères")
    private String localisation;

    @Schema(description = "Nombre maximum de rendez-vous actifs simultanés", example = "50")
    @Min(value = 1, message = "La capacité doit être d'au moins 1")
    @Max(value = 500, message = "La capacité ne peut pas dépasser 500")
    private int capacite;
}