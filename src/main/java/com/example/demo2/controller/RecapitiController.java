package com.example.demo2.controller;


import com.example.communication.model.RecapitiTelefonici;
import com.example.demo2.services.impl.RecapitiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recapiti")
public class RecapitiController {

    @Autowired
    public RecapitiServiceImpl recapitiService;

    @GetMapping   // GET Method for reading operation
    public List<RecapitiTelefonici> getAllrecapiti() {

        return recapitiService.recapitoAll();
    }

    @GetMapping("/{id}")    // GET Method for Read operation
    public Optional<RecapitiTelefonici> getRecapitiById(@PathVariable(value = "id") Long recaId)
            throws Exception {

        return recapitiService.recapitoById(recaId);
    }

    @PostMapping    // POST Method for Create operation
    public RecapitiTelefonici createRecapiti(@RequestBody RecapitiTelefonici recapiti) {
        return recapitiService.newRecapiti((long) recapiti.getIdreca(),
                (long) recapiti.getIdana(),
                recapiti.getTipo_recapito(),
                recapiti.getNumero_recapito());
    }

    @PutMapping("/{id}")
    public Optional<RecapitiTelefonici> updateRecapiti(
            @PathVariable(value = "id") long recaId,  @RequestBody RecapitiTelefonici recaDetails)
            throws Exception {

        return recapitiService.updateRecapiti(recaId, recaDetails.getTipo_recapito(), recaDetails.getNumero_recapito());
    }

    @DeleteMapping("/{id}")    // DELETE Method for Delete operation
    public Boolean deleteRecapiti(@PathVariable(value = "id") Long recaID) throws Exception {
        return recapitiService.deleteRecapiti(recaID);
    }

}
