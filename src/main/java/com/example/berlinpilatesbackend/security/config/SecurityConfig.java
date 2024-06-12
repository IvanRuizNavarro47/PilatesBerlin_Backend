package com.example.berlinpilatesbackend.security.config;

import com.example.berlinpilatesbackend.enums.Rol;
import com.example.berlinpilatesbackend.security.filter.JWTFilterChain;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTFilterChain jwtFilterChain;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JWTFilterChain jwtFilterChain, AuthenticationProvider authenticationProvider) {
        this.jwtFilterChain = jwtFilterChain;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())//medida de seguridad para agregar a los métodos post una autenticación basada en tokens
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests//
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()//todos los que coincidan con esta ruta van a ser públicos
                                .requestMatchers("/cliente/**").permitAll()
                                .requestMatchers("/admin/**").hasAnyAuthority(Rol.ADMIN.name())
                                .requestMatchers("/clases/gestion/**").hasAnyAuthority(Rol.MONITOR.name())
                                .requestMatchers("/clases/cliente/**").hasAnyAuthority(Rol.USUARIO.name())
                                .anyRequest().authenticated()//En cambio, cuañquier otro request
                )


                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
