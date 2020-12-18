package com.example.demo2.services;




import com.example.communication.bean.IndirizziBean;

import java.util.List;
import java.util.Optional;

public interface IndrizzoService {

    public IndirizziBean indirizzoById(Long id);
    public List<IndirizziBean> indirizzoAll();
    public IndirizziBean newIndirizzo(Long idana, String descrizione);
    public IndirizziBean updateIndirizzo(Long id, String descrizione);
    public boolean deleteIndirizzo(Long id);


}
