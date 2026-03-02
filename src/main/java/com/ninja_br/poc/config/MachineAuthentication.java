package com.ninja_br.poc.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class MachineAuthentication extends AbstractAuthenticationToken {

    private final String machineId;

    public MachineAuthentication(String machineId) {
        super(List.of(new SimpleGrantedAuthority("ROLE_MACHINE")));
        this.machineId = machineId;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return machineId;
    }
}