package com.mercadolibre.mutantes.controllers;

import com.mercadolibre.mutantes.model.Mutante;
import com.mercadolibre.mutantes.model.MutanteReporte;
import com.mercadolibre.mutantes.repositories.MutanteRepository;
import com.mercadolibre.mutantes.services.MutanteService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mercadolibre")
class MutantesController {


    @Autowired
    MutanteService service;

    @GetMapping("/stats")
    public MutanteReporte generarReporte(){
        MutanteReporte mr = new MutanteReporte();
        List<Mutante> lista = service.listAll();
        mr.setCount_mutant_dna(lista.stream().filter(m -> m.isMutant() == 1).count());
        mr.setCount_human_dna(lista.stream().filter(m -> m.isMutant() == 0).count());
        if(mr.getCount_human_dna() != 0){
            mr.setRatio(Float.valueOf(mr.getCount_mutant_dna()/mr.getCount_human_dna()));
        }else{
            mr.setRatio(Float.valueOf(1));
        }
        return mr;
    }

    @PostMapping("/mutant")
    public ResponseEntity validarMutante(@RequestBody String dna){
        Boolean valido = false;
        String dnaString = dna.substring(dna.indexOf("[")+1, dna.indexOf("]"));
        String dnaArray[];
        Mutante mu = this.getMutante(dnaString);

        if(mu.getId() == null){
            dnaArray = dnaString.replace(" ", "").split(",");
            for(int i=0; i<dnaArray.length; i++){
                for(int j=0; j<dnaArray[i].length(); j++){
                    if(j<dnaArray[i].length()-3){
                        valido = validarLetraHor(dnaArray, i, j, 1);
                        if(valido){ break; }

                        if(i<dnaArray.length-3){
                            valido = validarLetraDiag1(dnaArray, i, j, 1);
                            if(valido){ break; }
                        }

                        if(i>3){
                            valido = validarLetraDiag2(dnaArray, i, j, 1);
                            if(valido){ break; }
                        }
                    }
                    if(i<dnaArray.length-3){
                        valido = validarLetraVer(dnaArray, i, j, 1);
                        if(valido){ break; }
                    }
                }
                if(valido){ break; }
            }
            if(valido){
                mu.setMutant(1);
            }else{
                mu.setMutant(0);
            }
            service.guardarUsuario(mu);
        }
        if(mu.isMutant() == 1){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        //return mu.isMutant();
    }

    public Mutante getMutante(String dna){
        ArrayList<Mutante> lista = service.obtenerMutanteByDna(dna);
        Mutante m;
        if(lista.size() == 0){
            m = new Mutante();
            m.setDna(dna);
        }else{
            m=lista.get(0);
        }
        return m;
    }

    public boolean validarLetraHor(String[] dna, int posa, int posb, int cant){
        boolean bandera = false;
        if(dna[posa].substring(posb, posb+1).compareTo(dna[posa].substring(posb+cant, posb+cant+1)) == 0){
            if(cant==3){
                bandera=true;
            }else{
                bandera = validarLetraHor(dna, posa, posb, cant+1);
            }
        }
        return bandera;
    }

    public boolean validarLetraVer(String[] dna, int posa, int posb, int cant){
        boolean bandera = false;
        if(dna[posa].substring(posb, posb+1).compareTo(dna[posa+cant].substring(posb, posb+1)) == 0){
            if(cant==3){
                bandera=true;
            }else{
                bandera = validarLetraVer(dna, posa, posb, cant+1);
            }
        }
        return bandera;
    }

    public boolean validarLetraDiag1(String[] dna, int posa, int posb, int cant){
        boolean bandera = false;
        if(dna[posa].substring(posb, posb+1).compareTo(dna[posa+cant].substring(posb+cant, posb+cant+1)) == 0){
            if(cant==3){
                bandera=true;
            }else{
                bandera = validarLetraDiag1(dna, posa, posb, cant+1);
            }
        }
        return bandera;
    }

    public boolean validarLetraDiag2(String[] dna, int posa, int posb, int cant){
        boolean bandera = false;
        if(dna[posa].substring(posb, posb+1).compareTo(dna[posa-cant].substring(posb+cant, posb+cant+1)) == 0){
            if(cant==3){
                bandera=true;
            }else{
                bandera = validarLetraDiag2(dna, posa, posb, cant+1);
            }
        }
        return bandera;
    }

}
