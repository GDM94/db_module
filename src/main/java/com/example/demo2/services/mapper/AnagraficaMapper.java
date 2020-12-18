package com.example.demo2.services.mapper;


import com.example.communication.bean.AnagraficaBean;
import com.example.communication.model.Anagrafica;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AnagraficaMapper {
    List<AnagraficaBean> listEntityToListBean(List<Anagrafica> anagraficas);
    List<Anagrafica> ListBeanToListEntity(List<AnagraficaBean> anagraficaBeans);

    AnagraficaBean entityToBean(Anagrafica anagrafica);
    Anagrafica beanToEntity(AnagraficaBean anagraficaBean);




}
