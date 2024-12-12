package com.example.berlinpilatesbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {
    private Integer id;
    private String contenido;
    private LocalDateTime fechaComentario;
}
