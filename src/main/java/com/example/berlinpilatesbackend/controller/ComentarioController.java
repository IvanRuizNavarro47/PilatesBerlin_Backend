package com.example.berlinpilatesbackend.controller;

import com.example.berlinpilatesbackend.dto.ComentarioDTO;
import com.example.berlinpilatesbackend.mapper.ComentarioMapper;
import com.example.berlinpilatesbackend.model.Comentario;
import com.example.berlinpilatesbackend.model.Cliente;
import com.example.berlinpilatesbackend.model.Usuario;
import com.example.berlinpilatesbackend.service.ComentarioService;
import com.example.berlinpilatesbackend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ComentarioMapper comentarioMapper;

    @PostMapping("/crear")
    public ResponseEntity<ComentarioDTO> createComentario(@RequestBody ComentarioDTO comentarioDTO, @AuthenticationPrincipal Usuario usuario) {
        Cliente cliente = clienteService.findByUsuario(usuario);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Comentario comentario = comentarioMapper.toEntity(comentarioDTO);
        comentario.setCliente(cliente);
        comentario.setFecha(LocalDateTime.now());

        Comentario savedComentario = comentarioService.save(comentario);
        ComentarioDTO savedComentarioDTO = comentarioMapper.toDTO(savedComentario);

        return ResponseEntity.ok(savedComentarioDTO);
    }

    @GetMapping
    public List<ComentarioDTO> getAllComentarios() {
        List<Comentario> comentarios = comentarioService.findAll();
        return comentarioMapper.toDTOs(comentarios);
    }
}

