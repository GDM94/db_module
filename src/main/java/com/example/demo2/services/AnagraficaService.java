package com.example.demo2.services;





import com.example.communication.bean.AnagraficaBean;

import java.util.List;
import java.util.Optional;

public interface AnagraficaService {

    public AnagraficaBean newAnagrafica(Long id, String nome, String cognome);
    public boolean deleteAnagrafica(Long id);
    public AnagraficaBean updateAnagrafica(Long id, String nome, String cognome);
    public AnagraficaBean anagraficaById(Long id);
    public List<AnagraficaBean> anagraficaAll();

}

