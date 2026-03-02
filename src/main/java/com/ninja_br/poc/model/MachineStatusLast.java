package com.ninja_br.poc.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "machine_status_last")
public class MachineStatusLast {

    @Id
    @Column(name = "machine_id", length = 36)
    private String machineId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MachineStatus status;

    @Column(name = "email_user", length = 160)
    private String emailUser;

    @Column(name = "last_seen_at", nullable = false)
    private LocalDateTime lastSeenAt;

    @Column(name = "payload_json", columnDefinition = "LONGTEXT")
    private String payloadJson;

    @Column(name = "dt_atz", nullable = false)
    private LocalDateTime dtAtz;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (this.lastSeenAt == null) this.lastSeenAt = now;
        this.dtAtz = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.dtAtz = LocalDateTime.now();
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public MachineStatus getStatus() {
        return status;
    }

    public void setStatus(MachineStatus status) {
        this.status = status;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public LocalDateTime getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(LocalDateTime lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    public String getPayloadJson() {
        return payloadJson;
    }

    public void setPayloadJson(String payloadJson) {
        this.payloadJson = payloadJson;
    }

    public LocalDateTime getDtAtz() {
        return dtAtz;
    }

    public void setDtAtz(LocalDateTime dtAtz) {
        this.dtAtz = dtAtz;
    }
}