package com.example.demo2.services.impl;


import com.example.communication.model.Anagrafica;
import com.example.communication.services.AnagraficaService;
import com.example.demo2.repositories.AnagraficaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AnagraficaServiceImpl implements AnagraficaService {

    @Autowired
    private AnagraficaRepository anagraficaRepository;

    @Override
    public Anagrafica newAnagrafica(Long id, String nome, String cognome) {
        Anagrafica anagrafica = new Anagrafica();
        anagrafica.setIdana(id);
        anagrafica.setNome(nome);
        anagrafica.setCognome(cognome);
        Date date = new Date();
        anagrafica.setDate_create(date);
        anagrafica.setDate_agg(date);
        anagraficaRepository.save(anagrafica);
        return anagrafica;
    }

    @Override
    public Optional<Anagrafica> updateAnagrafica(Long id, String nome, String cognome) {
        Optional<Anagrafica> anagrafica = anagraficaRepository.findById(id);
        Date date = new Date();

        anagrafica.ifPresent(a -> {
            a.setNome(nome);
            a.setCognome(cognome);
            a.setDate_agg(date);
            anagraficaRepository.save(a);
        });

        return anagrafica;
    }

    @Override
    public boolean deleteAnagrafica(Long id) {
        Optional<Anagrafica> anagrafica = anagraficaRepository.findById(id);
        AtomicBoolean condition = new AtomicBoolean(false);
        anagrafica.ifPresent(a->{
            anagraficaRepository.deleteById(id);
            condition.set(true);
        });
        return condition.get();
    }

    @Override
    public Optional<Anagrafica> anagraficaById(Long id) {
        return anagraficaRepository.findById(id);
    }


    @Override
    public List<Anagrafica> anagraficaAll(){
        return anagraficaRepository.findAll();
    }

}