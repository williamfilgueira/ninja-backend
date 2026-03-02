package com.ninja_br.poc.service;

import com.ninja_br.poc.model.Machine;
import com.ninja_br.poc.model.MachineStatusLast;
import com.ninja_br.poc.model.dto.MachineListItemResponse;
import com.ninja_br.poc.repository.MachineRepository;
import com.ninja_br.poc.repository.MachineStatusLastRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MachineAdminService {

    private final MachineRepository machineRepository;
    private final MachineStatusLastRepository statusLastRepository;
    private final long ttlSeconds;

    public MachineAdminService(MachineRepository machineRepository, MachineStatusLastRepository statusLastRepository, @Value("${app.machine.lastseen-ttl-seconds}") long ttlSeconds) {
        this.machineRepository = machineRepository;
        this.statusLastRepository = statusLastRepository;
        this.ttlSeconds = ttlSeconds;
    }

    public List<MachineListItemResponse> list() {
        LocalDateTime now = LocalDateTime.now();

        return machineRepository.findAll().stream()
                .map(m -> toListItem(m, now))
                .toList();
    }

    private MachineListItemResponse toListItem(Machine m, LocalDateTime now) {
        Optional<MachineStatusLast> stOpt = statusLastRepository.findById(m.getId());

        String status = "unavailable";
        String emailUser = null;
        LocalDateTime lastSeenAt = null;
        boolean timedOut = true;

        if (stOpt.isPresent()) {
            MachineStatusLast st = stOpt.get();

            if (st.getStatus() != null) {
                status = st.getStatus().name().toLowerCase();
            }

            emailUser = st.getEmailUser();
            lastSeenAt = st.getLastSeenAt();

            if (lastSeenAt != null) {
                long seconds = Duration.between(lastSeenAt, now).getSeconds();
                timedOut = seconds > ttlSeconds;
            } else {
                timedOut = true;
            }

            if (timedOut) {
                status = "unavailable";
            }
        }

        return new MachineListItemResponse(
                m.getId(),
                m.getName(),
                m.getEnvironment(),
                status,
                emailUser,
                lastSeenAt,
                timedOut
        );
    }
}
