package com.example.demo2.services.memchaced;

import com.example.communication.bean.AnagraficaBean;

public interface AnagraficaMemcached {

    public AnagraficaBean findById(Long idana);
    void save(AnagraficaBean anagraficaBean);
}
