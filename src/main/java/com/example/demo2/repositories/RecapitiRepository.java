package com.example.demo2.repositories;

import com.example.communication.model.RecapitiTelefonici;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecapitiRepository extends JpaRepository<RecapitiTelefonici, Long> {

}
