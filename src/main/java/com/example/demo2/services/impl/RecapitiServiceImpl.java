package com.example.demo2.services.impl;

import com.example.communication.bean.RecapitiBean;
import com.example.communication.model.Anagrafica;
import com.example.communication.model.RecapitiTelefonici;
import com.example.demo2.repositories.AnagraficaRepository;
import com.example.demo2.repositories.RecapitiRepository;
import com.example.demo2.services.RecapitiService;
import com.example.demo2.services.mapper.IndirizziMapper;
import com.example.demo2.services.mapper.RecapitiMapper;
import org.mapstruct.factory.Mappers;
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

    private final RecapitiMapper recapitiMapper= Mappers.getMapper(RecapitiMapper.class);

    @Override
    public RecapitiBean recapitoById(Long id){
        Optional<RecapitiTelefonici> result_query = recapitiRepository.findById(id);
        RecapitiTelefonici recapitiTelefonici = new RecapitiTelefonici();
        result_query.ifPresent(r->{
            recapitiTelefonici.setIdreca(r.getIdreca());
            recapitiTelefonici.setIdana(r.getIdana());
            recapitiTelefonici.setTipo_recapito(r.getTipo_recapito());
            recapitiTelefonici.setNumero_recapito(r.getNumero_recapito());
            Optional<Anagrafica> anagrafica = anagraficaRepository.findById(r.getIdana());
            anagrafica.ifPresent(a->{
                recapitiTelefonici.setAnagrafica(a);
            });
        });
        RecapitiBean recapitiBean = recapitiMapper.entityToBean(recapitiTelefonici);
        return recapitiBean;
    }

    @Override
    public List<RecapitiBean> recapitoAll(){
        List<RecapitiTelefonici> recapitiTelefonicis = recapitiRepository.findAll();
        List<RecapitiBean> recapitiBeans = recapitiMapper.listEntityToListBean(recapitiTelefonicis);
        return recapitiBeans;
    }

    @Override
    public RecapitiBean newRecapiti(Long id, Long idana, String tipo_recapito, String numero_recapito){
        RecapitiTelefonici recapitiTelefonici = new RecapitiTelefonici();
        recapitiTelefonici.setIdreca(id);
        recapitiTelefonici.setIdana(idana);
        recapitiTelefonici.setTipo_recapito(tipo_recapito);
        recapitiTelefonici.setNumero_recapito(numero_recapito);
        Optional<Anagrafica> anagrafica = anagraficaRepository.findById(idana);
        anagrafica.ifPresent(ana -> {
            recapitiTelefonici.setAnagrafica(ana);
            recapitiRepository.save(recapitiTelefonici);
        });
        RecapitiBean recapitiBean = recapitiMapper.entityToBean(recapitiTelefonici);
        return recapitiBean;
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
    public RecapitiBean updateRecapiti(Long id, String tipo_recapito, String numero_recapito){
        Optional<RecapitiTelefonici> result_query = recapitiRepository.findById(id);
        RecapitiTelefonici recapitiTelefonici = new RecapitiTelefonici();
        result_query.ifPresent(r -> {
            recapitiTelefonici.setIdreca(r.getIdreca());
            recapitiTelefonici.setIdana(r.getIdana());
            recapitiTelefonici.setTipo_recapito(tipo_recapito);
            recapitiTelefonici.setNumero_recapito(numero_recapito);
            Optional<Anagrafica> anagrafica = anagraficaRepository.findById(r.getIdana());
            anagrafica.ifPresent(a->{
                recapitiTelefonici.setAnagrafica(a);
            });
        });
        RecapitiBean recapitiBean = recapitiMapper.entityToBean(recapitiTelefonici);
        return recapitiBean;

    }
}
