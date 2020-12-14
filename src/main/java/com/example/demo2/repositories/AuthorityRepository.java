package com.example.demo2.repositories;


import com.example.communication.model.Authority;
import com.example.communication.model.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(AuthorityName name);

}
