package com.UO.Modele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "participants")
@Data@AllArgsConstructor@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Participant implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String civilite;
    private String rfidCardId;
    private String nom;
    private String prenom ;
    private Date ddn;
    private String email;
    private String tel;
    private String adresse;
    private String commune;
    private String codepos;
    private String fonction;
    private double montantUFC;
    private double montantADAUO;


    @Lob
    private byte[] photo;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participation> participations = new HashSet<>();

    // Constructeur(s), getters et setters

    // Méthodes pour gérer les participations
    public void addParticipation(Participation participation) {
        participations.add(participation);
        participation.setParticipant(this);
    }

    public void removeParticipation(Participation participation) {
        participations.remove(participation);
        participation.setParticipant(null);
    }
}
