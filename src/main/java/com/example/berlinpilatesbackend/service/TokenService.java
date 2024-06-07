package com.example.berlinpilatesbackend.service;

import com.example.berlinpilatesbackend.model.Token;
import com.example.berlinpilatesbackend.model.Usuario;
import com.example.berlinpilatesbackend.repository.ITokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final ITokenRepository tokenRepository;


    public Token getByUsuario(Usuario usuario){
        return tokenRepository.findTopByUsuario(usuario);
    }

    public Token save(Token token){
        return tokenRepository.save(token);
    }

}