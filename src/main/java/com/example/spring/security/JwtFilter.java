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

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 1️⃣ Não tem token → continua sem autenticação
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            // 2️⃣ Validar token
            Claims claims = JwtUtilitario.validateToken(token);

            String email = claims.getSubject();
            String role = claims.get("role", String.class);

            // 3️⃣ Criar authority (ROLE_DOCENTE / ROLE_ALUNO)
            var authority = new SimpleGrantedAuthority("ROLE_" + role);

            // 4️⃣ Criar Authentication
            var auth = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    List.of(authority)
            );

            // 5️⃣ Guardar no contexto do Spring
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            // Token inválido → ignora (Spring vai bloquear depois)
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
