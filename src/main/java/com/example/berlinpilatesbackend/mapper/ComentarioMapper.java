package com.example.berlinpilatesbackend.mapper;

import com.example.berlinpilatesbackend.dto.ComentarioDTO;
import com.example.berlinpilatesbackend.model.Comentario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    Comentario toEntity(ComentarioDTO dto);

    ComentarioDTO toDTO(Comentario comentario);
    List<ComentarioDTO> toDTOs(List<Comentario> comentarios);
}
