package com.example.demo.controller;

import com.example.demo.dto.AsignacionCamionConductorDTO;
import com.example.demo.service.AsignacionCamionConductorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionCamionConductorController {

    private final AsignacionCamionConductorService asignacionService;

    public AsignacionCamionConductorController(AsignacionCamionConductorService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @GetMapping
    public ResponseEntity<List<AsignacionCamionConductorDTO>> listar() {
        return ResponseEntity.ok(asignacionService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignacionCamionConductorDTO> obtener(@PathVariable Long id) {
        AsignacionCamionConductorDTO asignacion = asignacionService.obtenerPorId(id);
        if (asignacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(asignacion);
    }

    @PostMapping
    public ResponseEntity<AsignacionCamionConductorDTO> crear(@RequestBody AsignacionCamionConductorDTO dto) {
        try {
            AsignacionCamionConductorDTO creada = asignacionService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignacionCamionConductorDTO> actualizar(@PathVariable Long id, @RequestBody AsignacionCamionConductorDTO dto) {
        AsignacionCamionConductorDTO actualizada = asignacionService.actualizar(id, dto);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        asignacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
