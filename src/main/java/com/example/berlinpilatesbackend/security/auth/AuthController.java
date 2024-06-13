package com.example.berlinpilatesbackend.security.auth;

import com.example.berlinpilatesbackend.dto.ClienteDTO;
import com.example.berlinpilatesbackend.dto.UsuarioDTO;
import com.example.berlinpilatesbackend.model.Cliente;
import com.example.berlinpilatesbackend.model.Token;
import com.example.berlinpilatesbackend.model.Usuario;
import com.example.berlinpilatesbackend.security.jwt.JWTService;
import com.example.berlinpilatesbackend.service.ClienteService;
import com.example.berlinpilatesbackend.service.TokenService;
import com.example.berlinpilatesbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"/http://localhost:4200"})
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClienteService clienteService;


    @PostMapping("/login")
    public AuthDTO login(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = (Usuario) usuarioService.loadUserByUsername(usuarioDTO.getUsername());
        String apiKey = null;
        String mensaje;

        if (usuario != null) {
            if (usuarioService.validarPassword(usuario, usuarioDTO.getPassword())) {

                mensaje = "Usuario Logueado";

                //Usuario sin token
                if (usuario.getToken() == null) {
                    apiKey = jwtService.generateToken(usuario);
                    Token token = new Token();
                    token.setUsuario(usuario);
                    token.setToken(apiKey);
                    token.setFechaExpiracion(LocalDateTime.now().plusDays(1));
                    tokenService.save(token);

                    //Usuario con token caducado
                } else if (usuario.getToken().getFechaExpiracion().isBefore(LocalDateTime.now())) {
                    Token token = usuario.getToken();
                    apiKey = jwtService.generateToken(usuario);
                    token.setToken(apiKey);
                    token.setFechaExpiracion(LocalDateTime.now().plusDays(1));
                    tokenService.save(token);

                    //Usuario con token válido
                } else {
                    apiKey = usuario.getToken().getToken();
                }
            } else {
                mensaje = "Contraseña no válida";
            }
        } else {
            mensaje = "Usuario No encontrado";
        }

        return AuthDTO
                .builder()
                .token(apiKey)
                .info(mensaje)
                .build();
    }


    @PostMapping("/register")
    public AuthDTO register(@RequestBody ClienteDTO clienteDTO) {

        Cliente clieneNuevo = clienteService.save(clienteDTO);

        String token = jwtService.generateToken(clieneNuevo.getUsuario());

        return AuthDTO
                .builder()
                .token(token)
                .info("Usuario creado correctamente")
                .build();
    }
}