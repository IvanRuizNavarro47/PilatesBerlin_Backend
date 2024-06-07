package com.example.berlinpilatesbackend.repository;

import com.example.berlinpilatesbackend.model.Token;
import com.example.berlinpilatesbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Integer> {

    Token findTopByUsuario(Usuario usuario);

}