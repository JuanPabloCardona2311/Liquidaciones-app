package com.example.demo.dto;

import com.example.demo.model.enums.EstadoAsignacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionCamionConductorDTO {
    private Long id;
    private Long camionId;
    private Long conductorId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoAsignacion estado;
}
