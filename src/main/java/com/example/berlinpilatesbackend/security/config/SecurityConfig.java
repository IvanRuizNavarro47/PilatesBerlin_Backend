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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@CrossOrigin(origins = {"/http://localhost:4200"})

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
                        authorizeRequests

                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()//todos los que coincidan con esta ruta van a ser públicos
                                .requestMatchers("/cliente/**").permitAll()
                                .requestMatchers("/admin/**").permitAll()
                                .requestMatchers("/clases/gestion/**").permitAll()
                                .requestMatchers("/clases/cliente/**").permitAll()
                                .anyRequest().authenticated()//En cambio, cuañquier otro request

                )


                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(withDefaults()) // Habilitar CORS

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
