package com.example.infra.security;

import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.domain.user.User;
import com.example.repositories.UserRepository;
import com.example.infra.security.TokenService;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;


    // doFilterInternal é o método que vai ser chamado para cada requisição, ele vai verificar se o token é válido e se for, vai setar o usuário 
    // no contexto de segurança.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if(login != null){
            User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User not found"));
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    // recoverToken é o método que vai recuperar o token do header da requisição, ele vai verificar se o header 
    // Authorization existe e se ele começa com "Bearer ", se sim, ele vai retornar o token, se não, ele vai retornar null.
    private String recoverToken(HttpServletRequest request){
        var authHeader =  request.getHeader("Authorization");
        if(authHeader == null || authHeader.isEmpty()){
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }


}
