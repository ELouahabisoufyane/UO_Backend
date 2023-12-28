package com.UO.Modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "participation")
@Data
public class Participation  {

    @EmbeddedId
    private ParticipationId id = new ParticipationId();


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("participantId")
    private Participant participant ;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("conferenceId")
    private Conference conference ;


    private  boolean etat=false;

    // Constructeur(s), getters et setters

    @Embeddable
    @Data
    public static class ParticipationId implements Serializable {
        @Column(name = "participant_id")
        private Long participantId;

        @Column(name = "conference_id")
        private Long conferenceId;

    }
}
