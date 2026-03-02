package com.ninja_br.poc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class MachineTokenHasher {

    private final String salt;

    public MachineTokenHasher(@Value("${app.machine.token.salt}")String salt) {
        System.out.println("MACHINE_TOKEN_SALT loaded: " + (salt == null ? "null" : "***" + salt.length()));
        this.salt = salt;
    }

    public String sha256Hex(String token) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest((token + salt).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}

