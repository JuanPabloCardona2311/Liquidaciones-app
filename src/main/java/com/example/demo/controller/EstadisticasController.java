package com.example.demo.controller;

import com.example.demo.dto.EstadisticaCamionMensualDTO;
import com.example.demo.service.LiquidacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    private final LiquidacionService liquidacionService;

    public EstadisticasController(LiquidacionService liquidacionService) {
        this.liquidacionService = liquidacionService;
    }

    @GetMapping("/camiones")
    public ResponseEntity<List<EstadisticaCamionMensualDTO>> estadisticasCamiones(
            @RequestParam(name = "anio", required = false) Integer anio) {
        int yearToUse = (anio != null) ? anio : Year.now().getValue();
        List<EstadisticaCamionMensualDTO> data = liquidacionService.generarEstadisticasCamiones(yearToUse);
        return ResponseEntity.ok(data);
    }
}
