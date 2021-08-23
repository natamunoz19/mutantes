package com.mercadolibre.mutantes.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mutante")
public class Mutante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dna")
    private String dna;

    @Column(name = "isMutant")
    private Integer isMutant;

    public Mutante() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }

    public Integer isMutant() {
        return isMutant;
    }

    public void setMutant(Integer mutant) {
        isMutant = mutant;
    }
}
