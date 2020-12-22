package com.example.demo2.services.impl;


import com.example.communication.bean.AnagraficaBean;
import com.example.communication.model.Anagrafica;

import com.example.demo2.repositories.AnagraficaRepository;
import com.example.demo2.services.mapper.AnagraficaMapper;
import com.example.demo2.services.AnagraficaService;
import com.example.demo2.services.memcached.AnagraficaMemcached;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AnagraficaServiceImpl implements AnagraficaService {

    @Autowired
    private AnagraficaRepository anagraficaRepository;


    @Autowired
    private AnagraficaMemcached anagraficaMemcached;

    private AnagraficaMapper anagraficaMapper= Mappers.getMapper(AnagraficaMapper.class);

    @Override
    public AnagraficaBean newAnagrafica(String nome, String cognome) throws IOException {
        Anagrafica anagrafica = new Anagrafica();
        anagrafica.setNome(nome);
        anagrafica.setCognome(cognome);
        Date date = new Date();
        anagrafica.setDate_create(date);
        anagrafica.setDate_agg(date);
        anagraficaRepository.save(anagrafica);
        AnagraficaBean anagraficaBean = anagraficaMapper.entityToBean(anagrafica);
        anagraficaMemcached.save(anagraficaBean);
        return anagraficaBean;
    }

    @Override
    public AnagraficaBean updateAnagrafica(Long id, String nome, String cognome) {
        Optional<Anagrafica> result_query = anagraficaRepository.findById(id);
        Date date = new Date();
        Anagrafica anagrafica = new Anagrafica();
        result_query.ifPresent(a -> {
            anagrafica.setIdana(a.getIdana());
            anagrafica.setNome(nome);
            anagrafica.setCognome(cognome);
            anagrafica.setDate_agg(date);
            anagrafica.setDate_create(a.getDate_create());
            anagraficaRepository.save(anagrafica);
        });

        if (result_query.isPresent()) {
            AnagraficaBean anagraficaBean = anagraficaMapper.entityToBean(anagrafica);
            return anagraficaBean;
        }else {
            return null;
        }
    }

    @Override
    public boolean deleteAnagrafica(Long id) throws IOException {
        AtomicBoolean condition = new AtomicBoolean(false);
        Optional<AnagraficaBean> result_query_mem = anagraficaMemcached.findById(id);
        result_query_mem.ifPresent(a->{
            anagraficaMemcached.deleteById(id);
        });
        Optional<Anagrafica> anagrafica = anagraficaRepository.findById(id);
        anagrafica.ifPresent(a->{
            anagraficaRepository.deleteById(id);
            condition.set(true);
        });
        return condition.get();
    }

    @Override
    public AnagraficaBean anagraficaById(Long id) throws IOException {
        Optional<AnagraficaBean> result_query_mem = anagraficaMemcached.findById(id);
        if (result_query_mem.isPresent()){
            AnagraficaBean anagraficaMem = new AnagraficaBean();
            result_query_mem.ifPresent(q->{
                anagraficaMem.setIdana(q.getIdana());
                anagraficaMem.setNome(q.getNome());
                anagraficaMem.setCognome(q.getCognome());
            });
            return anagraficaMem;
        } else{
            Optional<Anagrafica> result_query = anagraficaRepository.findById(id);
            final Anagrafica anagrafica = new Anagrafica();
            result_query.ifPresent(q->{
                anagrafica.setIdana(q.getIdana());
                anagrafica.setNome(q.getNome());
                anagrafica.setCognome(q.getCognome());
            });
            if (result_query.isPresent()) {
                AnagraficaBean anagraficaBean = anagraficaMapper.entityToBean(anagrafica);
                return anagraficaBean;
            }else {
                AnagraficaBean anagraficaBean = new AnagraficaBean();
                return anagraficaBean;
            }
        }

    }


    @Override
    public List<AnagraficaBean> anagraficaAll(){
        List<Anagrafica> result_query = anagraficaRepository.findAll();
        List<AnagraficaBean> anagraficaBeans = anagraficaMapper.listEntityToListBean(result_query);
        return anagraficaBeans;
    }

}