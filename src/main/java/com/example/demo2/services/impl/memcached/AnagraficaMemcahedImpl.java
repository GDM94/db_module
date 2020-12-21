package com.example.demo2.services.impl.memcached;

import com.example.communication.bean.AnagraficaBean;
import com.example.demo2.DbModuleApplication;
import com.example.demo2.services.memchaced.AnagraficaMemcached;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Service;

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

    public AnagraficaBean findById(Long idana){
        String key = prefix+"_"+idana.toString();
        AnagraficaBean anagraficaBean = (AnagraficaBean) memcachedClient.get(key);
        return anagraficaBean;
    }

    public void save(AnagraficaBean anagraficaBean){
        String key = prefix+"_"+anagraficaBean.getIdana().toString();
        boolean c = memcachedClient.set(key, 3600, anagraficaBean.toString()).isDone();
        if (c==true){
            logger.info("memcached set success");
        }else{
            logger.info("memcached set falled");
        }
    }
}
