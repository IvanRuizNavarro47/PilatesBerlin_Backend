package com.example.berlinpilatesbackend.service;

import com.example.berlinpilatesbackend.dto.ClienteDTO;
import com.example.berlinpilatesbackend.mapper.ClienteMapper;
import com.example.berlinpilatesbackend.model.Cliente;
import com.example.berlinpilatesbackend.model.Usuario;
import com.example.berlinpilatesbackend.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Cliente save(ClienteDTO dto) {
        Cliente entity = clienteMapper.toEntity(dto);

        // Asegurarse de que el usuario no sea null
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
}

