package com.example.demo2.services;




import com.example.communication.bean.RecapitiBean;

import java.util.List;
import java.util.Optional;

public interface RecapitiService {
    public RecapitiBean recapitoById(Long id);
    public List<RecapitiBean> recapitoAll();
    public RecapitiBean newRecapiti(Long idana, String tipo_recapito, String numero_recapito);
    public boolean deleteRecapiti(long id);
    public RecapitiBean updateRecapiti(Long id, String tipo_recapito, String numero_recapito);

}
