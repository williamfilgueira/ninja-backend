package com.ninja_br.poc.controller;

import com.ninja_br.poc.model.dto.AgentStatusRequest;
import com.ninja_br.poc.service.MachineStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final MachineStatusService machineStatusService;

    public AgentController(MachineStatusService machineStatusService) {
        this.machineStatusService = machineStatusService;
    }

    @PostMapping("/status")
    public ResponseEntity<Void> postStatus(@RequestBody AgentStatusRequest req, Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            return ResponseEntity.status(401).build();
        }

        String machineId = auth.getPrincipal().toString();
        machineStatusService.upsertLastStatus(machineId, req);
        return ResponseEntity.ok().build();
    }


}
