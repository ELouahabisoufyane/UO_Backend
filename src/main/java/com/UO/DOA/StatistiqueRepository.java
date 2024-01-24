package com.UO.DOA;

import com.UO.Modele.Participant;
import com.UO.Modele.Statistique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatistiqueRepository extends JpaRepository<Statistique,Long> {
    @Query("select s from Statistique s where s.titre = :x")
    Statistique findByTitre(@Param("x") String titre);
}
