package com.MBEMNOVA.Hopital.Service;

import com.MBEMNOVA.Hopital.DTOs.RendezVousRequestDTO;
import com.MBEMNOVA.Hopital.DTOs.RendezVousResponseDTO;
import com.MBEMNOVA.Hopital.DTOs.RendezVousUpdateDTO;
import com.MBEMNOVA.Hopital.Entity.Hopital;
import com.MBEMNOVA.Hopital.Entity.Patient;
import com.MBEMNOVA.Hopital.Entity.RendezVous;
import com.MBEMNOVA.Hopital.Entity.StatutRendezVous;
import com.MBEMNOVA.Hopital.Exception.CapaciteAtteinteException;
import com.MBEMNOVA.Hopital.Exception.ConflitHoraireException;
import com.MBEMNOVA.Hopital.Exception.RessourceNotFoundException;
import com.MBEMNOVA.Hopital.Mapper.RendezVousMapper;
import com.MBEMNOVA.Hopital.Repository.HopitalRepository;
import com.MBEMNOVA.Hopital.Repository.PatientRepository;
import com.MBEMNOVA.Hopital.Repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final HopitalRepository hopitalRepository;
    private final PatientRepository patientRepository;
    private final RendezVousMapper rendezVousMapper;

    @Transactional
    public RendezVousResponseDTO creer(RendezVousRequestDTO dto) {
        Hopital hopital = hopitalRepository.findById(dto.getHopitalId())
                .orElseThrow(() -> new RessourceNotFoundException(
                        "Hôpital introuvable avec l'id : " + dto.getHopitalId()));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RessourceNotFoundException(
                        "Patient introuvable avec l'id : " + dto.getPatientId()));

        verifierCapacite(hopital);
        verifierConflitHoraire(hopital, dto.getDate(), dto.getHeure(), null);

        RendezVous rdv = RendezVous.builder()
                .patient(patient)
                .hopital(hopital)
                .date(dto.getDate())
                .heure(dto.getHeure())
                .statut(StatutRendezVous.CONFIRME)
                .build();

        return rendezVousMapper.toResponseDTO(rendezVousRepository.save(rdv));
    }

    @Transactional
    public RendezVousResponseDTO modifier(Long id, RendezVousUpdateDTO dto) {
        RendezVous rdv = trouverOuLever(id);

        if (rdv.getStatut() == StatutRendezVous.ANNULE) {
            throw new IllegalStateException("Impossible de modifier un rendez-vous annulé.");
        }

        verifierConflitHoraire(rdv.getHopital(), dto.getDate(), dto.getHeure(), rdv.getId());

        rdv.setDate(dto.getDate());
        rdv.setHeure(dto.getHeure());

        return rendezVousMapper.toResponseDTO(rendezVousRepository.save(rdv));
    }

    @Transactional
    public RendezVousResponseDTO annuler(Long id) {
        RendezVous rdv = trouverOuLever(id);
        rdv.setStatut(StatutRendezVous.ANNULE);
        return rendezVousMapper.toResponseDTO(rendezVousRepository.save(rdv));
    }

    @Transactional(readOnly = true)
    public List<RendezVousResponseDTO> listerTous() {
        return rendezVousRepository.findAll().stream()
                .map(rendezVousMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public RendezVousResponseDTO recupererParId(Long id) {
        return rendezVousMapper.toResponseDTO(trouverOuLever(id));
    }

    @Transactional(readOnly = true)
    public Map<String, Long> statistiquesParStatut() {
        return rendezVousRepository.findAll().stream()
                .collect(Collectors.groupingBy(r -> r.getStatut().name(), Collectors.counting()));
    }

    @Transactional(readOnly = true)
    public Map<String, Double> tauxOccupationParHopital() {
        return hopitalRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Hopital::getNom,
                        h -> {
                            long actifs = rendezVousRepository.countByHopitalAndStatut(h, StatutRendezVous.CONFIRME);
                            return h.getCapacite() == 0 ? 0.0 : (actifs * 100.0) / h.getCapacite();
                        }
                ));
    }

    @Transactional(readOnly = true)
    public Map<String, Long> rendezVousParLocalisation() {
        return rendezVousRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        r -> r.getHopital().getLocalisation(),
                        Collectors.counting()));
    }

    private void verifierCapacite(Hopital hopital) {
        long nbActifs = rendezVousRepository.countByHopitalAndStatut(hopital, StatutRendezVous.CONFIRME);
        if (nbActifs >= hopital.getCapacite()) {
            throw new CapaciteAtteinteException(
                    "L'hôpital \"" + hopital.getNom() + "\" a atteint sa capacité maximale ("
                            + hopital.getCapacite() + " rendez-vous actifs).");
        }
    }

    private void verifierConflitHoraire(Hopital hopital, LocalDate date, LocalTime heure, Long rdvIdAExclure) {
        List<RendezVous> actifs = rendezVousRepository.findByHopitalAndStatut(hopital, StatutRendezVous.CONFIRME);
        boolean conflit = actifs.stream()
                .filter(r -> rdvIdAExclure == null || !r.getId().equals(rdvIdAExclure))
                .anyMatch(r -> r.getDate().equals(date) && r.getHeure().equals(heure));

        if (conflit) {
            throw new ConflitHoraireException(
                    "Un rendez-vous existe déjà le " + date + " à " + heure
                            + " dans l'hôpital \"" + hopital.getNom() + "\".");
        }
    }

    private RendezVous trouverOuLever(Long id) {
        return rendezVousRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Rendez-vous introuvable avec l'id : " + id));
    }
}