package com.ninja_br.poc.service;

import com.ninja_br.poc.model.dto.CreateUserRequest;
import com.ninja_br.poc.model.dto.UpdateUserRequest;
import com.ninja_br.poc.model.Role;
import com.ninja_br.poc.model.Usuario;
import com.ninja_br.poc.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional
    public Usuario criarComoAdmin(CreateUserRequest req) {
        String email = normEmail(req.email());
        if (usuarioRepository.existsByEmail(email)) throw new RuntimeException("Email já cadastrado");

        Role role = parseRole(req.role());

        Usuario user = new Usuario();
        user.setNome(req.nome() == null ? "Sem nome" : req.nome().trim());
        user.setEmail(email);
        user.setSenha(passwordEncoder.encode(req.senha()));
        user.setPermissao(role);


        if (role == Role.USER) {
            user.setExpiraEm(req.expiraEm());
        } else {
            user.setExpiraEm(null);
        }

        user.setBloqueado(false);
        return usuarioRepository.save(user);
    }

    @Transactional
    public Usuario criarComoModerator(CreateUserRequest req) {
        String email = normEmail(req.email());
        if (usuarioRepository.existsByEmail(email)) throw new RuntimeException("Email já cadastrado");

        // Moderator só cria USER
        Role role = Role.USER;

        if (req.expiraEm() == null) throw new RuntimeException("expiraEm é obrigatório para criação por MODERATOR");
        if (!req.expiraEm().isAfter(LocalDateTime.now())) throw new RuntimeException("expiraEm deve ser no futuro");

        Usuario user = new Usuario();
        user.setNome(req.nome() == null ? "Sem nome" : req.nome().trim());
        user.setEmail(email);
        user.setSenha(passwordEncoder.encode(req.senha()));
        user.setPermissao(role);
        user.setExpiraEm(req.expiraEm());
        user.setBloqueado(false);

        return usuarioRepository.save(user);
    }

    @Transactional
    public Usuario atualizarComoAdmin(String id, UpdateUserRequest req) {
        Usuario u = buscarPorId(id);

        if (req.nome() != null && !req.nome().isBlank()) u.setNome(req.nome().trim());
        if (req.email() != null && !req.email().isBlank()) {
            String email = normEmail(req.email());
            if (usuarioRepository.existsByEmailAndIdNot(email, id)) throw new RuntimeException("Email já cadastrado");
            u.setEmail(email);
        }
        if (req.senha() != null && !req.senha().isBlank()) u.setSenha(passwordEncoder.encode(req.senha()));

        if (req.role() != null && !req.role().isBlank()) {
            Role role = parseRole(req.role());
            u.setPermissao(role);

            // admin/mod nunca expiram
            if (role != Role.USER) u.setExpiraEm(null);
        }

        if (req.expiraEm() != null) {
            // só faz sentido para USER
            if (u.getPermissao() == Role.USER) u.setExpiraEm(req.expiraEm());
        }

        if (req.bloqueado() != null) u.setBloqueado(req.bloqueado());

        return usuarioRepository.save(u);
    }

    @Transactional
    public void deletarComoAdmin(String id) {
        if (!usuarioRepository.existsById(id)) throw new RuntimeException("Usuário não encontrado");
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public Usuario bloquear(String id) {
        Usuario u = buscarPorId(id);
        u.setBloqueado(true);
        return usuarioRepository.save(u);
    }

    @Transactional
    public Usuario desbloquear(String id) {
        Usuario u = buscarPorId(id);
        u.setBloqueado(false);
        return usuarioRepository.save(u);
    }

    private String normEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    private Role parseRole(String role) {
        try {
            return Role.valueOf(role.trim().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Role inválida. Use: ADMIN, MODERATOR, USER");
        }
    }
}