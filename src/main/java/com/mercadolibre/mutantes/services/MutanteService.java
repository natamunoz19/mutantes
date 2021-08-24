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

    public Mutante obtenerMutanteByDna(String dna){
        ArrayList<Mutante> lista = mutanteRepository.findByDna(dna);
        Mutante m;
        if(lista.size() == 0){
            m = new Mutante();
            m.setDna(dna);
        }else{
            m=lista.get(0);
        }
        return m;
    }

    public ArrayList<Mutante> listAll(){
        return (ArrayList<Mutante>) mutanteRepository.findAll();
    }


}
