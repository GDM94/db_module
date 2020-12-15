package com.example.demo2.services;


import com.example.communication.model.RecapitiTelefonici;

import java.util.List;
import java.util.Optional;

public interface RecapitiService {
    public Optional<RecapitiTelefonici> recapitoById(Long id);
    public List<RecapitiTelefonici> recapitoAll();
    public RecapitiTelefonici newRecapiti(Long id, Long idana, String tipo_recapito, String numero_recapito);
    public boolean deleteRecapiti(long id);
    public Optional<RecapitiTelefonici> updateRecapiti(Long id, String tipo_recapito, String numero_recapito);

}
