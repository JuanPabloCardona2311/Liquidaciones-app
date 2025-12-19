package com.example.demo.repository;

import com.example.demo.model.Viaje;
import com.example.demo.model.Camion;
import com.example.demo.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    List<Viaje> findByConductorAndFechaViajeBetween(Conductor conductor, LocalDate inicio, LocalDate fin);
    List<Viaje> findByCamionAndFechaViajeBetween(Camion camion, LocalDate inicio, LocalDate fin);
}
