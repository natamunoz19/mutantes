package com.mercadolibre.mutantes.services;

import com.mercadolibre.mutantes.model.Mutante;
import com.mercadolibre.mutantes.repositories.MutanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MutanteService {
    @Autowired
    MutanteRepository mutanteRepository;

    public Mutante guardarUsuario(Mutante m){
        return mutanteRepository.save(m);
    }

    public ArrayList<Mutante> obtenerMutanteByDna(String dna){
        return (ArrayList<Mutante>) mutanteRepository.findByDna(dna);
    }

    public ArrayList<Mutante> listAll(){
        return (ArrayList<Mutante>) mutanteRepository.findAll();
    }

    public Long countMutantes(){
        return mutanteRepository.findAll().stream().filter(m -> m.isMutant() == 1).count();
    }

    public Long countHumanos(){
        return mutanteRepository.findAll().stream().filter(m -> m.isMutant() == 0).count();
    }
}
