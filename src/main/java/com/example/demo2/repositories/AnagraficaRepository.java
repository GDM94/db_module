package com.example.demo2.repositories;

import com.example.communication.model.Anagrafica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnagraficaRepository extends JpaRepository<Anagrafica, Long> {

    @Query(value = "SELECT ana.nome, reca.numero_recapito from anagrafica ana INNER JOIN recapiti_telefonici reca ON ana.idana = reca.idana WHERE ana.idana = :id",
            nativeQuery = true)
    List<String> getName_Numero(@Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE anagrafica SET nome = :nome  WHERE idana = :id",
            nativeQuery = true)
    int updateNomeCognome(@Param("id") long id, @Param("nome") String nomeIn);

    @Query(value = "SELECT ana.nome from anagrafica ana WHERE ana.idana = :id",
            nativeQuery = true)
    List<String> getNamebyId(@Param("id") long id);


}

