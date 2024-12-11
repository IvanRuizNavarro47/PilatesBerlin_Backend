package com.example.berlinpilatesbackend.mapper;

import com.example.berlinpilatesbackend.dto.ComentarioDTO;
import com.example.berlinpilatesbackend.model.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {


    Comentario toEntity(ComentarioDTO dto);

    ComentarioDTO toDTO(Comentario entity);

    List<Comentario> toEntity(List<ComentarioDTO> dtos);

    List<ComentarioDTO> toDTO(List<Comentario> entities);
}