package com.mercadolibre.mutantes.repositories;

import com.mercadolibre.mutantes.model.Mutante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MutanteRepository extends JpaRepository<Mutante, Long> {
    ArrayList<Mutante> findByDna(String dna);
}
