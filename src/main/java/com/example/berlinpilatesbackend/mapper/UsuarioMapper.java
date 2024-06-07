package com.example.berlinpilatesbackend.mapper;

import com.example.berlinpilatesbackend.dto.UsuarioDTO;
import com.example.berlinpilatesbackend.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO(Usuario entity);

    List<Usuario> toEntity(List<UsuarioDTO> dtos);

    List<UsuarioDTO> toDTO(List<Usuario> entities);



}
