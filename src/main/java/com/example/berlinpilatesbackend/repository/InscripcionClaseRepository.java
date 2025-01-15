package com.example.berlinpilatesbackend.repository;

import com.example.berlinpilatesbackend.model.Clase;
import com.example.berlinpilatesbackend.model.InscripcionClase;
import com.example.berlinpilatesbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscripcionClaseRepository extends JpaRepository<InscripcionClase, Long> {

    // Obtener las inscripciones activas de un usuario
    List<InscripcionClase> findByUsuarioIdAndEstadoInscripcion(Long usuarioId, String estadoInscripcion);

    // Buscar una inscripción de un usuario en una clase específica
    InscripcionClase findByUsuarioIdAndClaseId(Long usuarioId, Long claseId);

    Optional<InscripcionClase> findByUsuarioAndClase(Usuario usuario, Clase clase);

}
