package com.ninja_br.poc.model.dto;

import java.time.LocalDateTime;

public record MachineListItemResponse(
        String id,
        String name,
        String environment,
        String status,
        String emailUser,
        LocalDateTime lastSeenAt,
        boolean timedOut
) {
}
