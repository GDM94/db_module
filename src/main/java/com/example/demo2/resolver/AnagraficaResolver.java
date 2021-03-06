package com.example.demo2.resolver;


import com.example.communication.model.Anagrafica;
import com.example.demo2.services.AnagraficaService;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AnagraficaResolver {

    @Autowired
    private AnagraficaService anagraficaService;

    @GraphQLMutation
    public Anagrafica newAnagrafica(Long id, String nome, String cognome) {
        return anagraficaService.newAnagrafica(id, nome, cognome);
    }

    @GraphQLMutation
    public Optional<Anagrafica> updateAnagrafica(Long id, String nome, String cognome) {
        return anagraficaService.updateAnagrafica(id, nome, cognome);
    }

    @GraphQLMutation
    public boolean deleteAnagrafica(Long id) {
        return anagraficaService.deleteAnagrafica(id);
    }

    @GraphQLQuery
    public Optional<Anagrafica> anagraficaById(Long id) {
        return anagraficaService.anagraficaById(id);
    }

    @GraphQLQuery
    public List<Anagrafica> anagraficaAll() {
        return anagraficaService.anagraficaAll();
    }

}