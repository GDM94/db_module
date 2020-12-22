package com.example.demo2.services.impl.memcached;

import com.example.communication.bean.IndirizziBean;
import com.example.communication.model.Indirizzo;
import com.example.demo2.repositories.IndirizzoRepository;
import com.example.demo2.services.memcached.IndirizzoMemcached;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class IndirizzoMemcachedImpl implements IndirizzoMemcached {

    @Autowired
    IndirizzoRepository indirizzoRepository;

    @Autowired
    MemcachedClient memcachedClient;

    @Value("${memcached.cache.prefix.indirizzo}")
    private String prefix;

    @Value("${memcached.cache.expiration}")
    private int expiration;

    private static final Logger logger = LoggerFactory.getLogger(IndirizzoMemcachedImpl.class);
    private final ObjectMapper Obj = new ObjectMapper();

    public Optional<IndirizziBean> findById(Long idaddress) {
        String key = prefix+"_"+idaddress.toString();
        Object object = memcachedClient.get(key);
        IndirizziBean indirizziBean = null;
        try {
            indirizziBean = Obj.readValue(object.toString(), IndirizziBean.class);
            logger.info(String.format("indirizzo with id: %s is present in cache", idaddress.toString()));
            return Optional.ofNullable(indirizziBean);
        } catch (Exception e) {
            logger.info(String.format("indirizzo with id: %s is NOT present in cache", idaddress.toString()));
          return Optional.ofNullable(indirizziBean);
        }
    }

    public void save(IndirizziBean indirizzoBean) throws IOException {
        String key = prefix+"_"+indirizzoBean.getIdaddress().toString();
        String jsonStr = Obj.writeValueAsString(indirizzoBean);
        OperationFuture<Boolean> c = memcachedClient.set(key, expiration, jsonStr);
        if (c.getStatus().isSuccess()){
            logger.info("indirizzo saved success in cache");
        }else{
            logger.info("indirizzo saved failed in cache");
        }
    }

    public Boolean deleteById(Long id) {
        String key = prefix + "_" + id.toString();
        OperationFuture<Boolean> delete = memcachedClient.delete(key);
        return delete.getStatus().isSuccess();
    }

    public void update(IndirizziBean indirizziBean) throws IOException {
        boolean delete_condition = this.deleteById(indirizziBean.getIdaddress());
        if (delete_condition){
            this.save(indirizziBean);
        }
    }

    public void deleteByIdana(Long idana){
        List<Long> idaddress_list = indirizzoRepository.getIdaddressByIdana(idana);
        logger.info(idaddress_list.toString());
        logger.info(String.valueOf(idaddress_list.getClass()));
        idaddress_list.forEach(i->{
            Boolean c = this.deleteById(i);
        });
    }


}
