package com.ninja_br.poc.config;

import com.ninja_br.poc.model.Role;
import com.ninja_br.poc.model.Usuario;
import com.ninja_br.poc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements ApplicationRunner {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    @Value("${app.bootstrap.admin.email}")
    private String adminEmail;

    @Value("${app.bootstrap.admin.password}")
    private String adminPassword;

    public AdminSeeder(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        String email = adminEmail.trim().toLowerCase();

        if (repo.findByEmail(email).isPresent()) {
            return;
        }

        Usuario admin = new Usuario();
        admin.setNome("Admin");
        admin.setEmail(email);
        admin.setPermissao(Role.ADMIN);
        admin.setSenha(encoder.encode(adminPassword));
        admin.setBloqueado(false);
        admin.setExpiraEm(null);

        repo.save(admin);

        System.out.println(">>> Admin bootstrap criado: " + email);
    }
}