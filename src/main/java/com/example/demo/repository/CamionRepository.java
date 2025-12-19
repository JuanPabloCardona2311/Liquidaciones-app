package com.example.demo.repository;

import com.example.demo.model.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CamionRepository extends JpaRepository<Camion, Long> {
    Optional<Camion> findByPlaca(String placa);
}
