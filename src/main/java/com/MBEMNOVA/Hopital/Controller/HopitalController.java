package com.MBEMNOVA.Hopital.Controller;

import com.MBEMNOVA.Hopital.DTOs.HopitalRequestDTO;
import com.MBEMNOVA.Hopital.DTOs.HopitalResponseDTO;
import com.MBEMNOVA.Hopital.Service.HopitalService;
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
@RequestMapping("/api/hopitaux")
@RequiredArgsConstructor
@Tag(name = "Hôpitaux", description = "Gestion des hôpitaux : création, consultation, disponibilité")
public class HopitalController {

    private final HopitalService hopitalService;

    @Operation(
            summary = "Créer un hôpital",
            description = "Crée un nouvel hôpital avec un nom, une localisation et une capacité maximale."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Hôpital créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides (champ manquant, capacité hors limites, etc.)")
    })
    @PostMapping
    public ResponseEntity<HopitalResponseDTO> creer(@Valid @RequestBody HopitalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hopitalService.creer(dto));
    }

    @Operation(
            summary = "Lister tous les hôpitaux",
            description = "Renvoie la liste complète des hôpitaux, y compris ceux qui ont atteint leur capacité."
    )
    @ApiResponse(responseCode = "200", description = "Liste des hôpitaux renvoyée avec succès")
    @GetMapping
    public ResponseEntity<List<HopitalResponseDTO>> listerTous() {
        return ResponseEntity.ok(hopitalService.listerTous());
    }

    @Operation(
            summary = "Récupérer un hôpital par son id",
            description = "Renvoie le détail d'un hôpital, avec le nombre de places restantes."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hôpital trouvé"),
            @ApiResponse(responseCode = "404", description = "Aucun hôpital trouvé pour cet id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HopitalResponseDTO> recupererParId(
            @Parameter(description = "Identifiant de l'hôpital") @PathVariable Long id) {
        return ResponseEntity.ok(hopitalService.recupererParId(id));
    }

    @Operation(
            summary = "Lister les hôpitaux disponibles pour un patient",
            description = "Renvoie les hôpitaux non pleins, en priorité ceux de la localisation du patient. "
                    + "Si aucun hôpital de sa localisation n'est disponible, renvoie tous les hôpitaux disponibles."
    )
    @ApiResponse(responseCode = "200", description = "Liste des hôpitaux disponibles renvoyée avec succès")
    @GetMapping("/disponibles")
    public ResponseEntity<List<HopitalResponseDTO>> getHopitauxDisponibles(
            @Parameter(description = "Localisation du patient, utilisée pour prioriser les hôpitaux proches")
            @RequestParam(required = false) String localisation) {
        return ResponseEntity.ok(hopitalService.getHopitauxDisponibles(localisation));
    }
}