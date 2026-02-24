package com.ninja_br.poc.service;

import com.ninja_br.poc.model.Usuario;
import com.ninja_br.poc.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       UsuarioRepository usuarioRepository,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public LoginResult login(String email, String senha) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
        );

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        String token = jwtService.generateToken(usuario);

        return new LoginResult(
                token,
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPermissao().name()
        );
    }

    public static class LoginResult {
        private final String token;
        private final String userId;
        private final String nome;
        private final String email;
        private final String role;

        public LoginResult(String token, String userId, String nome, String email, String role) {
            this.token = token;
            this.userId = userId;
            this.nome = nome;
            this.email = email;
            this.role = role;
        }

        public String getToken() { return token; }
        public String getUserId() { return userId; }
        public String getNome() { return nome; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
    }
}