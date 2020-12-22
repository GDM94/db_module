package com.example.demo2.services;





import com.example.communication.bean.AnagraficaBean;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AnagraficaService {

    public AnagraficaBean newAnagrafica(String nome, String cognome) throws IOException;
    public boolean deleteAnagrafica(Long id) throws IOException;
    public AnagraficaBean updateAnagrafica(Long id, String nome, String cognome) throws IOException;
    public AnagraficaBean anagraficaById(Long id) throws IOException;
    public List<AnagraficaBean> anagraficaAll();

}

