package com.example.demo2.repositories.redis;

import com.example.communication.bean.RecapitiBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecapitiRepositoryRedis extends JpaRepository<RecapitiBean, Long> {
}
