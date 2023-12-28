package com.UO.DOA;

import com.UO.Modele.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipantRepository extends JpaRepository<Participant,Long> {
    @Query("select p from Participant p where p.nom = :x")
    Participant findByName(@Param("x") String nom);
    @Query("select p from Participant p where p.nom like  CONCAT('%',:x,'%') ")
    Page<Participant> findAllByMotCle(@Param("x") String mc, Pageable pageable);
    @Query("select p from Participant p where p.rfidCardId = :x")
    Participant findByRfid(@Param("x") String nom);
}
