package com.UO.Modele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "conférences")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conference implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String titre;
    private Date date;
    private String conferencier;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participation> participations = new HashSet<>();

    // Constructeur(s), getters et setters

    // Méthodes pour gérer les participations
    public void addParticipation(Participation participation) {
        participations.add(participation);
        participation.setConference(this);
    }

    public void removeParticipation(Participation participation) {
        participations.remove(participation);
        participation.setConference(null);
    }

}
