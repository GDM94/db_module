package com.example.demo2.services.mapper;


import com.example.communication.bean.AnagraficaBean;
import com.example.communication.bean.IndirizziBean;
import com.example.communication.model.Anagrafica;
import com.example.communication.model.Indirizzo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = AnagraficaMapper.class)
public interface IndirizziMapper {

    IndirizziMapper INDIRIZZI_MAPPER = Mappers.getMapper(IndirizziMapper.class);

    List<IndirizziBean> listEntityToListBean(List<Indirizzo> indirizzos);
    List<Indirizzo> ListBeanToListEntity(List<IndirizziBean> indirizziBeans);

    @Mapping(source = "anagrafica", target = "anagrafica")
    IndirizziBean entityToBean(Indirizzo indirizzo);
    Indirizzo beanToEntity(IndirizziBean indirizziBean);

}
