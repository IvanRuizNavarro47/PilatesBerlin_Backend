package com.example.berlinpilatesbackend.mapper;

import com.example.berlinpilatesbackend.dto.ClaseDTO;
import com.example.berlinpilatesbackend.model.Clase;
import com.example.berlinpilatesbackend.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ClaseMapper {





    @Named("mapClientes")
    default List<Integer> mapClientes(List<Cliente> clientes) {
        if (clientes == null) {
            return null;
        }
        return clientes.stream()
                .map(Cliente::getId)
                .collect(Collectors.toList());
    }
}