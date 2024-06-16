package com.example.berlinpilatesbackend.service;

import com.example.berlinpilatesbackend.model.Clase;
import com.example.berlinpilatesbackend.repository.IClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaseService {

    @Autowired
    private IClaseRepository claseRepository;

    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

    public Clase save(Clase clase) {
        return claseRepository.save(clase);
    }

    public void deleteById(Integer id) {
        claseRepository.deleteById(id);
    }

    public Clase findById(Integer id) {
        return claseRepository.findById(id).orElse(null);
    }
}
