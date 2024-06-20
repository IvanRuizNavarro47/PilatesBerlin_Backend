package com.example.berlinpilatesbackend.service;

import com.example.berlinpilatesbackend.dto.ClienteDTO;
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
            usuario.setPassword(passwordEncoder.encode(dto.getUsuarioDTO().getPassword()));
            usuario.setRol(dto.getUsuarioDTO().getRol());
            entity.setUsuario(usuario);
        } else {
            entity.getUsuario().setPassword(passwordEncoder.encode(entity.getUsuario().getPassword()));
        }

        return clienteRepository.save(entity);
    }

    public List<ClienteDTO> getAll() {
        return clienteMapper.toDTO(clienteRepository.findAll());
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
