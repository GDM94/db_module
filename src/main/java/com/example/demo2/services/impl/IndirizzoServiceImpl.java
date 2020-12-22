package com.example.demo2.services.impl;



import com.example.communication.bean.AnagraficaBean;
import com.example.communication.bean.IndirizziBean;
import com.example.communication.model.Anagrafica;
import com.example.communication.model.Indirizzo;
import com.example.demo2.DbModuleApplication;
import com.example.demo2.repositories.AnagraficaRepository;
import com.example.demo2.repositories.IndirizzoRepository;
import com.example.demo2.services.IndrizzoService;
import com.example.demo2.services.mapper.AnagraficaMapper;
import com.example.demo2.services.mapper.IndirizziMapper;
import com.example.demo2.services.memcached.AnagraficaMemcached;
import com.example.demo2.services.memcached.IndirizzoMemcached;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private IndirizzoMemcached indirizzoMemcached;

    @Autowired
    private AnagraficaMemcached anagraficaMemcached;

    private final IndirizziMapper indirizziMapper= Mappers.getMapper(IndirizziMapper.class);
    private AnagraficaMapper anagraficaMapper= Mappers.getMapper(AnagraficaMapper.class);

    private static final Logger logger = LoggerFactory.getLogger(DbModuleApplication.class);

    @Override
    public IndirizziBean indirizzoById(Long id) throws IOException {
        Optional<IndirizziBean> result_query_mem = indirizzoMemcached.findById(id);
        if (result_query_mem.isPresent()){
            IndirizziBean indirizziBean = new IndirizziBean();
            result_query_mem.ifPresent(q->{
                indirizziBean.setIdaddress(q.getIdaddress());
                indirizziBean.setIdana(q.getIdana());
                indirizziBean.setDescrizione(q.getDescrizione());
                Optional<AnagraficaBean> anagraficaBean = null;
                try {
                    anagraficaBean = anagraficaMemcached.findById(q.getIdana());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                IndirizziBean indirizziBean = new IndirizziBean();
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

    @Override
    public IndirizziBean newIndirizzo( Long idana, String descrizione) throws IOException {
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setIdana(idana);
        indirizzo.setDescrizione(descrizione);
        Date date = new Date();
        indirizzo.setDate_create(date);
        indirizzo.setDate_agg(date);
        Optional<AnagraficaBean> anagraficaBean = anagraficaMemcached.findById(idana);
        if (anagraficaBean.isPresent()){
            anagraficaBean.ifPresent(a->{
                Anagrafica anagrafica = anagraficaMapper.beanToEntity(a);
                indirizzo.setAnagrafica(anagrafica);
            });
            indirizzoRepository.save(indirizzo);
            IndirizziBean indirizziBean = indirizziMapper.entityToBean(indirizzo);
            indirizzoMemcached.save(indirizziBean);
            return indirizziBean;

        }else {
            Optional<Anagrafica> anagrafica = anagraficaRepository.findById(idana);
            if (anagrafica.isPresent()){
                anagrafica.ifPresent(ana -> {
                    indirizzo.setAnagrafica(ana);
                    AnagraficaBean anagraficaBean1 = anagraficaMapper.entityToBean(ana);
                    try {
                        anagraficaMemcached.save(anagraficaBean1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                indirizzoRepository.save(indirizzo);
                IndirizziBean indirizziBean = indirizziMapper.entityToBean(indirizzo);
                indirizzoMemcached.save(indirizziBean);
                return indirizziBean;
            }else{
                IndirizziBean indirizziBean = new IndirizziBean();
                return indirizziBean;
            }
        }
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
    public boolean deleteIndirizzo(Long id) throws IOException {

        Optional<IndirizziBean> result_query_mem = indirizzoMemcached.findById(id);
        result_query_mem.ifPresent(a->{
            indirizzoMemcached.deleteById(id);
        });

        Optional<Indirizzo> indirizzo = indirizzoRepository.findById(id);
        AtomicBoolean condition = new AtomicBoolean(false);
        indirizzo.ifPresent(i->{
            indirizzoRepository.deleteById(id);
            condition.set(true);
        });
        return condition.get();
    }


}
