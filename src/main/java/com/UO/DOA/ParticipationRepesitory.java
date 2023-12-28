package com.UO.DOA;

import com.UO.Modele.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ParticipationRepesitory extends JpaRepository<Participation,Participation.ParticipationId> {
    @Query("select p from Participation p where p.conference.id = :x")
    Set<Participation> findByConferenceId(@Param("x") Long conferenceId);

    @Query("SELECT p FROM Participation p WHERE p.participant.id = :participantId AND p.conference.id = :conferenceId")
    Participation findByParticipantIdAndConferenceId(Long participantId, Long conferenceId);

    void deleteByParticipantId(Long participantId);
    void deleteByConferenceId(Long conferenceId);

}
