package com.example.demo2.services.impl;


import com.example.communication.bean.AnagraficaBean;
import com.example.communication.bean.IndirizziBean;
import com.example.communication.model.Anagrafica;
import com.example.communication.model.Indirizzo;
import com.example.demo2.DbModuleApplication;
import com.example.demo2.repositories.AnagraficaRepository;
import com.example.demo2.repositories.IndirizzoRepository;
import com.example.demo2.repositories.redis.AnagraficaRepositoryRedis;
import com.example.demo2.repositories.redis.IndirizziRepositoryRedis;
import com.example.demo2.services.IndrizzoService;
import com.example.demo2.services.mapper.AnagraficaMapper;
import com.example.demo2.services.mapper.IndirizziMapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private IndirizziRepositoryRedis indirizziRepositoryRedis;

    @Autowired
    private AnagraficaRepositoryRedis anagraficaRepositoryRedis;

    private final IndirizziMapper indirizziMapper= Mappers.getMapper(IndirizziMapper.class);
    private AnagraficaMapper anagraficaMapper= Mappers.getMapper(AnagraficaMapper.class);

    private static final Logger logger = LoggerFactory.getLogger(DbModuleApplication.class);

    @Override
    public IndirizziBean indirizzoById(Long id){
        Optional<IndirizziBean> result_query_redis = indirizziRepositoryRedis.findById(id);
        if (result_query_redis.isPresent()){
            IndirizziBean indirizziBean = new IndirizziBean();
            result_query_redis.ifPresent(q->{
                indirizziBean.setIdaddress(q.getIdaddress());
                indirizziBean.setIdana(q.getIdana());
                indirizziBean.setDescrizione(q.getDescrizione());
                Optional<AnagraficaBean> anagraficaBean = anagraficaRepositoryRedis.findById(q.getIdana());
                anagraficaBean.ifPresent(a->{
                    indirizziBean.setAnagrafica(a);
                });
            });
            return indirizziBean;
        }else{
            Optional<Indirizzo> result_query = indirizzoRepository.findById(id);
            Indirizzo indirizzo= new Indirizzo();
            result_query.ifPresent(q->{
                indirizzo.setIdaddress(q.getIdaddress());
                indirizzo.setIdana(q.getIdana());
                indirizzo.setDescrizione(q.getDescrizione());
                Optional<Anagrafica> anagrafica = anagraficaRepository.findById(q.getIdana());
                anagrafica.ifPresent(a->{
                    indirizzo.setAnagrafica(a);
                });
            });

            logger.info(indirizzo.toString());
            if (result_query.isPresent()){
                IndirizziBean indirizziBean = indirizziMapper.entityToBean(indirizzo);
                return indirizziBean;
            }else {
                IndirizziBean indirizziBean = null;
                return indirizziBean;
            }
        }


    }

    @Override
    public List<IndirizziBean> indirizzoAll(){
        List<Indirizzo> indirizzos = indirizzoRepository.findAll();
        indirizzos.forEach(i->{
            Optional<Anagrafica> anagrafica = anagraficaRepository.findById(i.getIdana());
            anagrafica.ifPresent(a->{
                i.setAnagrafica(a);
            });
        });
        logger.info(indirizzos.toString());
        List<IndirizziBean> indirizziBeans = indirizziMapper.listEntityToListBean(indirizzos);
        return indirizziBeans;
    }

    /*
    List<Indirizzo> indirizzos = indirizzoRepository.findAll()
    indirizzos.forEach(i -> {
        Optional<Anagrafica> ana = anagraficaRepository.findById(i.getIdana);
        if(anagrafica.getId() == id) {
        idaddress_toDelete = i.getId();
        indirizzoRepository.deleteById(idaddress_toDelete)


         */

    @Override
    public IndirizziBean newIndirizzo( Long idana, String descrizione){
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setIdana(idana);
        indirizzo.setDescrizione(descrizione);
        Date date = new Date();
        indirizzo.setDate_create(date);
        indirizzo.setDate_agg(date);
        Optional<AnagraficaBean> anagraficaBean = anagraficaRepositoryRedis.findById(idana);
        if (anagraficaBean.isPresent()){
            anagraficaBean.ifPresent(a->{
                Anagrafica anagrafica = anagraficaMapper.beanToEntity(a);
                indirizzo.setAnagrafica(anagrafica);
            });
        }else {
            Optional<Anagrafica> anagrafica = anagraficaRepository.findById(idana);
            anagrafica.ifPresent(ana -> {
                indirizzo.setAnagrafica(ana);
                AnagraficaBean anagraficaBean1 = anagraficaMapper.entityToBean(ana);
                anagraficaRepositoryRedis.save(anagraficaBean1);
            });
        }
        indirizzoRepository.save(indirizzo);
        IndirizziBean indirizziBean = indirizziMapper.entityToBean(indirizzo);
        indirizziRepositoryRedis.save(indirizziBean);
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
