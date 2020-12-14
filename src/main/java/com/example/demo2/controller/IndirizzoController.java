package com.example.demo2.controller;

import com.example.communication.model.Indirizzo;
import com.example.demo2.services.impl.IndirizzoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/indirizzo")

public class IndirizzoController {
    @Autowired
    public IndirizzoServiceImpl indirizzoService;

    @GetMapping   // GET Method for reading operation
    public List<Indirizzo> getAllindirizzo() {

        return indirizzoService.indirizzoAll();
    }

    @GetMapping("/{id}")    // GET Method for Read operation
    public Optional<Indirizzo> getIndirizzoById(@PathVariable(value = "id") Long indId)
            throws Exception {

        return indirizzoService.indirizzoById(indId);
    }

    @PostMapping    // POST Method for Create operation
    public Indirizzo createIndirizzo(@RequestBody Indirizzo indirizzo) {

        return indirizzoService.newIndirizzo((long) indirizzo.getIdaddress(),
                (long)indirizzo.getIdana(),indirizzo.getDescrizione());
    }

    @PutMapping("/{id}")
    public Optional<Indirizzo> updateIndirizzo(
            @PathVariable(value = "id") long indId,  @RequestBody Indirizzo indirizzoDetail)
            throws Exception {

        return indirizzoService.updateIndirizzo(indId, indirizzoDetail.getDescrizione());
    }

    @DeleteMapping("/{id}")    // DELETE Method for Delete operation
    public boolean deleteIndirizzo(@PathVariable(value = "id") Long indId) throws Exception {
        return indirizzoService.deleteIndirizzo(indId);
    }
}
