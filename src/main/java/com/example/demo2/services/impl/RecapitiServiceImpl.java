package com.example.demo2.services.impl;

import com.example.communication.model.Anagrafica;
import com.example.communication.model.RecapitiTelefonici;
import com.example.demo2.repositories.AnagraficaRepository;
import com.example.demo2.repositories.RecapitiRepository;
import com.example.demo2.services.RecapitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class RecapitiServiceImpl implements RecapitiService {
    @Autowired
    private RecapitiRepository recapitiRepository;

    @Autowired
    private AnagraficaRepository anagraficaRepository;

    @Override
    public Optional<RecapitiTelefonici> recapitoById(Long id){
        return recapitiRepository.findById(id);
    }

    @Override
    public List<RecapitiTelefonici> recapitoAll(){
        return recapitiRepository.findAll();
    }

    @Override
    public RecapitiTelefonici newRecapiti(Long id, Long idana, String tipo_recapito, String numero_recapito){
        RecapitiTelefonici recapitiTelefonici = new RecapitiTelefonici();
        recapitiTelefonici.setIdreca(id);
        recapitiTelefonici.setIdana(idana);
        recapitiTelefonici.setTipo_recapito(tipo_recapito);
        recapitiTelefonici.setNumero_recapito(numero_recapito);
        Optional<Anagrafica> anagrafica = anagraficaRepository.findById(idana);
        anagrafica.ifPresent(ana -> {
            recapitiTelefonici.setAnagrafica(ana);
        });

        recapitiRepository.save(recapitiTelefonici);
        return recapitiTelefonici;
    }

    @Override
    public boolean deleteRecapiti(long id){
        Optional<RecapitiTelefonici> recapitiTelefonici = recapitiRepository.findById(id);
        AtomicBoolean condition = new AtomicBoolean(false);
        recapitiTelefonici.ifPresent(r -> {
            recapitiRepository.deleteById(id);
            condition.set(true);
        });

        return condition.get();
    }

    @Override
    public Optional<RecapitiTelefonici> updateRecapiti(Long id, String tipo_recapito, String numero_recapito){
        Optional<RecapitiTelefonici> recapitiTelefonici = recapitiRepository.findById(id);
        recapitiTelefonici.ifPresent(a -> {
            a.setTipo_recapito(tipo_recapito);
            a.setNumero_recapito(numero_recapito);
        });

        return recapitiTelefonici;

    }
}
