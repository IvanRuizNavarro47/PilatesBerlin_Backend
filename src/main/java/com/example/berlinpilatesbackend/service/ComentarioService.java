package com.example.berlinpilatesbackend.service;

import com.example.berlinpilatesbackend.dto.ComentarioDTO;
import com.example.berlinpilatesbackend.mapper.ComentarioMapper;
import com.example.berlinpilatesbackend.model.Comentario;
import com.example.berlinpilatesbackend.model.Usuario;
import com.example.berlinpilatesbackend.repository.IComentarioRepository;
import com.example.berlinpilatesbackend.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    @Autowired
    private IComentarioRepository comentarioRepository;

    @Autowired
    private ComentarioMapper comentarioMapper;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public Comentario crearComentario(String contenido, String username) {
        // Buscar el usuario por el username extraído del token
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear y guardar el comentario
        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario); // Relación con la entidad Usuario
        comentario.setContenido(contenido);
        comentario.setFechaComentario(LocalDateTime.now());

        return comentarioRepository.save(comentario);
    }


    public List<ComentarioDTO> listarTodosLosComentarios() {
        List<Comentario> comentarios = comentarioRepository.findAll();
        return comentarios.stream()
                .map(comentarioMapper::toDTO)  // Mapea a ComentarioDTO con usuarioId incluido
                .collect(Collectors.toList());
    }


    public Comentario editarComentario(Integer id, String nuevoContenido) {
        // Buscar el comentario por ID
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        // Actualizar el contenido del comentario
        comentario.setContenido(nuevoContenido);
        comentario.setFechaComentario(LocalDateTime.now()); // Opcional: actualizar la fecha

        return comentarioRepository.save(comentario);
    }

    public void eliminarComentario(Integer id) {
        // Verificar si el comentario existe
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        // Eliminar el comentario
        comentarioRepository.delete(comentario);
    }

}
