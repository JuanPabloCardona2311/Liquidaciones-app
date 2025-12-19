package com.example.demo.service;

import com.example.demo.dto.ViajeDTO;
import com.example.demo.model.Camion;
import com.example.demo.model.Conductor;
import com.example.demo.model.Viaje;
import com.example.demo.repository.CamionRepository;
import com.example.demo.repository.ConductorRepository;
import com.example.demo.repository.ViajeRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final CamionRepository camionRepository;
    private final ConductorRepository conductorRepository;

    public ViajeService(
            ViajeRepository viajeRepository,
            CamionRepository camionRepository,
            ConductorRepository conductorRepository) {
        this.viajeRepository = viajeRepository;
        this.camionRepository = camionRepository;
        this.conductorRepository = conductorRepository;
    }

    public ViajeDTO crear(ViajeDTO dto) {
        Camion camion = camionRepository.findById(dto.getCamionId())
                .orElseThrow(() -> new RuntimeException("Camión no encontrado"));
        Conductor conductor = conductorRepository.findById(dto.getConductorId())
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        Viaje viaje = new Viaje();
        viaje.setCamion(camion);
        viaje.setConductor(conductor);
        viaje.setFechaViaje(dto.getFechaViaje());
        viaje.setOrigen(dto.getOrigen());
        viaje.setDestino(dto.getDestino());
        viaje.setEmpresaCliente(dto.getEmpresaCliente());
        viaje.setProducto(dto.getProducto());
        viaje.setPeso(dto.getPeso());
        viaje.setNumeroRemision(dto.getNumeroRemision());
        viaje.setManifiesto(dto.getManifiesto());
        viaje.setValorFlete(dto.getValorFlete());
        viaje.setAnticipo(dto.getAnticipo());
        // Auto-asignar el porcentaje del conductor desde su perfil
        viaje.setPorcentajeConductor(conductor.getPorcentajeFlete());
        viaje.setValorAcpm(dto.getValorAcpm());
        viaje.setValorPeajes(dto.getValorPeajes());
        viaje.setValorCargue(dto.getValorCargue());
        viaje.setValorDescargue(dto.getValorDescargue());
        viaje.setValorParqueo(dto.getValorParqueo());
        viaje.setTransporteConductor(dto.getTransporteConductor());
        viaje.setValorMontajeLlantas(dto.getValorMontajeLlantas());
        viaje.setValorCambioAceite(dto.getValorCambioAceite());
        viaje.setValorLavada(dto.getValorLavada());
        viaje.setValorEngrase(dto.getValorEngrase());
        viaje.setValorTensionFrenos(dto.getValorTensionFrenos());
        viaje.setOtrosGastos(dto.getOtrosGastos());
        viaje.setDescripcionOtros(dto.getDescripcionOtros());
        viaje.setEstado(dto.getEstado());

        Viaje guardado = viajeRepository.save(viaje);
        return convertirADTO(guardado);
    }

    public List<ViajeDTO> listarTodos() {
        return viajeRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ViajeDTO obtenerPorId(Long id) {
        return viajeRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    public ViajeDTO actualizar(Long id, ViajeDTO dto) {
        Viaje viaje = viajeRepository.findById(id).orElse(null);
        if (viaje == null) return null;

        // Solo actualizar los campos del viaje, sin cambiar camion/conductor
        viaje.setFechaViaje(dto.getFechaViaje());
        viaje.setOrigen(dto.getOrigen());
        viaje.setDestino(dto.getDestino());
        viaje.setEmpresaCliente(dto.getEmpresaCliente());
        viaje.setProducto(dto.getProducto());
        viaje.setPeso(dto.getPeso());
        viaje.setNumeroRemision(dto.getNumeroRemision());
        viaje.setManifiesto(dto.getManifiesto());
        viaje.setValorFlete(dto.getValorFlete());
        viaje.setAnticipo(dto.getAnticipo());
        if (dto.getPorcentajeConductor() != null) {
            viaje.setPorcentajeConductor(dto.getPorcentajeConductor());
        }
        viaje.setValorAcpm(dto.getValorAcpm());
        viaje.setValorPeajes(dto.getValorPeajes());
        viaje.setValorCargue(dto.getValorCargue());
        viaje.setValorDescargue(dto.getValorDescargue());
        viaje.setValorParqueo(dto.getValorParqueo());
        viaje.setTransporteConductor(dto.getTransporteConductor());
        viaje.setValorMontajeLlantas(dto.getValorMontajeLlantas());
        viaje.setValorCambioAceite(dto.getValorCambioAceite());
        viaje.setValorLavada(dto.getValorLavada());
        viaje.setValorEngrase(dto.getValorEngrase());
        viaje.setValorTensionFrenos(dto.getValorTensionFrenos());
        viaje.setOtrosGastos(dto.getOtrosGastos());
        viaje.setDescripcionOtros(dto.getDescripcionOtros());
        viaje.setEstado(dto.getEstado());

        return convertirADTO(viajeRepository.save(viaje));
    }

    public void eliminar(Long id) {
        viajeRepository.deleteById(id);
    }

    public List<ViajeDTO> viajesPorConductorEnMes(Long conductorId, int mes, int anio) {
        LocalDate inicio = LocalDate.of(anio, mes, 1);
        LocalDate fin = LocalDate.of(anio, mes, 28);
        if (mes == 2) fin = LocalDate.of(anio, mes, 28);
        else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) fin = LocalDate.of(anio, mes, 30);
        else fin = LocalDate.of(anio, mes, 31);

        Conductor conductor = conductorRepository.findById(conductorId).orElse(null);
        if (conductor == null) return List.of();

        return viajeRepository.findByConductorAndFechaViajeBetween(conductor, inicio, fin)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private ViajeDTO convertirADTO(Viaje viaje) {
        // Calcular pago del conductor: porcentaje sobre el flete
        java.math.BigDecimal valorFlete = viaje.getValorFlete() != null ? viaje.getValorFlete() : java.math.BigDecimal.ZERO;
        java.math.BigDecimal porcentaje = viaje.getPorcentajeConductor() != null ? viaje.getPorcentajeConductor() : java.math.BigDecimal.ZERO;
        java.math.BigDecimal pagoConductor = valorFlete.multiply(porcentaje).divide(java.math.BigDecimal.valueOf(100));

        // Calcular gastos totales
        java.math.BigDecimal totalGastos = java.util.stream.Stream.of(
            viaje.getValorAcpm(),
            viaje.getValorPeajes(),
            viaje.getValorCargue(),
            viaje.getValorDescargue(),
            viaje.getValorParqueo(),
            viaje.getTransporteConductor(),
            viaje.getValorMontajeLlantas(),
            viaje.getValorCambioAceite(),
            viaje.getValorLavada(),
            viaje.getValorEngrase(),
            viaje.getValorTensionFrenos(),
            viaje.getOtrosGastos()
        ).filter(java.util.Objects::nonNull)
         .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        ViajeDTO dto = new ViajeDTO(
            viaje.getId(),
            viaje.getCamion().getId(),
            viaje.getConductor().getId(),
            viaje.getCamion().getPlaca(),
            viaje.getConductor().getNombre(),
            viaje.getFechaViaje(),
            viaje.getOrigen(),
            viaje.getDestino(),
            viaje.getEmpresaCliente(),
            viaje.getProducto(),
            viaje.getPeso(),
            viaje.getNumeroRemision(),
            viaje.getManifiesto(),
            viaje.getValorFlete(),
            viaje.getAnticipo(),
            viaje.getPorcentajeConductor(),
            viaje.getValorAcpm(),
            viaje.getValorPeajes(),
            viaje.getValorCargue(),
            viaje.getValorDescargue(),
            viaje.getValorParqueo(),
            viaje.getTransporteConductor(),
            viaje.getValorMontajeLlantas(),
            viaje.getValorCambioAceite(),
            viaje.getValorLavada(),
            viaje.getValorEngrase(),
            viaje.getValorTensionFrenos(),
            viaje.getOtrosGastos(),
            viaje.getDescripcionOtros(),
            viaje.getEstado(),
            pagoConductor,
            totalGastos
        );
        return dto;
    }
}
