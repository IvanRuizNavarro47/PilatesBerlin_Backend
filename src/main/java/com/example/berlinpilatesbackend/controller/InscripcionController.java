package com.example.berlinpilatesbackend.controller;

import com.example.berlinpilatesbackend.model.Clase;
import com.example.berlinpilatesbackend.model.InscripcionClase;
import com.example.berlinpilatesbackend.model.Usuario;
import com.example.berlinpilatesbackend.repository.IClaseRepository;
import com.example.berlinpilatesbackend.repository.IUsuarioRepository;
import com.example.berlinpilatesbackend.repository.InscripcionClaseRepository;
import com.example.berlinpilatesbackend.security.jwt.JWTService;
import com.example.berlinpilatesbackend.service.InscripcionService;
import com.example.berlinpilatesbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IClaseRepository claseRepository;


    @Autowired
    private JWTService jwtService;

    @Autowired
    private InscripcionClaseRepository inscripcionClaseRepository;

    // Obtener todas las clases a las que un usuario está inscrito
    @GetMapping("/usuario")
    public ResponseEntity<List<Clase>> obtenerClasesInscritas(
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Extraer el token del encabezado Authorization
            String token = authHeader.replace("Bearer ", "");

            // Extraer el username del token
            String username = jwtService.extractTokenData(token).getUsername();

            // Buscar el usuario por username
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Obtener las clases usando el ID del usuario
            List<Clase> clases = inscripcionService.obtenerClasesInscritasPorUsuario(usuario.getId());

            return ResponseEntity.ok(clases);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ArrayList<>());
        }
    }

    // Inscribir a un usuario en una clase
    @PostMapping("/inscribir")
    public ResponseEntity<?> inscribirseEnClase(
            @RequestParam Integer claseId,
            @RequestHeader("Authorization") String authHeader) {

        try {
            // Extraer el token y username
            String token = authHeader.replace("Bearer ", "");
            String username = jwtService.extractTokenData(token).getUsername();

            // Obtener el usuario
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Verificar si ya está inscrito
            boolean yaInscrito = inscripcionClaseRepository
                    .existsByUsuarioIdAndClaseIdAndEstadoInscripcion(
                            usuario.getId(),
                            claseId,
                            "activo"
                    );

            if (yaInscrito) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Ya estás inscrito en esta clase.");
            }

            // Obtener la clase
            Clase clase = claseRepository.findById(claseId)
                    .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

            // Verificar capacidad
            if (clase.getCapacidadMaxima() > 0) {
                InscripcionClase inscripcion = new InscripcionClase();
                inscripcion.setUsuario(usuario);
                inscripcion.setClase(clase);
                inscripcion.setFechaInscripcion(LocalDateTime.now());
                inscripcion.setEstadoInscripcion("activo");

                inscripcionClaseRepository.save(inscripcion);

                clase.setCapacidadMaxima(clase.getCapacidadMaxima() - 1);
                claseRepository.save(clase);

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Inscripción exitosa.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La clase ya está llena.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/abandonar")
    public ResponseEntity<?> abandonarClase(
            @RequestParam Integer claseId,
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Extraer el token y username
            String token = authHeader.replace("Bearer ", "");
            String username = jwtService.extractTokenData(token).getUsername();

            // Obtener usuario
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Obtener clase
            Clase clase = claseRepository.findById(claseId)
                    .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

            // Buscar y eliminar la inscripción
            InscripcionClase inscripcion = inscripcionClaseRepository
                    .findByUsuarioAndClase(usuario, clase)
                    .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

            // Eliminar la inscripción
            inscripcionClaseRepository.delete(inscripcion);

            // Aumentar la capacidad de la clase
            clase.setCapacidadMaxima(clase.getCapacidadMaxima() + 1);
            claseRepository.save(clase);

            return ResponseEntity.ok("Clase abandonada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }
}