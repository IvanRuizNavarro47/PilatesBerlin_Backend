package com.example.berlinpilatesbackend.controller;

import com.example.berlinpilatesbackend.model.Clase;
import com.example.berlinpilatesbackend.service.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clases")
@CrossOrigin(origins = "http://localhost:4200")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public List<Clase> getAllClases() {
        return claseService.findAll();
    }

    @PostMapping("/crear")
    public Clase createClase(@RequestBody Clase clase) {
        return claseService.save(clase);
    }

    @PutMapping("/{id}")
    public Clase updateClase(@PathVariable Integer id, @RequestBody Clase clase) {
        clase.setId(id);
        return claseService.save(clase);
    }

    @DeleteMapping("/{id}")
    public void deleteClase(@PathVariable Integer id) {
        claseService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Clase getClaseById(@PathVariable Integer id) {
        return claseService.findById(id);
    }
}
