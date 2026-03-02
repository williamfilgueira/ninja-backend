package com.ninja_br.poc.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "machines")
public class Machine {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 20)
    private String environment; // LAB/PROD/HML etc (string simples)

    @Column(name = "token_hash", nullable = false, length = 64, unique = true)
    private String tokenHash;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "dt_criacao", nullable = false)
    private LocalDateTime dtCriacao;

    @Column(name = "dt_atz", nullable = false)
    private LocalDateTime dtAtz;

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        if (this.dtCriacao == null) this.dtCriacao = now;
        this.dtAtz = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.dtAtz = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDateTime dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public LocalDateTime getDtAtz() {
        return dtAtz;
    }

    public void setDtAtz(LocalDateTime dtAtz) {
        this.dtAtz = dtAtz;
    }
}
