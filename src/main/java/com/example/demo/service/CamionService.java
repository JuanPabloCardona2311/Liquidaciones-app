package com.example.demo.service;

import com.example.demo.dto.CamionDTO;
import com.example.demo.model.Camion;
import com.example.demo.repository.CamionRepository;
import com.example.demo.repository.AsignacionCamionConductorRepository;
import com.example.demo.repository.ViajeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CamionService {

    private final CamionRepository camionRepository;
    private final AsignacionCamionConductorRepository asignacionRepository;
    private final ViajeRepository viajeRepository;

    public CamionService(CamionRepository camionRepository,
                         AsignacionCamionConductorRepository asignacionRepository,
                         ViajeRepository viajeRepository) {
        this.camionRepository = camionRepository;
        this.asignacionRepository = asignacionRepository;
        this.viajeRepository = viajeRepository;
    }

    public CamionDTO crear(CamionDTO dto) {
        Camion camion = new Camion();
        camion.setPlaca(dto.getPlaca());
        camion.setMarca(dto.getMarca());
        camion.setColor(dto.getColor());
        camion.setModelo(dto.getModelo());
        camion.setAnio(dto.getAnio());
        camion.setEstado(dto.getEstado());
        
        Camion guardado = camionRepository.save(camion);
        return convertirADTO(guardado);
    }

    public List<CamionDTO> listarTodos() {
        return camionRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CamionDTO obtenerPorId(Long id) {
        return camionRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    public CamionDTO actualizar(Long id, CamionDTO dto) {
        Camion camion = camionRepository.findById(id).orElse(null);
        if (camion == null) return null;
        
        camion.setPlaca(dto.getPlaca());
        camion.setMarca(dto.getMarca());
        camion.setColor(dto.getColor());
        camion.setModelo(dto.getModelo());
        camion.setAnio(dto.getAnio());
        camion.setEstado(dto.getEstado());
        
        return convertirADTO(camionRepository.save(camion));
    }

    public void eliminar(Long id) {
        Camion camion = camionRepository.findById(id).orElse(null);
        if (camion == null) return;

        boolean tieneViajes = !viajeRepository.findByCamionAndFechaViajeBetween(camion, java.time.LocalDate.MIN, java.time.LocalDate.MAX).isEmpty();

        if (tieneViajes) {
            throw new RuntimeException("No se puede eliminar el camión: primero elimine los viajes asociados.");
        }

        // Eliminar automáticamente asignaciones del camión
        java.util.List<com.example.demo.model.AsignacionCamionConductor> asignaciones = asignacionRepository.findByCamion(camion);
        if (!asignaciones.isEmpty()) {
            asignacionRepository.deleteAll(asignaciones);
        }

        camionRepository.deleteById(id);
    }

    private CamionDTO convertirADTO(Camion camion) {
        return new CamionDTO(
                camion.getId(),
                camion.getPlaca(),
                camion.getMarca(),
                camion.getColor(),
                camion.getModelo(),
                camion.getAnio(),
                camion.getEstado()
        );
    }
}
