package com.ninja_br.poc.config;

import com.ninja_br.poc.model.Machine;
import com.ninja_br.poc.repository.MachineRepository;
import com.ninja_br.poc.service.MachineTokenHasher;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MachineTokenAuthFilter extends OncePerRequestFilter {

    private final MachineRepository machineRepo;
    private final MachineTokenHasher hasher;

    public MachineTokenAuthFilter(MachineRepository machineRepo, MachineTokenHasher hasher) {
        this.machineRepo = machineRepo;
        this.hasher = hasher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (auth == null || !auth.startsWith("Bearer ")) {
            response.setStatus(401);
            return;
        }

        String token = auth.substring("Bearer ".length()).trim();
        if (token.isEmpty()) {
            response.setStatus(401);
            return;
        }

        String hash = hasher.sha256Hex(token);


        var machineOpt = machineRepo.findByTokenHashAndActiveTrue(hash);
        if (machineOpt.isEmpty()) {
            response.setStatus(401);
            return;
        }

        Machine machine = machineOpt.get();
        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(new MachineAuthentication(machine.getId()));
        chain.doFilter(request, response);
    }
}