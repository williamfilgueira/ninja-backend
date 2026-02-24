package com.ninja_br.poc.service;

import com.ninja_br.poc.model.Role;
import com.ninja_br.poc.model.Usuario;
import com.ninja_br.poc.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        List<SimpleGrantedAuthority> auths =
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getPermissao().name()));

        boolean isAdminOrMod = user.getPermissao() == Role.ADMIN || user.getPermissao() == Role.MODERATOR;

        boolean accountNonExpired = isAdminOrMod || user.getExpiraEm() == null || user.getExpiraEm().isAfter(LocalDateTime.now());
        boolean enabled = !user.isBloqueado();

        return User.withUsername(user.getEmail())
                .password(user.getSenha())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getPermissao().name())))
                .accountExpired(!accountNonExpired)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!enabled)
                .build();
        }
    }
