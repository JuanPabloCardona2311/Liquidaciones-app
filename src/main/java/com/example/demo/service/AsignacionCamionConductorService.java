package com.example.demo.service;

import com.example.demo.dto.AsignacionCamionConductorDTO;
import com.example.demo.model.AsignacionCamionConductor;
import com.example.demo.model.Camion;
import com.example.demo.model.Conductor;
import com.example.demo.repository.AsignacionCamionConductorRepository;
import com.example.demo.repository.CamionRepository;
import com.example.demo.repository.ConductorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionCamionConductorService {

    private final AsignacionCamionConductorRepository asignacionRepository;
    private final CamionRepository camionRepository;
    private final ConductorRepository conductorRepository;

    public AsignacionCamionConductorService(
            AsignacionCamionConductorRepository asignacionRepository,
            CamionRepository camionRepository,
            ConductorRepository conductorRepository) {
        this.asignacionRepository = asignacionRepository;
        this.camionRepository = camionRepository;
        this.conductorRepository = conductorRepository;
    }

    public AsignacionCamionConductorDTO crear(AsignacionCamionConductorDTO dto) {
        Camion camion = camionRepository.findById(dto.getCamionId())
                .orElseThrow(() -> new RuntimeException("Camión no encontrado"));
        Conductor conductor = conductorRepository.findById(dto.getConductorId())
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        AsignacionCamionConductor asignacion = new AsignacionCamionConductor();
        asignacion.setCamion(camion);
        asignacion.setConductor(conductor);
        asignacion.setFechaInicio(dto.getFechaInicio());
        asignacion.setFechaFin(dto.getFechaFin());
        asignacion.setEstado(dto.getEstado());

        AsignacionCamionConductor guardada = asignacionRepository.save(asignacion);
        return convertirADTO(guardada);
    }

    public List<AsignacionCamionConductorDTO> listarTodas() {
        return asignacionRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public AsignacionCamionConductorDTO obtenerPorId(Long id) {
        return asignacionRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    public AsignacionCamionConductorDTO actualizar(Long id, AsignacionCamionConductorDTO dto) {
        AsignacionCamionConductor asignacion = asignacionRepository.findById(id).orElse(null);
        if (asignacion == null) return null;

        asignacion.setFechaInicio(dto.getFechaInicio());
        asignacion.setFechaFin(dto.getFechaFin());
        asignacion.setEstado(dto.getEstado());

        return convertirADTO(asignacionRepository.save(asignacion));
    }

    public void eliminar(Long id) {
        asignacionRepository.deleteById(id);
    }

    private AsignacionCamionConductorDTO convertirADTO(AsignacionCamionConductor asignacion) {
        return new AsignacionCamionConductorDTO(
                asignacion.getId(),
                asignacion.getCamion().getId(),
                asignacion.getConductor().getId(),
                asignacion.getFechaInicio(),
                asignacion.getFechaFin(),
                asignacion.getEstado()
        );
    }
}
