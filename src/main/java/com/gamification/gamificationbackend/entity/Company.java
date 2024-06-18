package com.gamification.gamificationbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "companies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer levelFormulaConstant;

    private Integer levelFormulaPower;

    @PrePersist
    private void prePersist() {
        levelFormulaConstant = 5;
        levelFormulaPower = 2;
    }
}
