package com.example.demo.dto;

import java.math.BigDecimal;

public class EstadisticaCamionMensualDTO {
    private Long camionId;
    private String camionPlaca;
    private int mes;
    private int anio;
    private BigDecimal totalFlete;
    private BigDecimal totalGastos;
    private BigDecimal totalPagoConductor;
    private BigDecimal utilidadNeta;

    public EstadisticaCamionMensualDTO() {
    }

    public EstadisticaCamionMensualDTO(Long camionId,
                                       String camionPlaca,
                                       int mes,
                                       int anio,
                                       BigDecimal totalFlete,
                                       BigDecimal totalGastos,
                                       BigDecimal totalPagoConductor,
                                       BigDecimal utilidadNeta) {
        this.camionId = camionId;
        this.camionPlaca = camionPlaca;
        this.mes = mes;
        this.anio = anio;
        this.totalFlete = totalFlete;
        this.totalGastos = totalGastos;
        this.totalPagoConductor = totalPagoConductor;
        this.utilidadNeta = utilidadNeta;
    }

    public Long getCamionId() {
        return camionId;
    }

    public void setCamionId(Long camionId) {
        this.camionId = camionId;
    }

    public String getCamionPlaca() {
        return camionPlaca;
    }

    public void setCamionPlaca(String camionPlaca) {
        this.camionPlaca = camionPlaca;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public BigDecimal getTotalFlete() {
        return totalFlete;
    }

    public void setTotalFlete(BigDecimal totalFlete) {
        this.totalFlete = totalFlete;
    }

    public BigDecimal getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(BigDecimal totalGastos) {
        this.totalGastos = totalGastos;
    }

    public BigDecimal getTotalPagoConductor() {
        return totalPagoConductor;
    }

    public void setTotalPagoConductor(BigDecimal totalPagoConductor) {
        this.totalPagoConductor = totalPagoConductor;
    }

    public BigDecimal getUtilidadNeta() {
        return utilidadNeta;
    }

    public void setUtilidadNeta(BigDecimal utilidadNeta) {
        this.utilidadNeta = utilidadNeta;
    }
}
