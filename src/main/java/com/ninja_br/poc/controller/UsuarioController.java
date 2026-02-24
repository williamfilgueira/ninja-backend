package com.ninja_br.poc.controller;

import com.ninja_br.poc.model.dto.CreateUserRequest;
import com.ninja_br.poc.model.dto.UpdateUserRequest;
import com.ninja_br.poc.model.dto.UserResponse;
import com.ninja_br.poc.model.Usuario;
import com.ninja_br.poc.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @GetMapping
    public List<UserResponse> listar() {
        return service.listar().stream().map(this::toResponse).toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @GetMapping("/{id}")
    public UserResponse buscar(@PathVariable String id) {
        return toResponse(service.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserResponse criar(@RequestBody @Valid CreateUserRequest req) {
        return toResponse(service.criarComoAdmin(req));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderator")
    public UserResponse criarComoModerator(@RequestBody @Valid CreateUserRequest req) {
        return toResponse(service.criarComoModerator(req));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserResponse atualizar(@PathVariable String id, @RequestBody @Valid UpdateUserRequest req) {
        return toResponse(service.atualizarComoAdmin(id, req));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletarComoAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @PatchMapping("/{id}/block")
    public UserResponse bloquear(@PathVariable String id) {
        return toResponse(service.bloquear(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @PatchMapping("/{id}/unblock")
    public UserResponse desbloquear(@PathVariable String id) {
        return toResponse(service.desbloquear(id));
    }

    private UserResponse toResponse(Usuario u) {
        return new UserResponse(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getPermissao().name(),
                u.isBloqueado(),
                u.getExpiraEm(),
                u.getDtCriacao(),
                u.getDtAtz()
        );
    }
}