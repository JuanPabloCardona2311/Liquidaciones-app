package com.example.demo.service;

import com.example.demo.dto.ConductorDTO;
import com.example.demo.model.Conductor;
import com.example.demo.repository.ConductorRepository;
import com.example.demo.repository.AsignacionCamionConductorRepository;
import com.example.demo.repository.ViajeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConductorService {

    private final ConductorRepository conductorRepository;
    private final AsignacionCamionConductorRepository asignacionRepository;
    private final ViajeRepository viajeRepository;

    public ConductorService(ConductorRepository conductorRepository,
                            AsignacionCamionConductorRepository asignacionRepository,
                            ViajeRepository viajeRepository) {
        this.conductorRepository = conductorRepository;
        this.asignacionRepository = asignacionRepository;
        this.viajeRepository = viajeRepository;
    }

    public ConductorDTO crear(ConductorDTO dto) {
        Conductor conductor = new Conductor();
        conductor.setCedula(dto.getCedula());
        conductor.setNombre(dto.getNombre());
        conductor.setApellido(dto.getApellido());
        conductor.setTelefono(dto.getTelefono());
        conductor.setEmail(dto.getEmail());
        conductor.setFechaContratacion(dto.getFechaContratacion());
        conductor.setPorcentajeFlete(dto.getPorcentajeFlete());
        conductor.setEstado(dto.getEstado());
        
        Conductor guardado = conductorRepository.save(conductor);
        return convertirADTO(guardado);
    }

    public List<ConductorDTO> listarTodos() {
        return conductorRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ConductorDTO obtenerPorId(Long id) {
        return conductorRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    public ConductorDTO actualizar(Long id, ConductorDTO dto) {
        Conductor conductor = conductorRepository.findById(id).orElse(null);
        if (conductor == null) return null;
        
        conductor.setCedula(dto.getCedula());
        conductor.setNombre(dto.getNombre());
        conductor.setApellido(dto.getApellido());
        conductor.setTelefono(dto.getTelefono());
        conductor.setEmail(dto.getEmail());
        conductor.setFechaContratacion(dto.getFechaContratacion());
        conductor.setPorcentajeFlete(dto.getPorcentajeFlete());
        conductor.setEstado(dto.getEstado());
        
        return convertirADTO(conductorRepository.save(conductor));
    }

    public void eliminar(Long id) {
        Conductor conductor = conductorRepository.findById(id).orElse(null);
        if (conductor == null) return;

        boolean tieneViajes = !viajeRepository.findByConductorAndFechaViajeBetween(conductor, java.time.LocalDate.MIN, java.time.LocalDate.MAX).isEmpty();

        if (tieneViajes) {
            throw new RuntimeException("No se puede eliminar el conductor: primero elimine los viajes asociados.");
        }

        // Eliminar automáticamente asignaciones del conductor
        java.util.List<com.example.demo.model.AsignacionCamionConductor> asignaciones = asignacionRepository.findByConductor(conductor);
        if (!asignaciones.isEmpty()) {
            asignacionRepository.deleteAll(asignaciones);
        }

        conductorRepository.deleteById(id);
    }

    private ConductorDTO convertirADTO(Conductor conductor) {
        return new ConductorDTO(
                conductor.getId(),
                conductor.getCedula(),
                conductor.getNombre(),
                conductor.getApellido(),
                conductor.getTelefono(),
                conductor.getEmail(),
                conductor.getFechaContratacion(),
                conductor.getPorcentajeFlete(),
                conductor.getEstado()
        );
    }
}
