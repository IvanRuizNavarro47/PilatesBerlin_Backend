package com.example.berlinpilatesbackend.dto;

import com.example.berlinpilatesbackend.enums.Rol;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UsuarioDTO {

    private Integer id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Rol rol;
}
