package com.example.demo2.resolver;

import com.example.communication.bean.RecapitiBean;
import com.example.communication.model.RecapitiTelefonici;
import com.example.demo2.services.RecapitiService;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecapitiResolver {

    @Autowired
    RecapitiService recapitiService;

    @GraphQLQuery
    public RecapitiBean recapitoById(Long id){
        return recapitiService.recapitoById(id);
    }

    @GraphQLQuery
    public List<RecapitiBean> recapitoAll(){
        return recapitiService.recapitoAll();
    }

    @GraphQLMutation
    public RecapitiBean newRecapiti(Long idana, String tipo_recapito, String numero_recapito){
        return recapitiService.newRecapiti(idana, tipo_recapito, numero_recapito);
    }

    @GraphQLMutation
    public Boolean deleteRecapiti(long idreca){
        return recapitiService.deleteRecapiti(idreca);
    }

    @GraphQLMutation
    public RecapitiBean updateRecapiti(Long id, String tipo_recapito, String numero_recapito){
        return recapitiService.updateRecapiti(id, tipo_recapito, numero_recapito);
    }

}
