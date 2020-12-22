package com.example.demo2.services.impl.memcached;

import com.example.communication.bean.AnagraficaBean;
import net.spy.memcached.MemcachedClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AnagraficaMemcachedImplTest {
    @InjectMocks
    AnagraficaMemcahedImpl anagraficaMemcahed;

    private static String prefix = "anagrafica";

    private static ObjectMapper Obj = new ObjectMapper();

    private static MemcachedClient memcachedClient;
    private static AnagraficaBean anagrafica = new AnagraficaBean();
    private static Long id = (long) 1;
    private static String nome = "test";
    private static String cognome = "test";
    private static String key = prefix+"_"+id.toString();



    @BeforeClass
    public static void init() throws IOException {
        memcachedClient = Mockito.mock(MemcachedClient.class);
        anagrafica.setIdana(id);
        anagrafica.setNome(nome);
        anagrafica.setCognome(cognome);
        String jsonStr = Obj.writeValueAsString(anagrafica);
        Mockito.when(memcachedClient.get(key)).thenReturn(jsonStr);
    }

    @Test
    public void findById() {
        Optional<AnagraficaBean> anagraficaBean = anagraficaMemcahed.findById(id);
        anagraficaBean.ifPresent(a->{
            assertEquals(a.getIdana(), id);
            assertEquals(a.getNome(), nome);
            assertEquals(a.getCognome(), cognome);
        });
    }

    @Test
    public void save() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void update() {
    }
}
