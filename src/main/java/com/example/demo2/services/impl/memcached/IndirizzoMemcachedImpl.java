package com.example.demo2.services.impl.memcached;

import com.example.communication.bean.IndirizziBean;
import com.example.communication.model.Indirizzo;
import com.example.demo2.services.memcached.IndirizzoMemcached;
import net.spy.memcached.MemcachedClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class IndirizzoMemcachedImpl implements IndirizzoMemcached {

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
            //logger.info("get memcached");
            //logger.info(object.toString());
            //logger.info(String.valueOf(object.getClass()));
            indirizziBean = Obj.readValue(object.toString(), IndirizziBean.class);
            return Optional.ofNullable(indirizziBean);
        } catch (Exception e) {
          return Optional.ofNullable(indirizziBean);
        }
    }

    public void save(IndirizziBean indirizzoBean) throws IOException {
        String key = prefix+"_"+indirizzoBean.getIdaddress().toString();
        String jsonStr = Obj.writeValueAsString(indirizzoBean);
        boolean c = memcachedClient.set(key, expiration, jsonStr).isDone();
        if (c==true){
            logger.info("memcached set success");
        }else{
            logger.info("memcached set failed");
        }
    }

    public Boolean deleteById(Long id) {
        String key = prefix + "_" + id.toString();
        return memcachedClient.delete(key).isDone();
    }


}
