package com.example.berlinpilatesbackend.repository;

import com.example.berlinpilatesbackend.model.InscripcionClase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscripcionClaseRepository extends JpaRepository<InscripcionClase, Long> {

    // Obtener las inscripciones activas de un usuario
    List<InscripcionClase> findByUsuarioIdAndEstadoInscripcion(Long usuarioId, String estadoInscripcion);

    // Buscar una inscripción de un usuario en una clase específica
    InscripcionClase findByUsuarioIdAndClaseId(Long usuarioId, Long claseId);
}
