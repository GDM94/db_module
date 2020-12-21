package com.example.demo2.services.memcached;

import com.example.communication.bean.IndirizziBean;

import java.io.IOException;
import java.util.Optional;

public interface IndirizzoMemcached {

    public Optional<IndirizziBean> findById(Long idaddress) throws IOException;
    void save(IndirizziBean indirizziBean) throws IOException;
    Boolean deleteById(Long idaddress);
}
