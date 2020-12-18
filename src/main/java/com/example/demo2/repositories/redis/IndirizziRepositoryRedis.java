package com.example.demo2.repositories.redis;

import com.example.communication.bean.IndirizziBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndirizziRepositoryRedis extends JpaRepository<IndirizziBean, Long> {
}
