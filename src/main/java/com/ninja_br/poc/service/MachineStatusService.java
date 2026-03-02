package com.ninja_br.poc.service;

import com.ninja_br.poc.model.MachineStatus;
import com.ninja_br.poc.model.MachineStatusLast;
import com.ninja_br.poc.model.dto.AgentStatusRequest;
import com.ninja_br.poc.repository.MachineStatusLastRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class MachineStatusService {

    private final MachineStatusLastRepository statusLastRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MachineStatusService(MachineStatusLastRepository statusLastRepository) {
        this.statusLastRepository = statusLastRepository;
    }

    @Transactional
    public void upsertLastStatus(String machineId, AgentStatusRequest request) {
        if (request == null || request.machine_status() == null || request.machine_status().status == null) {
            throw new IllegalArgumentException("Invalid request payload");
        }

        MachineStatus st = MachineStatus.valueOf(request.machine_status().status.trim().toUpperCase());

        MachineStatusLast row = statusLastRepository.findById(machineId)
                .orElseGet(() -> {
                    MachineStatusLast newStatus = new MachineStatusLast();
                    newStatus.setMachineId(machineId);
                    return newStatus;
                });
        row.setStatus(st);
        row.setEmailUser(request.machine_status().emailUser);
        row.setLastSeenAt(LocalDateTime.now());

        try{
            row.setPayloadJson(objectMapper.writeValueAsString(request));
        }catch (Exception e){
            row.setPayloadJson(null);
        }

        statusLastRepository.save(row);
    }

}
