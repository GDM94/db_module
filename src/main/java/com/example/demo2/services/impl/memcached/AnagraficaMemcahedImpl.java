package com.example.demo2.services.impl.memcached;

import com.example.communication.bean.AnagraficaBean;
import com.example.demo2.services.memcached.AnagraficaMemcached;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

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

    public Optional<AnagraficaBean> findById(Long idana) {
        String key = prefix+"_"+idana.toString();
        Object object = memcachedClient.get(key);
        AnagraficaBean anagraficaBean = null;
        try {
            /*
            logger.info("get memcached");
            logger.info(object.toString());
            logger.info(String.valueOf(object.getClass()));

             */
            anagraficaBean = Obj.readValue(object.toString(), AnagraficaBean.class);
            return Optional.ofNullable(anagraficaBean);
        } catch (Exception e) {

            return Optional.ofNullable(anagraficaBean);

        }
    }

    public void save(AnagraficaBean anagraficaBean) throws IOException {
        String key = prefix+"_"+anagraficaBean.getIdana().toString();
        String jsonStr = Obj.writeValueAsString(anagraficaBean);
        OperationFuture<Boolean> c = memcachedClient.set(key, expiration, jsonStr);
        if (c.getStatus().isSuccess()){
            logger.info("memcached set success");
        }else{
            logger.info("memcached set failed");
        }
    }

    public Boolean deleteById(Long id){
        String key = prefix+"_"+ id.toString();
        return memcachedClient.delete(key).isDone();

    }
}
