package com.example.demo2.repositories;

import com.example.communication.model.RecapitiTelefonici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface RecapitiRepository extends JpaRepository<RecapitiTelefonici, Long> {

    @Query(value = "SELECT idreca FROM recapiti_telefonici WHERE idana = :id", nativeQuery = true)
    Optional<List<BigInteger>> findByIdana(@Param("id") long id);

}
