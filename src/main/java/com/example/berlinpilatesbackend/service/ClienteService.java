package com.example.berlinpilatesbackend.service;

import com.example.berlinpilatesbackend.dto.ClienteDTO;
import com.example.berlinpilatesbackend.enums.Rol;
import com.example.berlinpilatesbackend.mapper.ClienteMapper;
import com.example.berlinpilatesbackend.model.Cliente;
import com.example.berlinpilatesbackend.model.Clase;
import com.example.berlinpilatesbackend.model.Usuario;
import com.example.berlinpilatesbackend.repository.IClaseRepository;
import com.example.berlinpilatesbackend.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IClaseRepository claseRepository;

    public Cliente save(ClienteDTO dto) {
        Cliente entity = clienteMapper.toEntity(dto);

        if (entity.getUsuario() == null) {
            Usuario usuario = new Usuario();
            usuario.setUsername(dto.getUsuarioDTO().getUsername());
            usuario.setPassword(passwordEncoder.encode(dto.getUsuarioDTO().getPassword())); // Encripta la contraseña
            usuario.setRol(dto.getUsuarioDTO().getRol());
            entity.setUsuario(usuario);
        } else {
            entity.getUsuario().setPassword(passwordEncoder.encode(entity.getUsuario().getPassword())); // Encripta también en actualización
        }

        return clienteRepository.save(entity); // Guarda siempre la contraseña encriptada
    }




    public List<ClienteDTO> getAll() {
        return clienteMapper.toDTO(clienteRepository.findAll());
    }


    // Nuevo método para obtener solo los clientes cuyo usuario tiene el rol MONITOR
    public List<ClienteDTO> getMonitores() {
        return clienteMapper.toDTO(
                clienteRepository.findAll().stream()
                        .filter(cliente -> cliente.getUsuario().getRol() == Rol.MONITOR)
                        .collect(Collectors.toList())
        );
    }

    public Cliente createMonitor(ClienteDTO dto) {
        Cliente monitor = clienteMapper.toEntity(dto);
        return clienteRepository.save(monitor);
    }


    public Cliente updateMonitor(Integer id, ClienteDTO monitorDTO) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Monitor no encontrado"));

        clienteExistente.setNombre(monitorDTO.getNombre());
        clienteExistente.setApellido1(monitorDTO.getApellido1());
        clienteExistente.setApellido2(monitorDTO.getApellido2());
        clienteExistente.setDni(monitorDTO.getDni());
        clienteExistente.setEmail(monitorDTO.getEmail());

        Usuario usuario = clienteExistente.getUsuario();
        usuario.setUsername(monitorDTO.getUsuarioDTO().getUsername());
        if (monitorDTO.getUsuarioDTO().getPassword() != null && !monitorDTO.getUsuarioDTO().getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(monitorDTO.getUsuarioDTO().getPassword())); // Encriptamos si hay nueva contraseña
        }

        return clienteRepository.save(clienteExistente);
    }




    public void deleteMonitor(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Monitor no encontrado");
        }
        clienteRepository.deleteById(id);
    }

    public List<ClienteDTO> buscarClientePorFiltro(String nombre, String letraDNI) {
        if (nombre != null && letraDNI != null) {
            return clienteMapper.toDTO(clienteRepository.buscarPorLetraDNIYNombre(letraDNI, nombre));
        } else if (nombre == null && letraDNI != null) {
            return clienteMapper.toDTO(clienteRepository.buscarPorLetraDNI(letraDNI));
        } else if (nombre != null) {
            return clienteMapper.toDTO(clienteRepository.buscarPorNombre(nombre));
        } else {
            return clienteMapper.toDTO(clienteRepository.findAll());
        }
    }

    public Cliente addClaseToCliente(Integer clienteId, Integer claseId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente not found"));
        Clase clase = claseRepository.findById(claseId).orElseThrow(() -> new RuntimeException("Clase not found"));
        cliente.getClases().add(clase);
        return clienteRepository.save(cliente);
    }

    public Cliente removeClaseFromCliente(Integer clienteId, Integer claseId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente not found"));
        Clase clase = claseRepository.findById(claseId).orElseThrow(() -> new RuntimeException("Clase not found"));
        cliente.getClases().remove(clase);
        return clienteRepository.save(cliente);
    }

    public Cliente findByUsuario(Usuario usuario) {
        return clienteRepository.findByUsuario(usuario);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }


}
