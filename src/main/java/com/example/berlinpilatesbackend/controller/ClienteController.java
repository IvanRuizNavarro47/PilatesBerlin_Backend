package com.example.berlinpilatesbackend.controller;

import com.example.berlinpilatesbackend.dto.ClienteDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.example.berlinpilatesbackend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
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



}
