package com.example.demo2.services.memcached;

import com.example.communication.bean.IndirizziBean;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IndirizzoMemcached {

    Optional<IndirizziBean> findById(Long idaddress) throws IOException;
    void save(IndirizziBean indirizziBean) throws IOException;
    Boolean deleteById(Long idaddress);
    void update(IndirizziBean indirizziBeann) throws IOException;
    void deleteByIdana(Long idana);
}
