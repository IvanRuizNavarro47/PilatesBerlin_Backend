package com.example.berlinpilatesbackend.mapper;

import com.example.berlinpilatesbackend.dto.ClienteDTO;
import com.example.berlinpilatesbackend.dto.UsuarioDTO;
import com.example.berlinpilatesbackend.model.Cliente;
import com.example.berlinpilatesbackend.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    UsuarioMapper usuarioMapper = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(source = "usuarioDTO", target = "usuario", qualifiedByName = "transformarUsuarioDTO")
    Cliente toEntity(ClienteDTO dto);

    @Mapping(source = "usuario", target = "usuarioDTO", qualifiedByName = "transformarUsuario")
    ClienteDTO toDTO(Cliente entity);

    List<ClienteDTO> toDTO(List<Cliente> entities);

    @Named("transformarUsuarioDTO")
    default Usuario transformarUsuario(UsuarioDTO dto) {
        return usuarioMapper.toEntity(dto);
    }

    @Named("transformarUsuario")
    default UsuarioDTO transformarUsuario(Usuario entity) {
        return usuarioMapper.toDTO(entity);
    }
}