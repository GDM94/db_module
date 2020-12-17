package com.example.demo2.services.impl;


import com.example.communication.bean.IndirizziBean;
import com.example.communication.model.Anagrafica;
import com.example.communication.model.Indirizzo;
import com.example.demo2.repositories.AnagraficaRepository;
import com.example.demo2.repositories.IndirizzoRepository;
import com.example.demo2.services.IndrizzoService;
import com.example.demo2.services.mapper.AnagraficaMapper;
import com.example.demo2.services.mapper.IndirizziMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class IndirizzoServiceImpl implements IndrizzoService {

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    @Autowired
    private AnagraficaRepository anagraficaRepository;

    private final IndirizziMapper indirizziMapper= Mappers.getMapper(IndirizziMapper.class);

    @Override
    public IndirizziBean indirizzoById(Long id){
        Optional<Indirizzo> result_query = indirizzoRepository.findById(id);
        Indirizzo indirizzo= new Indirizzo();
        result_query.ifPresent(q->{
            indirizzo.setIdaddress(q.getIdaddress());
            indirizzo.setIdana(q.getIdana());
            indirizzo.setDescrizione(q.getDescrizione());
            indirizzo.setAnagrafica(q.getAnagrafica());
        });
        if (result_query.isPresent()){
            IndirizziBean indirizziBean = indirizziMapper.entityToBean(indirizzo);
            return indirizziBean;
        }else {
            IndirizziBean indirizziBean = null;
            return indirizziBean;
        }

    }

    @Override
    public List<IndirizziBean> indirizzoAll(){
        List<Indirizzo> indirizzos = indirizzoRepository.findAll();
        List<IndirizziBean> indirizziBeans = indirizziMapper.listEntityToListBean(indirizzos);
        return indirizziBeans;
    }

    @Override
    public IndirizziBean newIndirizzo(Long id, Long idana, String descrizione){
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setIdaddress(id);
        indirizzo.setIdana(idana);
        indirizzo.setDescrizione(descrizione);
        Date date = new Date();
        indirizzo.setDate_create(date);
        indirizzo.setDate_agg(date);
        Optional<Anagrafica> anagrafica = anagraficaRepository.findById(idana);
        anagrafica.ifPresent(ana -> {
            indirizzo.setAnagrafica(ana);
            indirizzoRepository.save(indirizzo);
        });
        IndirizziBean indirizziBean = indirizziMapper.entityToBean(indirizzo);
        return indirizziBean;
    }

    @Override
    public IndirizziBean updateIndirizzo(Long id, String descrizione){
        Optional<Indirizzo> result_query = indirizzoRepository.findById(id);
        Date date = new Date();
        Indirizzo indirizzo = new Indirizzo();
        result_query.ifPresent(i -> {
            indirizzo.setIdaddress(i.getIdaddress());
            indirizzo.setIdana(i.getIdana());
            indirizzo.setDescrizione(descrizione);
            indirizzo.setDate_agg(date);
            indirizzo.setDate_create(i.getDate_create());
            Optional<Anagrafica> anagrafica_query = anagraficaRepository.findById(i.getIdana());
            anagrafica_query.ifPresent(a->{
                indirizzo.setAnagrafica(a);
                indirizzoRepository.save(indirizzo);
            });
        });

        if (result_query.isPresent()){
            IndirizziBean indirizziBean = indirizziMapper.entityToBean(indirizzo);
            return indirizziBean;
        }else {
            IndirizziBean indirizziBean = null;
            return indirizziBean;
        }

    }

    @Override
    public boolean deleteIndirizzo(Long id){
        Optional<Indirizzo> indirizzo = indirizzoRepository.findById(id);
        AtomicBoolean condition = new AtomicBoolean(false);
        indirizzo.ifPresent(i->{
            indirizzoRepository.deleteById(id);
            condition.set(true);
        });
        return condition.get();
    }


}
