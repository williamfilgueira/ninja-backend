package com.ninja_br.poc.controller;

import com.ninja_br.poc.model.dto.MachineListItemResponse;
import com.ninja_br.poc.service.MachineAdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/machines")
public class MachineAdminController {

    private final MachineAdminService machineAdminService;

    public MachineAdminController(MachineAdminService machineAdminService) {
        this.machineAdminService = machineAdminService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<MachineListItemResponse> listMachines() {
        return machineAdminService.list();
    }
}
