package com.example.demo2.resolver;


import com.example.communication.bean.IndirizziBean;
import com.example.demo2.services.IndrizzoService;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class IndirizziResolver {
    @Autowired
    IndrizzoService idrizzoService;

    @GraphQLQuery
    public IndirizziBean indirizzoById(Long id) throws IOException {
        return idrizzoService.indirizzoById(id);
    }

    @GraphQLQuery
    public List<IndirizziBean> indirizzoAll(){
        return idrizzoService.indirizzoAll();
    }


    @GraphQLMutation
    public IndirizziBean newIndirizzo( Long idana, String descrizione) throws IOException {
        return  idrizzoService.newIndirizzo(idana, descrizione);
    }

    @GraphQLMutation
    public IndirizziBean updateIndirizzo(Long id, String descrizione) throws IOException {
        return idrizzoService.updateIndirizzo(id, descrizione);
    }

    @GraphQLMutation
    public boolean deleteIndirizzo(Long id) throws IOException {
        return idrizzoService.deleteIndirizzo(id);
    }
}
