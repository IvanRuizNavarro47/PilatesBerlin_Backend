package com.example.berlinpilatesbackend.controller;

import com.example.berlinpilatesbackend.dto.ClienteDTO;
import com.example.berlinpilatesbackend.enums.Rol;
import com.example.berlinpilatesbackend.model.Cliente;
import com.example.berlinpilatesbackend.model.Usuario;
import com.example.berlinpilatesbackend.repository.IClienteRepository;
import com.example.berlinpilatesbackend.repository.IUsuarioRepository;
import com.example.berlinpilatesbackend.service.ClienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = {"/http://localhost:4200"})
@SecurityRequirement(name = "Bearer Authentication")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    // Obtener todos los clientes
    @GetMapping("/all")
    public List<ClienteDTO> obtenerTodos() {
        return clienteService.getAll();
    }

    @PostMapping
    public Cliente crearMonitor(@RequestBody ClienteDTO dto) {
        Usuario usuario;

        // Validación de los campos username y password
        if (dto.getUsuarioDTO() == null || dto.getUsuarioDTO().getUsername() == null || dto.getUsuarioDTO().getUsername().isEmpty()) {
            throw new RuntimeException("El campo 'username' es obligatorio");
        }
        if (dto.getUsuarioDTO().getPassword() == null || dto.getUsuarioDTO().getPassword().isEmpty()) {
            throw new RuntimeException("El campo 'password' es obligatorio");
        }

        // Si no se proporciona un idUsuario, crea uno nuevo
        if (dto.getUsuarioDTO().getId() == null) {
            usuario = new Usuario();
            usuario.setRol(Rol.MONITOR);  // Usar el enum Rol.MONITOR
            usuario.setUsername(dto.getUsuarioDTO().getUsername());
            // Encripta la contraseña
            usuario.setPassword(passwordEncoder.encode(dto.getUsuarioDTO().getPassword()));
            usuario = usuarioRepository.save(usuario); // Guardar el nuevo usuario en la base de datos
        } else {
            // Si se proporciona un idUsuario, buscarlo en la base de datos
            usuario = usuarioRepository.findById(dto.getUsuarioDTO().getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }

        // Crear el nuevo monitor
        Cliente nuevoMonitor = new Cliente();
        nuevoMonitor.setNombre(dto.getNombre());
        nuevoMonitor.setApellido1(dto.getApellido1());
        nuevoMonitor.setApellido2(dto.getApellido2());
        nuevoMonitor.setDni(dto.getDni());
        nuevoMonitor.setEmail(dto.getEmail());
        nuevoMonitor.setUsuario(usuario); // Asociar el usuario al nuevo monitor

        return clienteRepository.save(nuevoMonitor); // Guardar el nuevo monitor en la base de datos
    }

    // Obtener solo usuarios

    @GetMapping("/usuarios")
    public List<ClienteDTO> obtenerUsuarios() {
        return clienteService.getUsuarios();
    }

    // Obtener solo monitores
    @GetMapping("/monitores")
    public List<ClienteDTO> obtenerMonitores() {
        return clienteService.getMonitores();
    }

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
