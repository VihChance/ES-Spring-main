package com.example.spring.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtilitario jwtUtilitario;

    // Injeta JwtUtilitario via construtor
    public JwtFilter(JwtUtilitario jwtUtilitario) {
        this.jwtUtilitario = jwtUtilitario;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 1️ Não tem token → continua sem autenticação
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            // Validar token usando o bean
            Claims claims = jwtUtilitario.validateToken(token);

            String email = claims.getSubject();
            String role = claims.get("role", String.class);

            //  Criar authority (ROLE_DOCENTE / ROLE_ALUNO)
            var authority = new SimpleGrantedAuthority("ROLE_" + role);

            //  Criar Authentication
            var auth = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    List.of(authority)
            );

            //  Guardar no contexto do Spring
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            // Token inválido → ignora (Spring vai bloquear depois)
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
