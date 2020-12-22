package com.example.demo2.repositories;

import com.example.communication.model.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {
    @Query(value = "SELECT idaddress from indirizzo WHERE indirizzo.idana = :id", nativeQuery = true)
    List<Long> getIdaddressByIdana(@Param("id") long id);
}
