package com.ninja_br.poc.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "permissao", nullable = false, length = 20)
    private Role permissao;

    @Column(name = "dt_criacao", nullable = false)
    private Instant dtCriacao;

    @Column(name = "dt_atz", nullable = false)
    private Instant dtAtz;

    @Column(nullable = false)
    private boolean bloqueado;

    @Column(name = "expira_em")
    private LocalDateTime expiraEm;

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID().toString();
        Instant now = Instant.now();
        if (this.dtCriacao == null) this.dtCriacao = now;
        this.dtAtz = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.dtAtz = Instant.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getPermissao() {
        return permissao;
    }

    public void setPermissao(Role permissao) {
        this.permissao = permissao;
    }

    public Instant getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(Instant dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public Instant getDtAtz() {
        return dtAtz;
    }

    public void setDtAtz(Instant dtAtz) {
        this.dtAtz = dtAtz;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public LocalDateTime getExpiraEm() {
        return expiraEm;
    }

    public void setExpiraEm(LocalDateTime expiraEm) {
        this.expiraEm = expiraEm;
    }
}
