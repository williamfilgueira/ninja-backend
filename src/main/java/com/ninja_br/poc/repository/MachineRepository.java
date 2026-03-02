package com.ninja_br.poc.repository;

import com.ninja_br.poc.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MachineRepository extends JpaRepository<Machine, String> {

    Optional<Machine> findByTokenHashAndActiveTrue(String tokenHash);
}
