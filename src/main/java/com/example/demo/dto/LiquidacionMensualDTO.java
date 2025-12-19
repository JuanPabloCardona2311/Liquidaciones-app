package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiquidacionMensualDTO {
    private Long conductorId;
    private String nombreConductor;
    private String apellidoConductor;
    private int mes;
    private int anio;
    private int totalViajes;
    private BigDecimal totalFleteGenerado;
    private BigDecimal porcentajeConductor;
    private BigDecimal gananciasConductor;
    private BigDecimal totalGastosViajes;
    private BigDecimal totalAnticipos;
    private BigDecimal neto;
}
