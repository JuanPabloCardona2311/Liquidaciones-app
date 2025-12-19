-- Esquema inicial para control de camiones y viajes

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL,
    nombre_completo VARCHAR(160),
    rol VARCHAR(20) NOT NULL,
    activo BIT NOT NULL DEFAULT b'1',
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE camiones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(20) NOT NULL UNIQUE,
    marca VARCHAR(80) NOT NULL,
    color VARCHAR(40),
    modelo VARCHAR(80),
    anio INT,
    estado VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE conductores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    telefono VARCHAR(30),
    email VARCHAR(120),
    fecha_contratacion DATE NOT NULL,
    porcentaje_flete DECIMAL(5,2) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE asignaciones_camion_conductor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    camion_id BIGINT NOT NULL,
    conductor_id BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    estado VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    CONSTRAINT fk_asignacion_camion FOREIGN KEY (camion_id) REFERENCES camiones (id),
    CONSTRAINT fk_asignacion_conductor FOREIGN KEY (conductor_id) REFERENCES conductores (id)
) ENGINE=InnoDB;
CREATE INDEX idx_asignacion_camion ON asignaciones_camion_conductor(camion_id);
CREATE INDEX idx_asignacion_conductor ON asignaciones_camion_conductor(conductor_id);

CREATE TABLE viajes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    camion_id BIGINT NOT NULL,
    conductor_id BIGINT NOT NULL,
    fecha_viaje DATE NOT NULL,
    origen VARCHAR(120) NOT NULL,
    destino VARCHAR(120) NOT NULL,
    empresa_cliente VARCHAR(160),
    producto VARCHAR(160),
    peso DECIMAL(15,3),
    numero_remision VARCHAR(60),
    manifiesto VARCHAR(60),
    valor_flete DECIMAL(15,2) NOT NULL,
    anticipo DECIMAL(15,2) DEFAULT 0,
    porcentaje_conductor DECIMAL(5,2) NOT NULL,
    valor_acpm DECIMAL(15,2),
    valor_peajes DECIMAL(15,2),
    valor_cargue DECIMAL(15,2),
    valor_descargue DECIMAL(15,2),
    valor_parqueo DECIMAL(15,2),
    transporte_conductor DECIMAL(15,2),
    valor_montaje_llantas DECIMAL(15,2),
    valor_cambio_aceite DECIMAL(15,2),
    valor_lavada DECIMAL(15,2),
    valor_engrase DECIMAL(15,2),
    valor_tension_frenos DECIMAL(15,2),
    otros_gastos DECIMAL(15,2),
    descripcion_otros VARCHAR(255),
    estado VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    CONSTRAINT fk_viaje_camion FOREIGN KEY (camion_id) REFERENCES camiones (id),
    CONSTRAINT fk_viaje_conductor FOREIGN KEY (conductor_id) REFERENCES conductores (id)
) ENGINE=InnoDB;
CREATE INDEX idx_viaje_camion ON viajes(camion_id);
CREATE INDEX idx_viaje_conductor ON viajes(conductor_id);
