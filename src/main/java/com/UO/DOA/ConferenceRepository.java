package com.UO.DOA;

import com.UO.Modele.Conference;

import com.UO.Modele.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

public interface ConferenceRepository extends JpaRepository<Conference,Long> {

    @Query("select p from Conference p where p.titre = :x")
    Conference findByTitre(@Param("x") String titre);

    @Query("select p from Conference p where p.titre like  CONCAT('%',:x,'%') ")
    Page<Conference> findAllByMotCle(@Param("x") String mc, Pageable pageable);

    @Query("select p from Conference p where p.date= :x")
    Conference findByDate(@Param("x") LocalDate d);

}
