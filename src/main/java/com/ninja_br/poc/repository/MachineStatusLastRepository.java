package com.ninja_br.poc.repository;

import com.ninja_br.poc.model.Machine;
import com.ninja_br.poc.model.MachineStatusLast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MachineStatusLastRepository extends JpaRepository<MachineStatusLast, String> {

}
