package com.example.demo2.services.memchaced;

import com.example.communication.bean.AnagraficaBean;

import java.io.IOException;

public interface AnagraficaMemcached {

    public AnagraficaBean findById(Long idana) throws IOException;
    void save(AnagraficaBean anagraficaBean) throws IOException;
}
