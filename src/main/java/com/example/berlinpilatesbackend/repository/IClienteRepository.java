package com.example.berlinpilatesbackend.repository;


import com.example.berlinpilatesbackend.enums.Rol;
import com.example.berlinpilatesbackend.model.Cliente;
import com.example.berlinpilatesbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query(value = "select c.* from {h-schema}cliente c where c.nombre like %:nombre% " , nativeQuery = true)
    List<Cliente> buscarPorNombre(String nombre);

    @Query(value = "select c.* from {h-schema}cliente c where c.dni like %:letraDNI",nativeQuery = true)
    List<Cliente> buscarPorLetraDNI(String letraDNI);

    @Query(value = "select c.* from {h-schema}cliente c where c.nombre like %:nombre% and c.dni like %:letraDNI ",nativeQuery = true)
    List<Cliente> buscarPorLetraDNIYNombre(String letraDNI, String nombre);

    Cliente findByUsuario(Usuario usuario);

    // En el Repository
// En ClienteRepository
    @Query("SELECT c FROM Cliente c JOIN c.usuario u WHERE u.rol = :rol")
    List<Cliente> findByUsuarioRol(@Param("rol") Rol rol);


}