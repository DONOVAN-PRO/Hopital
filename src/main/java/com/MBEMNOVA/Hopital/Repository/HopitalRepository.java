package com.MBEMNOVA.Hopital.Repository;

import com.MBEMNOVA.Hopital.Entity.Hopital;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HopitalRepository extends JpaRepository<Hopital, Long> {

    List<Hopital> findByLocalisationIgnoreCase(String localisation);

    // Verrou pessimiste : bloque la ligne le temps de la transaction
    // pour empêcher deux créations simultanées de dépasser la capacité
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT h FROM Hopital h WHERE h.id = :id")
    Optional<Hopital> findByIdForUpdate(@Param("id") Long id);
}