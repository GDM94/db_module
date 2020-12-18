package com.example.demo2.repositories.redis;



import com.example.communication.bean.AnagraficaBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnagraficaRepositoryRedis extends JpaRepository<AnagraficaBean, Long> {
}
