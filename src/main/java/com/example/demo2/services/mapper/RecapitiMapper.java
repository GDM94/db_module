package com.example.demo2.services.mapper;


import com.example.communication.bean.RecapitiBean;
import com.example.communication.model.RecapitiTelefonici;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = AnagraficaMapper.class)
public interface RecapitiMapper {

    RecapitiMapper RECAPITI_MAPPER = Mappers.getMapper(RecapitiMapper.class);

    List<RecapitiBean> listEntityToListBean(List<RecapitiTelefonici> recapitiTelefonicis);
    List<RecapitiTelefonici> ListBeanToListEntity(List<RecapitiBean> recapitiBeans);

    @Mapping(target = "anagraficaBean", source = "anagrafica")
    RecapitiBean entityToBean(RecapitiTelefonici recapitiTelefonici);
    RecapitiTelefonici beanToEntity(RecapitiBean recapitiBean);
}
