package com.example.demo.repository;

import com.example.demo.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConductorRepository extends JpaRepository<Conductor, Long> {
    Optional<Conductor> findByCedula(String cedula);
}
