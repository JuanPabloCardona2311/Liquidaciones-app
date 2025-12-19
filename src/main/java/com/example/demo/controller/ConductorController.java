package com.example.demo.controller;

import com.example.demo.dto.ConductorDTO;
import com.example.demo.service.ConductorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    private final ConductorService conductorService;

    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    @GetMapping
    public ResponseEntity<List<ConductorDTO>> listar() {
        return ResponseEntity.ok(conductorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConductorDTO> obtener(@PathVariable Long id) {
        ConductorDTO conductor = conductorService.obtenerPorId(id);
        if (conductor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conductor);
    }

    @PostMapping
    public ResponseEntity<ConductorDTO> crear(@RequestBody ConductorDTO dto) {
        ConductorDTO creado = conductorService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConductorDTO> actualizar(@PathVariable Long id, @RequestBody ConductorDTO dto) {
        ConductorDTO actualizado = conductorService.actualizar(id, dto);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        conductorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
