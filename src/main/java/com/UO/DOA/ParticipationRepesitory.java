package com.UO.DOA;

import com.UO.Modele.Participant;
import com.UO.Modele.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ParticipationRepesitory extends JpaRepository<Participation,Participation.ParticipationId> {
    @Query("select p.participant from Participation p where p.conference.id = :x AND p.etat=TRUE ")
    Page<Participant> findByConferenceId(@Param("x") Long conferenceId, Pageable pageable);

    @Query("select p.participant from Participation p where p.conference.id = :x  ")
    List<Participant> findByConferencesId(@Param("x") Long conferenceId);

    @Query("SELECT p FROM Participation p WHERE p.conference.id= :idConference AND p.etat = true")
    List<Participation> findParticipantsWithEtatTrue(@Param("idConference") Long idConference);

    @Query("SELECT p FROM Participation p WHERE p.conference.id= :idConference AND p.etat = false")
    List<Participation> findParticipantsWithEtatFalse(@Param("idConference") Long idConference);
    @Query("SELECT p FROM Participation p WHERE p.conference.id= :idConference AND p.etat = true AND p.how=true")
    List<Participation> findParticipantsWithHowTrue(@Param("idConference") Long idConference);
    @Query("SELECT p FROM Participation p WHERE p.conference.id= :idConference AND p.etat = true AND p.how=false")
    List<Participation> findParticipantsWithHowFlase(@Param("idConference") Long idConference);
    @Query("select p.participant from Participation p where p.conference.id = :x AND p.etat=false ")
    Page<Participant> findByPageConferenceId(@Param("x") Long conferenceId, Pageable pageable);

    @Query("SELECT p FROM Participation p WHERE p.participant.id = :participantId AND p.conference.id = :conferenceId")
    Participation findByParticipantIdAndConferenceId(Long participantId, Long conferenceId);

    void deleteByParticipantId(Long participantId);
    void deleteByConferenceId(Long conferenceId);

}
