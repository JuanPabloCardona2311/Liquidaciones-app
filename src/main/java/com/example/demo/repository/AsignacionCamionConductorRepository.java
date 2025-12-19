package com.example.demo.repository;

import com.example.demo.model.AsignacionCamionConductor;
import com.example.demo.model.Camion;
import com.example.demo.model.Conductor;
import com.example.demo.model.enums.EstadoAsignacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AsignacionCamionConductorRepository extends JpaRepository<AsignacionCamionConductor, Long> {
    List<AsignacionCamionConductor> findByCamion(Camion camion);
    List<AsignacionCamionConductor> findByConductor(Conductor conductor);
    Optional<AsignacionCamionConductor> findFirstByCamionAndEstadoOrderByFechaInicioDesc(Camion camion, EstadoAsignacion estado);
    Optional<AsignacionCamionConductor> findFirstByConductorAndEstadoOrderByFechaInicioDesc(Conductor conductor, EstadoAsignacion estado);
    List<AsignacionCamionConductor> findByCamionAndEstado(Camion camion, EstadoAsignacion estado);
    List<AsignacionCamionConductor> findByConductorAndEstado(Conductor conductor, EstadoAsignacion estado);

    // Buscar asignaciones activas que cubran una fecha
    List<AsignacionCamionConductor> findByCamionAndEstadoAndFechaInicioLessThanEqualAndFechaFinIsNullOrFechaFinGreaterThanEqual(
            Camion camion,
            EstadoAsignacion estado,
            LocalDate fecha,
            LocalDate fecha2
    );
}
