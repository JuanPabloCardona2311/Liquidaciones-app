package com.example.demo.controller;

import com.example.demo.dto.ViajeDTO;
import com.example.demo.service.ViajeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viajes")
public class ViajeController {

    private final ViajeService viajeService;

    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    @GetMapping
    public ResponseEntity<List<ViajeDTO>> listar() {
        return ResponseEntity.ok(viajeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViajeDTO> obtener(@PathVariable Long id) {
        ViajeDTO viaje = viajeService.obtenerPorId(id);
        if (viaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viaje);
    }

    @PostMapping
    public ResponseEntity<ViajeDTO> crear(@RequestBody ViajeDTO dto) {
        try {
            ViajeDTO creado = viajeService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViajeDTO> actualizar(@PathVariable Long id, @RequestBody ViajeDTO dto) {
        ViajeDTO actualizado = viajeService.actualizar(id, dto);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        viajeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/conductor/{conductorId}/mes/{mes}/anio/{anio}")
    public ResponseEntity<List<ViajeDTO>> viajesPorConductorEnMes(
            @PathVariable Long conductorId,
            @PathVariable int mes,
            @PathVariable int anio) {
        return ResponseEntity.ok(viajeService.viajesPorConductorEnMes(conductorId, mes, anio));
    }
}
