package com.mercadolibre.mutantes;

import com.mercadolibre.mutantes.controllers.MutanteController;
import com.mercadolibre.mutantes.model.Mutante;
import com.mercadolibre.mutantes.model.MutanteReporte;
import com.mercadolibre.mutantes.repositories.MutanteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


//@DataJpaTest
@SpringBootTest
class MutantesApplicationTests {


    @Autowired
    private MutanteRepository mutanteRepository;

    @Autowired
    private MutanteController mutanteController;

    @Test
    void contextLoads() {
    }


    @Test
    public void testMutantesClass(){
        Mutante mut1 = new Mutante();
        mut1.setId(1L);
        mut1.setDna("ATGCGC,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG");
        mut1.setMutant(0);

        assertEquals(1L, mut1.getId());
        assertEquals("ATGCGC,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG", mut1.getDna());
        assertEquals(0, mut1.isMutant());
    }

    @Test
    public void testMutantReportesClass(){
        MutanteReporte mr = new MutanteReporte();
        mr.setCount_mutant_dna(100L);
        mr.setCount_human_dna(500L);
        Float f = Float.valueOf(mr.getCount_mutant_dna())/Float.valueOf(mr.getCount_human_dna());
        mr.setRatio(f);

        assertEquals(100L, mr.getCount_mutant_dna());
        assertEquals(500L, mr.getCount_human_dna());
        assertEquals(Float.parseFloat("0.2"), mr.getRatio());
    }

    @Test
    public void testPatronesClass(){
        String dna0 = "AAACAA,CCCACC,GGGTGG,TTTGTT,AAACAA,CCCACC";
        String dna1 = "ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG";

        String[] cadena0 = dna0.split(",");
        String[] cadena1 = dna1.split(",");


        assertEquals(false, mutanteController.validarLetraHor(cadena0, 0, 0, 1));
        assertEquals(false, mutanteController.validarLetraHor(cadena1, 0, 0, 1));

        assertEquals(false, mutanteController.validarLetraVer(cadena0, 0, 0, 1));
        assertEquals(false, mutanteController.validarLetraVer(cadena1, 0, 0, 1));

        assertEquals(false, mutanteController.validarLetraDiag1(cadena0, 0, 0, 1));
        assertEquals(true, mutanteController.validarLetraDiag1(cadena1, 0, 0, 1));

        assertEquals(false, mutanteController.validarLetraDiag2(cadena0, 3, 3, 1));
        assertEquals(false, mutanteController.validarLetraDiag2(cadena1, 3, 3, 1));

        assertEquals(false, mutanteController.validarCadena(dna0));
        assertEquals(true, mutanteController.validarCadena(dna1));
    }

}
