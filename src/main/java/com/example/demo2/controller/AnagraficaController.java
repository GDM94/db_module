package com.example.demo2.controller;

import com.example.communication.model.Anagrafica;
import com.example.demo2.services.impl.AnagraficaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/anagrafica")
public class AnagraficaController {
    @Autowired
    public AnagraficaServiceImpl anagraficaService;


    @GetMapping   // GET Method for reading operation
    public List<Anagrafica> getAllanagrafica() {

        return anagraficaService.anagraficaAll();
    }

    @GetMapping("/{id}")    // GET Method for Read operation
    public Optional<Anagrafica> getAnagraficaById(@PathVariable(value = "id") Long anaId)
            throws Exception {

        return anagraficaService.anagraficaById(anaId);
    }


    @PostMapping    // POST Method for Create operation
    public Anagrafica createAnagrafica(@RequestBody Anagrafica ana) {
        long id = ana.getIdana();
        String nome = ana.getNome();
        String cognome = ana.getCognome();
        return anagraficaService.newAnagrafica(id, nome, cognome);
    }

    @PutMapping("/{id}")
    public Optional<Anagrafica> updateAnagrafica(
            @PathVariable(value = "id") long anaId,  @RequestBody Anagrafica anaDetails)
            throws Exception {

        return anagraficaService.updateAnagrafica(anaId, anaDetails.getNome(), anaDetails.getCognome());
    }

    @DeleteMapping("/{id}")    // DELETE Method for Delete operation
    public boolean deleteAnagrafica(@PathVariable(value = "id") Long anaID) throws Exception {
        return anagraficaService.deleteAnagrafica(anaID);
    }


}
