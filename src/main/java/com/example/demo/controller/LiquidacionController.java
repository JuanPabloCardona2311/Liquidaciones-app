package com.example.demo.controller;

import com.example.demo.dto.LiquidacionMensualDTO;
import com.example.demo.service.LiquidacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/liquidaciones")
public class LiquidacionController {

    private final LiquidacionService liquidacionService;

    public LiquidacionController(LiquidacionService liquidacionService) {
        this.liquidacionService = liquidacionService;
    }

    @GetMapping("/{conductorId}/mes/{mes}/anio/{anio}")
    public ResponseEntity<LiquidacionMensualDTO> obtenerLiquidacionConductor(
            @PathVariable Long conductorId,
            @PathVariable int mes,
            @PathVariable int anio) {
        try {
            LiquidacionMensualDTO liquidacion = liquidacionService.generarLiquidacionConductor(conductorId, mes, anio);
            return ResponseEntity.ok(liquidacion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mes/{mes}/anio/{anio}")
    public ResponseEntity<List<LiquidacionMensualDTO>> obtenerLiquidacionesMes(
            @PathVariable int mes,
            @PathVariable int anio) {
        List<LiquidacionMensualDTO> liquidaciones = liquidacionService.generarLiquidacionesTodosConductores(mes, anio);
        return ResponseEntity.ok(liquidaciones);
    }
}
