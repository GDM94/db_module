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
            anagraficaBean = Obj.readValue(object.toString(), AnagraficaBean.class);
            logger.info(String.format("anagrafica with id: %s is present in cache", idana.toString()));
            return Optional.ofNullable(anagraficaBean);
        } catch (Exception e) {
            logger.info(String.format("anagrafica with id: %s is NOT present in cache", idana.toString()));
            return Optional.ofNullable(anagraficaBean);

        }
    }

    public void save(AnagraficaBean anagraficaBean) throws IOException {
        String key = prefix+"_"+anagraficaBean.getIdana().toString();
        String jsonStr = Obj.writeValueAsString(anagraficaBean);
        OperationFuture<Boolean> c = memcachedClient.set(key, expiration, jsonStr);
        if (c.getStatus().isSuccess()){
            logger.info("memcached saved success");
        }else{
            logger.info("memcached saved failed");
        }
    }

    public Boolean deleteById(Long id){
        String key = prefix+"_"+ id.toString();
        OperationFuture<Boolean> delete = memcachedClient.delete(key);
        logger.info("delete memcached status:");
        logger.info(String.valueOf(delete.getStatus().isSuccess()));
        return delete.getStatus().isSuccess();
    }

    public void update(AnagraficaBean anagraficaBean) throws IOException {
        boolean delete_condition = this.deleteById(anagraficaBean.getIdana());
        if (delete_condition){
            this.save(anagraficaBean);
        }
    }
}
