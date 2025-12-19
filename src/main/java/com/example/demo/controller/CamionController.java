package com.example.demo.controller;

import com.example.demo.dto.CamionDTO;
import com.example.demo.service.CamionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camiones")
public class CamionController {

    private final CamionService camionService;

    public CamionController(CamionService camionService) {
        this.camionService = camionService;
    }

    @GetMapping
    public ResponseEntity<List<CamionDTO>> listar() {
        return ResponseEntity.ok(camionService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamionDTO> obtener(@PathVariable Long id) {
        CamionDTO camion = camionService.obtenerPorId(id);
        if (camion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(camion);
    }

    @PostMapping
    public ResponseEntity<CamionDTO> crear(@RequestBody CamionDTO dto) {
        CamionDTO creado = camionService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CamionDTO> actualizar(@PathVariable Long id, @RequestBody CamionDTO dto) {
        CamionDTO actualizado = camionService.actualizar(id, dto);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        camionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
