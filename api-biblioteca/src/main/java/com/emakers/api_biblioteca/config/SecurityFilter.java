package com.emakers.api_biblioteca.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.AuthorizationService;
import com.emakers.api_biblioteca.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
    
    @Autowired
    TokenService tokenService;
    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    AuthorizationService authorizationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/auth/register")) {
            filterChain.doFilter(request, response);
            return; 
            }
        var token = this.recoverToken(request);
        if(token != null){
            var email = tokenService.validateToken(token);
            UserDetails pessoa = authorizationService.loadUserByUsername(email);

            var authentication = new UsernamePasswordAuthenticationToken(pessoa, null, pessoa.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
            }
    
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

}
