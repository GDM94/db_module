package com.example.demo2.repositories;

import com.example.communication.model.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {

    @Query(value = "SELECT idaddress FROM indirizzo WHERE idana = :id", nativeQuery = true)
    Optional<List<BigInteger>> findByIdana(@Param("id") long id);

}
