package com.example.demo2.services.impl.memcached;

import com.example.communication.bean.AnagraficaBean;
import com.example.communication.model.Anagrafica;
import com.example.demo2.DbModuleApplication;
import com.example.demo2.services.memchaced.AnagraficaMemcached;

import net.spy.memcached.MemcachedClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class AnagraficaMemcahedImpl implements AnagraficaMemcached {

    @Autowired
    MemcachedClient memcachedClient;

    @Value("${memcached.cache.prefix.anagrafica}")
    private String prefix;

    @Value("${memcached.cache.expiration}")
    private int expiration;

    private static final Logger logger = LoggerFactory.getLogger(AnagraficaMemcahedImpl.class);
    private final ObjectMapper Obj = new ObjectMapper();

    public AnagraficaBean findById(Long idana) throws IOException {
        String key = prefix+"_"+idana.toString();
        Object object = memcachedClient.get(key);
        logger.info("get memecached");
        logger.info(object.toString());
        logger.info(String.valueOf(object.getClass()));
        AnagraficaBean anagraficaBean = Obj.readValue(object.toString(), AnagraficaBean.class);
        return anagraficaBean;
    }

    public void save(AnagraficaBean anagraficaBean) throws IOException {
        String key = prefix+"_"+anagraficaBean.getIdana().toString();
        String jsonStr = Obj.writeValueAsString(anagraficaBean);
        boolean c = memcachedClient.set(key, 3600, jsonStr).isDone();
        if (c==true){
            logger.info("memcached set success");
        }else{
            logger.info("memcached set falled");
        }
    }
}
