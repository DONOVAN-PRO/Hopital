package com.MBEMNOVA.Hopital.Repository;

import com.MBEMNOVA.Hopital.Entity.Hopital;
import com.MBEMNOVA.Hopital.Entity.RendezVous;
import com.MBEMNOVA.Hopital.Entity.StatutRendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    // Rendez-vous actifs d'un hôpital -> utilisé pour vérifier le conflit d'horaire
    List<RendezVous> findByHopitalAndStatut(Hopital hopital, StatutRendezVous statut);

    // Comptage direct -> utilisé pour vérifier la capacité
    long countByHopitalAndStatut(Hopital hopital, StatutRendezVous statut);

    // Alternative disponible si besoin, non utilisée actuellement dans le service
    boolean existsByHopitalAndDateAndHeureAndStatut(
            Hopital hopital, LocalDate date, LocalTime heure, StatutRendezVous statut);

    List<RendezVous> findByStatut(StatutRendezVous statut);

    List<RendezVous> findByPatientId(Long patientId);
}