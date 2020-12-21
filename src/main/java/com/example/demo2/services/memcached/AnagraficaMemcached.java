package com.example.demo2.services.memcached;

import com.example.communication.bean.AnagraficaBean;

import java.io.IOException;
import java.util.Optional;

public interface AnagraficaMemcached {

    public Optional<AnagraficaBean> findById(Long idana) throws IOException;
    void save(AnagraficaBean anagraficaBean) throws IOException;
    Boolean deleteById(Long idana);

}
