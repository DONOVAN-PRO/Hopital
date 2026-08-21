package com.MBEMNOVA.Hopital.Controller;

import com.MBEMNOVA.Hopital.DTOs.RendezVousRequestDTO;
import com.MBEMNOVA.Hopital.DTOs.RendezVousResponseDTO;
import com.MBEMNOVA.Hopital.DTOs.RendezVousUpdateDTO;
import com.MBEMNOVA.Hopital.Service.RendezVousService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rendezvous")
@RequiredArgsConstructor
public class RendezVousController {

    private final RendezVousService rendezVousService;

    @PostMapping
    public ResponseEntity<RendezVousResponseDTO> creer(@Valid @RequestBody RendezVousRequestDTO dto) {
        RendezVousResponseDTO created = rendezVousService.creer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVousResponseDTO> modifier(
            @PathVariable Long id, @Valid @RequestBody RendezVousUpdateDTO dto) {
        return ResponseEntity.ok(rendezVousService.modifier(id, dto));
    }

    @PatchMapping("/{id}/annuler")
    public ResponseEntity<RendezVousResponseDTO> annuler(@PathVariable Long id) {
        return ResponseEntity.ok(rendezVousService.annuler(id));
    }

    @GetMapping
    public ResponseEntity<List<RendezVousResponseDTO>> listerTous() {
        return ResponseEntity.ok(rendezVousService.listerTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RendezVousResponseDTO> recupererParId(@PathVariable Long id) {
        return ResponseEntity.ok(rendezVousService.recupererParId(id));
    }

    @GetMapping("/statistiques")
    public ResponseEntity<Map<String, Object>> getStatistiques() {
        Map<String, Object> stats = Map.of(
                "parStatut", rendezVousService.statistiquesParStatut(),
                "tauxOccupationParHopital", rendezVousService.tauxOccupationParHopital(),
                "parLocalisation", rendezVousService.rendezVousParLocalisation()
        );
        return ResponseEntity.ok(stats);
    }
}