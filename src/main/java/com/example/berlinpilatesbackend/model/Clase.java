package com.example.berlinpilatesbackend.model;

import com.example.berlinpilatesbackend.enums.TipoClase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tipo")
    @Enumerated(EnumType.ORDINAL)
    private TipoClase tipoClase;

    @Column(name = "capacidad_max")
    private Integer capacidadMaxima;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @OneToMany(mappedBy = "clase")
    @JsonIgnore  // Ignora las inscripciones en clase
    private List<InscripcionClase> inscripciones;

    @ManyToMany
    @JoinTable(
            name = "inscripcion_clase",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "id_clase"),  // Columna que se refiere a la clase
            inverseJoinColumns = @JoinColumn(name = "id_usuario")  // Columna que se refiere al usuario
    )
    private List<Cliente> clientes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoClase getTipoClase() {
        return tipoClase;
    }

    public void setTipoClase(TipoClase tipoClase) {
        this.tipoClase = tipoClase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(Integer capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }
}
