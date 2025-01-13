package com.example.berlinpilatesbackend.repository;

import com.example.berlinpilatesbackend.model.Clase;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IClaseRepository extends JpaRepository<Clase, Integer> {

    // Método para añadir un cliente a una clase


    // Método para eliminar un cliente de una clase


    List<Clase> findByFecha(Date fecha);








}