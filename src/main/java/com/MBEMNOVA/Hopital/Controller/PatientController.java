package com.MBEMNOVA.Hopital.Controller;

import com.MBEMNOVA.Hopital.DTOs.PatientRequestDTO;
import com.MBEMNOVA.Hopital.DTOs.PatientResponseDTO;
import com.MBEMNOVA.Hopital.Service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Gestion des patients")
public class PatientController {

    private final PatientService patientService;

    @Operation(
            summary = "Créer un patient",
            description = "Enregistre un nouveau patient avec ses coordonnées et sa localisation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Patient créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides (email mal formé, téléphone invalide, champ manquant)")
    })
    @PostMapping
    public ResponseEntity<PatientResponseDTO> creer(@Valid @RequestBody PatientRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.creer(dto));
    }

    @Operation(
            summary = "Lister tous les patients",
            description = "Renvoie la liste complète des patients enregistrés."
    )
    @ApiResponse(responseCode = "200", description = "Liste des patients renvoyée avec succès")
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> listerTous() {
        return ResponseEntity.ok(patientService.listerTous());
    }

    @Operation(
            summary = "Récupérer un patient par son id",
            description = "Renvoie le détail d'un patient donné."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient trouvé"),
            @ApiResponse(responseCode = "404", description = "Aucun patient trouvé pour cet id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> recupererParId(
            @Parameter(description = "Identifiant du patient") @PathVariable Long id) {
        return ResponseEntity.ok(patientService.recupererParId(id));
    }
}