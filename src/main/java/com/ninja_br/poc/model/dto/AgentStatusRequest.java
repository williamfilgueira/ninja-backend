package com.ninja_br.poc.model.dto;

public record AgentStatusRequest(MachineStatusPayload machine_status) {

    public static class MachineStatusPayload {
        public String status;
        public String emailUser;
    }
}
