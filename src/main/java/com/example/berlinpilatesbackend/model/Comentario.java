package com.example.berlinpilatesbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentario", catalog = "postgres", schema = "berlinpilates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "usuario_id") // Cambio: En lugar de cliente_id, ahora usuario_id
    private Usuario usuario;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "fecha_comentario")
    private LocalDateTime fechaComentario;


}
