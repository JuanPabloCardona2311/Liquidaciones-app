package com.example.demo.dto;

import com.example.demo.model.enums.EstadoConductor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConductorDTO {
    private Long id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private LocalDate fechaContratacion;
    private BigDecimal porcentajeFlete;
    private EstadoConductor estado;
}
