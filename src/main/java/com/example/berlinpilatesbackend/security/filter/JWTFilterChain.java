package com.example.berlinpilatesbackend.security.filter;

import com.example.berlinpilatesbackend.model.Usuario;
import com.example.berlinpilatesbackend.security.auth.TokenDataDTO;
import com.example.berlinpilatesbackend.security.jwt.JWTService;
import com.example.berlinpilatesbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JWTFilterChain extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Excluir la autenticación para rutas específicas
        if (request.getServletPath().startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Permitir acceso público para la creación de comentarios
        if (request.getServletPath().startsWith("/comentarios/crear")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Si no hay cabecera de autorización o no comienza con Bearer, continuar con el siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token
        String token = authHeader.substring(7);
        TokenDataDTO tokenDataDTO = jwtService.extractTokenData(token);

        // Verificar si el token es válido y si no hay autenticación actual
        if (tokenDataDTO != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Usuario usuario = usuarioService.buscarPorUsername(tokenDataDTO.getUsername());

            if (usuario != null && !jwtService.isExpired(token)) {
                // Crear la autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        usuario.getUsername(),
                        null, // Aquí debería ser la contraseña, pero no se utiliza en JWT
                        usuario.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
