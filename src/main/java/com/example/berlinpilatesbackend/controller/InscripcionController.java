package com.example.berlinpilatesbackend.controller;

import com.example.berlinpilatesbackend.model.Clase;
import com.example.berlinpilatesbackend.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    // Obtener todas las clases a las que un usuario está inscrito
    @GetMapping("/usuario/{usuarioId}")
    public List<Clase> obtenerClasesInscritas(@PathVariable Long usuarioId) {
        return inscripcionService.obtenerClasesInscritasPorUsuario(usuarioId);
    }

    // Inscribir a un usuario en una clase
    @PostMapping("/inscribir")
    public void inscribirseEnClase(@RequestParam Integer usuarioId, @RequestParam Integer claseId) {
        inscripcionService.inscribirseEnClase(usuarioId, claseId);
    }

    // Abandonar una clase
    @PostMapping("/abandonar")
    public void abandonarClase(@RequestParam Long usuarioId, @RequestParam Long claseId) {
        inscripcionService.abandonarClase(usuarioId, claseId);
    }
}