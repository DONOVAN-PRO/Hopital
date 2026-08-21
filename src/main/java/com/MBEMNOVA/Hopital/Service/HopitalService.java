package com.MBEMNOVA.Hopital.Service;

import com.MBEMNOVA.Hopital.DTOs.HopitalRequestDTO;
import com.MBEMNOVA.Hopital.DTOs.HopitalResponseDTO;
import com.MBEMNOVA.Hopital.Entity.Hopital;
import com.MBEMNOVA.Hopital.Entity.StatutRendezVous;
import com.MBEMNOVA.Hopital.Exception.RessourceNotFoundException;
import com.MBEMNOVA.Hopital.Mapper.HopitalMapper;
import com.MBEMNOVA.Hopital.Repository.HopitalRepository;
import com.MBEMNOVA.Hopital.Repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HopitalService {

    private final HopitalRepository hopitalRepository;
    private final RendezVousRepository rendezVousRepository;
    private final HopitalMapper hopitalMapper;

    @Transactional
    public HopitalResponseDTO creer(HopitalRequestDTO dto) {
        Hopital hopital = hopitalMapper.toEntity(dto);
        Hopital saved = hopitalRepository.save(hopital);
        return hopitalMapper.toResponseDTO(saved, 0);
    }

    @Transactional(readOnly = true)
    public List<HopitalResponseDTO> listerTous() {
        return hopitalRepository.findAll().stream()
                .map(h -> hopitalMapper.toResponseDTO(h, rdvActifs(h)))
                .toList();
    }

    @Transactional(readOnly = true)
    public HopitalResponseDTO recupererParId(Long id) {
        Hopital hopital = trouverOuLever(id);
        return hopitalMapper.toResponseDTO(hopital, rdvActifs(hopital));
    }

    /**
     * Hôpitaux proposables pour un patient :
     * - jamais les hôpitaux pleins
     * - priorité à ceux de la localisation du patient
     * - sinon, tous les hôpitaux disponibles, peu importe la localisation
     */
    @Transactional(readOnly = true)
    public List<HopitalResponseDTO> getHopitauxDisponibles(String localisationPatient) {
        List<Hopital> disponibles = hopitalRepository.findAll().stream()
                .filter(h -> rdvActifs(h) < h.getCapacite())
                .sorted(Comparator.comparing(Hopital::getNom))
                .toList();

        List<Hopital> resultat = disponibles;
        if (localisationPatient != null && !localisationPatient.isBlank()) {
            List<Hopital> memeLocalisation = disponibles.stream()
                    .filter(h -> h.getLocalisation().equalsIgnoreCase(localisationPatient))
                    .toList();
            if (!memeLocalisation.isEmpty()) {
                resultat = memeLocalisation;
            }
        }

        return resultat.stream()
                .map(h -> hopitalMapper.toResponseDTO(h, rdvActifs(h)))
                .toList();
    }

    // --- helpers internes ---

    private long rdvActifs(Hopital hopital) {
        return rendezVousRepository.countByHopitalAndStatut(hopital, StatutRendezVous.CONFIRME);
    }

    private Hopital trouverOuLever(Long id) {
        return hopitalRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Hôpital introuvable avec l'id : " + id));
    }

    // TODO : modification et suppression, à compléter plus tard
}