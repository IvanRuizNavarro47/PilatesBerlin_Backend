package com.example.berlinpilatesbackend.repository;

import com.example.berlinpilatesbackend.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IComentarioRepository extends JpaRepository<Comentario, Integer> {
    // Métodos personalizados si es necesario
}