package com.ninja_br.poc.service;

import com.ninja_br.poc.model.dto.DashboardStatsResponse;
import com.ninja_br.poc.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final UsuarioRepository usuarioRepository;

    public DashboardService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public DashboardStatsResponse getStats() {
        long totalUsuarios = usuarioRepository.count();
        return new DashboardStatsResponse(totalUsuarios);
    }
}