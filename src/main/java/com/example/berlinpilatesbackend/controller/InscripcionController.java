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
    @GetMapping("/usuario/{usuarioId}")
    public List<Clase> obtenerClasesInscritas(@PathVariable Long usuarioId) {
        return inscripcionService.obtenerClasesInscritasPorUsuario(usuarioId);
    }

    // Inscribir a un usuario en una clase
    @PostMapping("/inscribir")
    public ResponseEntity<?> inscribirseEnClase(
            @RequestParam Integer claseId,  // Recibimos el claseId como parámetro
            @RequestHeader("Authorization") String authHeader) {  // Recibimos el token en el header

        try {
            // Extraer el token del encabezado Authorization
            String token = authHeader.replace("Bearer ", "");

            // Extraer el username del token
            String username = jwtService.extractTokenData(token).getUsername();

            // Obtener el usuario a partir del username
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Obtener la clase a partir del claseId
            Clase clase = claseRepository.findById(claseId)
                    .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

            // Verificar si la clase tiene capacidad disponible
            if (clase.getCapacidadMaxima() > 0) {
                // Crear la inscripción
                InscripcionClase inscripcion = new InscripcionClase();
                inscripcion.setUsuario(usuario);
                inscripcion.setClase(clase);
                inscripcion.setFechaInscripcion(LocalDateTime.now());
                inscripcion.setEstadoInscripcion("activo");

                // Guardar la inscripción en la base de datos
                inscripcionClaseRepository.save(inscripcion);

                // Reducir la capacidad disponible de la clase
                clase.setCapacidadMaxima(clase.getCapacidadMaxima() - 1);
                claseRepository.save(clase);

                // Retornar respuesta exitosa
                return ResponseEntity.status(HttpStatus.CREATED).body("Inscripción exitosa.");
            } else {
                // Si la clase ya está llena
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La clase ya está llena.");
            }

        } catch (Exception e) {
            // Retornar error en caso de fallo
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }


    // Abandonar una clase
    @PostMapping("/abandonar")
    public void abandonarClase(@RequestParam Long usuarioId, @RequestParam Long claseId) {
        inscripcionService.abandonarClase(usuarioId, claseId);
    }
}