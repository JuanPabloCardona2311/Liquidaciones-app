package com.example.demo.service;

import com.example.demo.dto.EstadisticaCamionMensualDTO;
import com.example.demo.dto.LiquidacionMensualDTO;
import com.example.demo.model.Camion;
import com.example.demo.model.Conductor;
import com.example.demo.model.Viaje;
import com.example.demo.repository.CamionRepository;
import com.example.demo.repository.ConductorRepository;
import com.example.demo.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class LiquidacionService {

    private final ConductorRepository conductorRepository;
    private final CamionRepository camionRepository;
    private final ViajeRepository viajeRepository;

    public LiquidacionService(ConductorRepository conductorRepository,
                              CamionRepository camionRepository,
                              ViajeRepository viajeRepository) {
        this.conductorRepository = conductorRepository;
        this.camionRepository = camionRepository;
        this.viajeRepository = viajeRepository;
    }

    public LiquidacionMensualDTO generarLiquidacionConductor(Long conductorId, int mes, int anio) {
        Conductor conductor = conductorRepository.findById(conductorId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        // Calcular rango de fechas del mes
        LocalDate inicio = LocalDate.of(anio, mes, 1);
        LocalDate fin = obtenerUltimoDiaDelMes(anio, mes);

        // Obtener viajes del conductor en el mes
        List<Viaje> viajes = viajeRepository.findByConductorAndFechaViajeBetween(conductor, inicio, fin);

        // Cálculos
        int totalViajes = viajes.size();
        BigDecimal totalFleteGenerado = BigDecimal.ZERO;
        BigDecimal totalGastosViajes = BigDecimal.ZERO;
        BigDecimal gananciasConductor = BigDecimal.ZERO;
        BigDecimal totalAnticipos = BigDecimal.ZERO;

        for (Viaje viaje : viajes) {
            // Sumar fletes
            if (viaje.getValorFlete() != null) {
                totalFleteGenerado = totalFleteGenerado.add(viaje.getValorFlete());
            }

            // Sumar anticipos
            if (viaje.getAnticipo() != null) {
                totalAnticipos = totalAnticipos.add(viaje.getAnticipo());
            }

            // Calcular ganancia del conductor para este viaje
            BigDecimal gananciaViaje = viaje.pagoConductor(); // Usa el método @Transient de Viaje
            gananciasConductor = gananciasConductor.add(gananciaViaje);

            // Sumar gastos del viaje
            BigDecimal gastosViaje = viaje.totalGastos(); // Usa el método @Transient de Viaje
            totalGastosViajes = totalGastosViajes.add(gastosViaje);
        }

        // Neto = ganancias del conductor - gastos deducibles (si aplica)
        // En este caso, los gastos de mantenimiento (llantas, aceite) son de la empresa, no se deducen
        BigDecimal neto = gananciasConductor;

        return new LiquidacionMensualDTO(
                conductorId,
                conductor.getNombre(),
                conductor.getApellido(),
                mes,
                anio,
                totalViajes,
                totalFleteGenerado,
                conductor.getPorcentajeFlete(),
                gananciasConductor,
                totalGastosViajes,
                totalAnticipos,
                neto
        );
    }

    public List<LiquidacionMensualDTO> generarLiquidacionesTodosConductores(int mes, int anio) {
        List<Conductor> conductores = conductorRepository.findAll();
        List<LiquidacionMensualDTO> liquidaciones = new ArrayList<>();

        for (Conductor conductor : conductores) {
            LiquidacionMensualDTO liquidacion = generarLiquidacionConductor(conductor.getId(), mes, anio);
            liquidaciones.add(liquidacion);
        }

        return liquidaciones;
    }

    public List<EstadisticaCamionMensualDTO> generarEstadisticasCamiones(int anio) {
        List<Camion> camiones = camionRepository.findAll();
        List<EstadisticaCamionMensualDTO> resultado = new ArrayList<>();

        for (Camion camion : camiones) {
            for (int mes = 1; mes <= 12; mes++) {
                YearMonth ym = YearMonth.of(anio, mes);
                LocalDate inicio = ym.atDay(1);
                LocalDate fin = ym.atEndOfMonth();

                List<Viaje> viajes = viajeRepository.findByCamionAndFechaViajeBetween(camion, inicio, fin);

                BigDecimal totalFlete = BigDecimal.ZERO;
                BigDecimal totalGastos = BigDecimal.ZERO;
                BigDecimal totalPagoConductor = BigDecimal.ZERO;

                for (Viaje viaje : viajes) {
                    if (viaje.getValorFlete() != null) {
                        totalFlete = totalFlete.add(viaje.getValorFlete());
                    }

                    BigDecimal gastosViaje = viaje.totalGastos();
                    totalGastos = totalGastos.add(gastosViaje);

                    BigDecimal pagoCond = viaje.pagoConductor();
                    totalPagoConductor = totalPagoConductor.add(pagoCond);
                }

                BigDecimal utilidadNeta = totalFlete.subtract(totalGastos).subtract(totalPagoConductor);

                resultado.add(new EstadisticaCamionMensualDTO(
                        camion.getId(),
                        camion.getPlaca(),
                        mes,
                        anio,
                        totalFlete,
                        totalGastos,
                        totalPagoConductor,
                        utilidadNeta
                ));
            }
        }

        return resultado;
    }

    private LocalDate obtenerUltimoDiaDelMes(int anio, int mes) {
        if (mes == 2) {
            return LocalDate.of(anio, mes, 28);
        } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            return LocalDate.of(anio, mes, 30);
        } else {
            return LocalDate.of(anio, mes, 31);
        }
    }
}
