package com.example.berlinpilatesbackend.dto;

import com.example.berlinpilatesbackend.enums.TipoClase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaseDTO {
    private Integer id;
    private TipoClase tipoClase;
    private Integer capacidadMaxima;
    private String descripcion;
    private Date fecha;
    private List<Integer> clienteIds;
}