package com.example.demo2.services;




import com.example.communication.bean.IndirizziBean;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IndrizzoService {

    public IndirizziBean indirizzoById(Long id) throws IOException;
    public List<IndirizziBean> indirizzoAll();
    public IndirizziBean newIndirizzo(Long idana, String descrizione) throws IOException;
    public IndirizziBean updateIndirizzo(Long id, String descrizione) throws IOException;
    public boolean deleteIndirizzo(Long id) throws IOException;


}
