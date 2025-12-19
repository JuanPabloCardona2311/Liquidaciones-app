package com.example.demo.model;

import com.example.demo.model.enums.EstadoViaje;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "viajes")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camion_id", nullable = false)
    private Camion camion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conductor_id", nullable = false)
    private Conductor conductor;

    @NotNull
    @Column(name = "fecha_viaje", nullable = false)
    private LocalDate fechaViaje;

    @NotBlank
    @Size(max = 120)
    @Column(name = "origen", nullable = false, length = 120)
    private String origen;

    @NotBlank
    @Size(max = 120)
    @Column(name = "destino", nullable = false, length = 120)
    private String destino;

    @Size(max = 160)
    @Column(name = "empresa_cliente", length = 160)
    private String empresaCliente;

    @Size(max = 160)
    @Column(name = "producto", length = 160)
    private String producto;

    @Column(name = "peso", precision = 15, scale = 3)
    private BigDecimal peso;

    @Size(max = 60)
    @Column(name = "numero_remision", length = 60)
    private String numeroRemision;

    @Size(max = 60)
    @Column(name = "manifiesto", length = 60)
    private String manifiesto;

    @NotNull
    @DecimalMin("0")
    @Column(name = "valor_flete", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorFlete;

    @DecimalMin("0")
    @Column(name = "anticipo", precision = 15, scale = 2)
    private BigDecimal anticipo;

    @NotNull
    @DecimalMin("0")
    @Column(name = "porcentaje_conductor", nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentajeConductor;

    @DecimalMin("0")
    @Column(name = "valor_acpm", precision = 15, scale = 2)
    private BigDecimal valorAcpm;

    @DecimalMin("0")
    @Column(name = "valor_peajes", precision = 15, scale = 2)
    private BigDecimal valorPeajes;

    @DecimalMin("0")
    @Column(name = "valor_cargue", precision = 15, scale = 2)
    private BigDecimal valorCargue;

    @DecimalMin("0")
    @Column(name = "valor_descargue", precision = 15, scale = 2)
    private BigDecimal valorDescargue;

    @DecimalMin("0")
    @Column(name = "valor_parqueo", precision = 15, scale = 2)
    private BigDecimal valorParqueo;

    @DecimalMin("0")
    @Column(name = "transporte_conductor", precision = 15, scale = 2)
    private BigDecimal transporteConductor;

    @DecimalMin("0")
    @Column(name = "valor_montaje_llantas", precision = 15, scale = 2)
    private BigDecimal valorMontajeLlantas;

    @DecimalMin("0")
    @Column(name = "valor_cambio_aceite", precision = 15, scale = 2)
    private BigDecimal valorCambioAceite;

    @DecimalMin("0")
    @Column(name = "valor_lavada", precision = 15, scale = 2)
    private BigDecimal valorLavada;

    @DecimalMin("0")
    @Column(name = "valor_engrase", precision = 15, scale = 2)
    private BigDecimal valorEngrase;

    @DecimalMin("0")
    @Column(name = "valor_tension_frenos", precision = 15, scale = 2)
    private BigDecimal valorTensionFrenos;

    @DecimalMin("0")
    @Column(name = "otros_gastos", precision = 15, scale = 2)
    private BigDecimal otrosGastos;

    @Size(max = 255)
    @Column(name = "descripcion_otros", length = 255)
    private String descripcionOtros;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoViaje estado;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        if (this.estado == null) {
            this.estado = EstadoViaje.PENDIENTE;
        }
        if (this.anticipo == null) {
            this.anticipo = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Transient
    public BigDecimal totalGastos() {
        return sumNonNull(
                valorAcpm,
                valorPeajes,
                valorCargue,
                valorDescargue,
                valorParqueo,
                transporteConductor,
                valorMontajeLlantas,
                valorCambioAceite,
                valorLavada,
                valorEngrase,
                valorTensionFrenos,
                otrosGastos
        );
    }

    @Transient
    public BigDecimal pagoConductor() {
        if (valorFlete == null || porcentajeConductor == null) {
            return BigDecimal.ZERO;
        }
        return valorFlete
                .multiply(porcentajeConductor)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal sumNonNull(BigDecimal... values) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            if (value != null) {
                total = total.add(value);
            }
        }
        return total;
    }

    public Long getId() {
        return id;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(LocalDate fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getEmpresaCliente() {
        return empresaCliente;
    }

    public void setEmpresaCliente(String empresaCliente) {
        this.empresaCliente = empresaCliente;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getNumeroRemision() {
        return numeroRemision;
    }

    public void setNumeroRemision(String numeroRemision) {
        this.numeroRemision = numeroRemision;
    }

    public String getManifiesto() {
        return manifiesto;
    }

    public void setManifiesto(String manifiesto) {
        this.manifiesto = manifiesto;
    }

    public BigDecimal getValorFlete() {
        return valorFlete;
    }

    public void setValorFlete(BigDecimal valorFlete) {
        this.valorFlete = valorFlete;
    }

    public BigDecimal getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(BigDecimal anticipo) {
        this.anticipo = anticipo;
    }

    public BigDecimal getPorcentajeConductor() {
        return porcentajeConductor;
    }

    public void setPorcentajeConductor(BigDecimal porcentajeConductor) {
        this.porcentajeConductor = porcentajeConductor;
    }

    public BigDecimal getValorAcpm() {
        return valorAcpm;
    }

    public void setValorAcpm(BigDecimal valorAcpm) {
        this.valorAcpm = valorAcpm;
    }

    public BigDecimal getValorPeajes() {
        return valorPeajes;
    }

    public void setValorPeajes(BigDecimal valorPeajes) {
        this.valorPeajes = valorPeajes;
    }

    public BigDecimal getValorCargue() {
        return valorCargue;
    }

    public void setValorCargue(BigDecimal valorCargue) {
        this.valorCargue = valorCargue;
    }

    public BigDecimal getValorDescargue() {
        return valorDescargue;
    }

    public void setValorDescargue(BigDecimal valorDescargue) {
        this.valorDescargue = valorDescargue;
    }

    public BigDecimal getValorParqueo() {
        return valorParqueo;
    }

    public void setValorParqueo(BigDecimal valorParqueo) {
        this.valorParqueo = valorParqueo;
    }

    public BigDecimal getTransporteConductor() {
        return transporteConductor;
    }

    public void setTransporteConductor(BigDecimal transporteConductor) {
        this.transporteConductor = transporteConductor;
    }

    public BigDecimal getValorMontajeLlantas() {
        return valorMontajeLlantas;
    }

    public void setValorMontajeLlantas(BigDecimal valorMontajeLlantas) {
        this.valorMontajeLlantas = valorMontajeLlantas;
    }

    public BigDecimal getValorCambioAceite() {
        return valorCambioAceite;
    }

    public void setValorCambioAceite(BigDecimal valorCambioAceite) {
        this.valorCambioAceite = valorCambioAceite;
    }

    public BigDecimal getValorLavada() {
        return valorLavada;
    }

    public void setValorLavada(BigDecimal valorLavada) {
        this.valorLavada = valorLavada;
    }

    public BigDecimal getValorEngrase() {
        return valorEngrase;
    }

    public void setValorEngrase(BigDecimal valorEngrase) {
        this.valorEngrase = valorEngrase;
    }

    public BigDecimal getValorTensionFrenos() {
        return valorTensionFrenos;
    }

    public void setValorTensionFrenos(BigDecimal valorTensionFrenos) {
        this.valorTensionFrenos = valorTensionFrenos;
    }

    public BigDecimal getOtrosGastos() {
        return otrosGastos;
    }

    public void setOtrosGastos(BigDecimal otrosGastos) {
        this.otrosGastos = otrosGastos;
    }

    public String getDescripcionOtros() {
        return descripcionOtros;
    }

    public void setDescripcionOtros(String descripcionOtros) {
        this.descripcionOtros = descripcionOtros;
    }

    public EstadoViaje getEstado() {
        return estado;
    }

    public void setEstado(EstadoViaje estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
