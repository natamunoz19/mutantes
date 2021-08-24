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
public class MutanteController {


    @Autowired
    MutanteService service;

    @GetMapping("/stats")
    public MutanteReporte generarReporte(){
        MutanteReporte mr = new MutanteReporte();
        List<Mutante> lista = service.listAll();
        mr.setCount_mutant_dna(lista.stream().filter(m -> m.isMutant() == 1).count());
        mr.setCount_human_dna(lista.stream().filter(m -> m.isMutant() == 0).count());
        if(mr.getCount_human_dna() != 0){
            mr.setRatio(Float.valueOf(mr.getCount_mutant_dna())/Float.valueOf(mr.getCount_human_dna()));
        }else{
            mr.setRatio(Float.valueOf(1));
        }
        return mr;
    }

    @PostMapping("/mutant")
    public ResponseEntity validarMutante(@RequestBody String dna){
        boolean ok = this.validarCadena(dna.substring(dna.indexOf("[")+1, dna.indexOf("]")));
        if(ok){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public boolean validarCadena(String dna){
        Boolean valido = false;
        Mutante mu = service.obtenerMutanteByDna(dna);

        if(mu.getId() == null){
            String[] dnaArray = dna.replace(" ", "").split(",");
            int n = dnaArray.length;
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    if(j<n-3){
                        valido = validarLetraHor(dnaArray, i, j, 1);
                        if(valido){ break; }

                        if(i<n-3){
                            valido = validarLetraDiag1(dnaArray, i, j, 1);
                            if(valido){ break; }
                        }

                        if(i>3){
                            valido = validarLetraDiag2(dnaArray, i, j, 1);
                            if(valido){ break; }
                        }
                    }
                    if(i<n-3){
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
        }else{
            if(mu.isMutant()==0){
                valido = false;
            }else{
                valido = true;
            }
        }
        return valido;
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
