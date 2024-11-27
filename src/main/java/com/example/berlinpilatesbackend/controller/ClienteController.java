package com.example.berlinpilatesbackend.controller;

import com.example.berlinpilatesbackend.dto.ClienteDTO;
import com.example.berlinpilatesbackend.model.Cliente;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.example.berlinpilatesbackend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = {"/http://localhost:4200"})
@SecurityRequirement(name = "Bearer Authentication")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/all")
    public List<ClienteDTO> obtenerTodos(){
        return clienteService.getAll();
    }
    @PostMapping("/crear")
    public ClienteDTO crearCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        return null;
    }

    @GetMapping("/monitores")
    public List<ClienteDTO> obtenerMonitores(){
        return clienteService.getMonitores();
    }

    // Crear un monitor
    @PostMapping
    public Cliente createMonitor(@RequestBody ClienteDTO monitorDTO) {
        return clienteService.createMonitor(monitorDTO);
    }

    // Editar un monitor
    @PutMapping("/{id}")
    public Cliente updateMonitor(@PathVariable Integer id, @RequestBody ClienteDTO monitorDTO) {
        return clienteService.updateMonitor(id, monitorDTO);
    }

    // Eliminar un monitor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonitor(@PathVariable Integer id) {
        clienteService.deleteMonitor(id);
        return ResponseEntity.noContent().build();
    }

}
