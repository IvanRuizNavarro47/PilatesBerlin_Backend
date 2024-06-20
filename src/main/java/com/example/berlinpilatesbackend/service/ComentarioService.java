package com.example.berlinpilatesbackend.service;

import com.example.berlinpilatesbackend.model.Comentario;
import com.example.berlinpilatesbackend.repository.IComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private IComentarioRepository comentarioRepository;

    public List<Comentario> findAll() {
        return comentarioRepository.findAll();
    }

    public Comentario save(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }
}
