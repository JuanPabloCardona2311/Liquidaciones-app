package com.example.demo.dto;

import com.example.demo.model.enums.EstadoViaje;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViajeDTO {
    private Long id;
    private Long camionId;
    private Long conductorId;
    private String camionPlaca;
    private String conductorNombre;
    private LocalDate fechaViaje;
    private String origen;
    private String destino;
    private String empresaCliente;
    private String producto;
    private BigDecimal peso;
    private String numeroRemision;
    private String manifiesto;
    private BigDecimal valorFlete;
    private BigDecimal anticipo;
    private BigDecimal porcentajeConductor;
    private BigDecimal valorAcpm;
    private BigDecimal valorPeajes;
    private BigDecimal valorCargue;
    private BigDecimal valorDescargue;
    private BigDecimal valorParqueo;
    private BigDecimal transporteConductor;
    private BigDecimal valorMontajeLlantas;
    private BigDecimal valorCambioAceite;
    private BigDecimal valorLavada;
    private BigDecimal valorEngrase;
    private BigDecimal valorTensionFrenos;
    private BigDecimal otrosGastos;
    private String descripcionOtros;
    private EstadoViaje estado;
    private BigDecimal pagoConductor;
    private BigDecimal totalGastos;
}
