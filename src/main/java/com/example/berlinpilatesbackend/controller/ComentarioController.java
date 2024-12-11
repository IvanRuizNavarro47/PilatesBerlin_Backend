package com.example.berlinpilatesbackend.controller;

import com.example.berlinpilatesbackend.dto.ComentarioDTO;
import com.example.berlinpilatesbackend.model.Comentario;
import com.example.berlinpilatesbackend.security.jwt.JWTService;
import com.example.berlinpilatesbackend.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private JWTService jwtService;

    // Obtener todos los comentarios
    @GetMapping
    public List<ComentarioDTO> listarComentarios() {
        return comentarioService.listarTodosLosComentarios();
    }

    // Endpoint para crear un nuevo comentario
    @PostMapping
    public ResponseEntity<?> crearComentario(
            @RequestBody Map<String, String> body,
            @RequestHeader("Authorization") String authHeader) {

        try {
            // Extraer el token del encabezado Authorization
            String token = authHeader.replace("Bearer ", "");

            // Extraer el username desde el token usando JWTService
            String username = jwtService.extractTokenData(token).getUsername();

            // Obtener el contenido del comentario desde el cuerpo de la petición
            String contenido = body.get("contenido");

            // Crear el comentario usando el servicio
            Comentario comentario = comentarioService.crearComentario(contenido, username);

            return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
