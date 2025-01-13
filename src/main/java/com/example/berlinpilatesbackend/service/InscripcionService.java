package com.example.berlinpilatesbackend.service;

import com.example.berlinpilatesbackend.model.Clase;
import com.example.berlinpilatesbackend.model.InscripcionClase;
import com.example.berlinpilatesbackend.model.Usuario;
import com.example.berlinpilatesbackend.repository.IClaseRepository;
import com.example.berlinpilatesbackend.repository.IUsuarioRepository;
import com.example.berlinpilatesbackend.repository.InscripcionClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionClaseRepository inscripcionRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IClaseRepository claseRepository;

    // Listar las clases en las que un usuario está inscrito
    public List<Clase> obtenerClasesInscritasPorUsuario(Long usuarioId) {
        List<InscripcionClase> inscripciones = inscripcionRepository.findByUsuarioIdAndEstadoInscripcion(usuarioId, "activo");
        return inscripciones.stream().map(InscripcionClase::getClase).toList();
    }

    // Inscribir a un usuario en una clase
    public void inscribirseEnClase(Integer usuarioId, Integer claseId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Clase clase = claseRepository.findById(claseId)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

        // Verificar si la clase tiene capacidad disponible
        if (clase.getCapacidadMaxima() > 0) {
            InscripcionClase inscripcion = new InscripcionClase();
            inscripcion.setUsuario(usuario);
            inscripcion.setClase(clase);
            inscripcion.setFechaInscripcion(LocalDateTime.now());
            inscripcion.setEstadoInscripcion("activo");

            // Guardar la inscripción
            inscripcionRepository.save(inscripcion);

            // Reducir la capacidad disponible de la clase (esto depende de tu lógica de negocio)
            clase.setCapacidadMaxima(clase.getCapacidadMaxima() - 1);
            claseRepository.save(clase);
        } else {
            throw new RuntimeException("La clase ya está llena.");
        }
    }

    // Abandonar una clase
    public void abandonarClase(Long usuarioId, Long claseId) {
        InscripcionClase inscripcion = inscripcionRepository.findByUsuarioIdAndClaseId(usuarioId, claseId);
        if (inscripcion != null) {
            inscripcion.setEstadoInscripcion("abandonado");
            inscripcionRepository.save(inscripcion);

            // Aumentar la capacidad disponible de la clase (según la lógica de negocio)
            Clase clase = inscripcion.getClase();
            clase.setCapacidadMaxima(clase.getCapacidadMaxima() + 1);
            claseRepository.save(clase);
        } else {
            throw new RuntimeException("No se encontró la inscripción para abandonar.");
        }
    }
}
