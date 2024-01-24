package com.UO.Modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
@Entity
@Table(name = "statistiques")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistique implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String titre;
    private LocalDate date;
    private String conferencier;
    private int nbPrresence;
    private int nbAbssence;
    private int parCarte;
    private int parGest ;



}
