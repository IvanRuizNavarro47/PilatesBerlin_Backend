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
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO cliente_clase (clase_id, cliente_id) VALUES (:claseId, :clienteId)", nativeQuery = true)
    void addClienteToClase(Integer claseId, Integer clienteId);

    // Método para eliminar un cliente de una clase
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cliente_clase WHERE clase_id = :claseId AND cliente_id = :clienteId", nativeQuery = true)
    void removeClienteFromClase(Integer claseId, Integer clienteId);

    List<Clase> findByFecha(Date fecha);








}