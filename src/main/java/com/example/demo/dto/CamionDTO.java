package com.example.demo.dto;

import com.example.demo.model.enums.EstadoCamion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamionDTO {
    private Long id;
    private String placa;
    private String marca;
    private String color;
    private String modelo;
    private Integer anio;
    private EstadoCamion estado;
}
