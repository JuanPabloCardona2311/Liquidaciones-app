# 🚚 Sistema de Liquidaciones - Aplicación Web

## 📋 Descripción

Sistema integral de gestión para empresas de transporte que permite:
- ✅ Administrar una flota de camiones
- ✅ Gestionar conductores y sus perfiles
- ✅ Registrar viajes detallados con costos
- ✅ Generar reportes mensuales de liquidación y pago a conductores
- ✅ Autenticación segura con JWT

---

## 🛠️ Tecnologías Utilizadas

- **Backend:** Spring Boot 4.0.0 con Java 17
- **Base de Datos:** MySQL 8.0.43 en AWS RDS
- **Autenticación:** JWT + BCrypt
- **Frontend:** HTML5, CSS3, JavaScript vanilla
- **ORM:** Hibernate 7.1.8 con Spring Data JPA
- **Build:** Maven

---

## 📦 Instalación y Configuración

### Requisitos Previos

- Java 17 o superior
- Maven 3.8.9 o superior
- MySQL Workbench (opcional, para verificar BD)

### Pasos de Instalación

1. **Clonar o descargar el proyecto**
   ```bash
   cd "Liquidaciones App/demo"
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

   O ejecutar desde el IDE (Eclipse, IntelliJ, VS Code)

4. **Acceder a la aplicación**
   ```
   http://localhost:8080/
   ```

---

## 🔐 Credenciales Iniciales

**Usuario Admin Auto-Creado:**
- 👤 **Usuario:** `admin`
- 🔑 **Contraseña:** `admin123`

Estas credenciales se crean automáticamente la primera vez que ejecuta la aplicación.

---

## 🚀 Funcionalidades Principales

### 1. 🔓 Autenticación (Login)
- Acceso seguro con token JWT
- Los tokens expiran en 24 horas
- Redirección automática al login si no hay sesión

### 2. 📊 Dashboard
- Panel principal con acceso a todas las funcionalidades
- Información rápida del usuario logueado
- Fecha y hora actual

### 3. 🚛 Gestión de Camiones
- **Crear:** Registrar nuevos camiones
- **Leer:** Listar todos los camiones
- **Actualizar:** Editar información de camiones
- **Eliminar:** Dar de baja camiones
- **Estados:** ACTIVO, INACTIVO, MANTENIMIENTO

**Datos del Camión:**
- Placa (única)
- Marca, Modelo, Año
- Color
- Estado

### 4. 👥 Gestión de Conductores
- **Crear:** Registrar nuevos conductores
- **Leer:** Listar todos los conductores
- **Actualizar:** Editar información
- **Eliminar:** Dar de baja conductores
- **Estados:** ACTIVO, INACTIVO

**Datos del Conductor:**
- Cédula (única)
- Nombre, Apellido
- Email, Teléfono
- Porcentaje de Flete (0-100%)
- Fecha de Contratación

### 5. 🛣️ Registro de Viajes
- **Crear:** Registrar nuevos viajes con todos los detalles
- **Leer:** Listar viajes realizados
- **Eliminar:** Cancelar viajes

**Datos del Viaje:**
- Fecha, Camión, Conductor
- Origen, Destino
- Empresa Cliente
- Producto, Peso, Remisión
- Valor del Flete
- Anticipo
- **Gastos Detallados:**
  - ACPM, Peajes
  - Cargue, Descargue
  - Parqueo
  - Transporte Conductor
  - Montaje Llantas, Cambio Aceite
  - Lavada, Engrase, Tensión Frenos
  - Otros Gastos
- Estados: PENDIENTE, EN_CURSO, FINALIZADO, CANCELADO

### 6. 💰 Reporte de Liquidaciones Mensuales
- Generar reportes por mes y año
- Filtrar por conductor específico o ver todos
- **Información que muestra:**
  - Total de viajes realizados
  - Flete total generado
  - Porcentaje de ganancia del conductor
  - Ganancias del conductor (flete × porcentaje)
  - Total de gastos asociados
  - **Neto a pagar** (ganancias del conductor)

**Resumen Global:**
- Total de flete en el período
- Total a pagar a conductores
- Total de gastos

---

## 📱 Interfaz de Usuario

### Estructura General

```
┌─────────────────────────────────────────────────┐
│                    HEADER                        │
│  🚚 Liquidaciones | Usuario: Admin | Cerrar     │
├──────────────┬──────────────────────────────────┤
│              │                                   │
│  SIDEBAR     │         CONTENIDO PRINCIPAL      │
│  (Menú)      │                                   │
│              │  - Tablas de datos               │
│  • Dashboard │  - Modales para crear/editar     │
│  • Camiones  │  - Filtros y búsqueda            │
│  • Conductores│ - Resúmenes y estadísticas      │
│  • Viajes    │                                   │
│  • Liquidaciones
│              │                                   │
│  Cerrar      │                                   │
│  Sesión      │                                   │
└──────────────┴──────────────────────────────────┘
```

### Colores Principales
- Azul Morado: `#667eea` a `#764ba2` (Gradiente principal)
- Blanco: Fondos de contenido
- Verde: Montos de dinero y estados positivos
- Rojo: Acciones de eliminación
- Amarillo: Estados en progreso

---

## 🔌 API REST Endpoints

### Autenticación
- `POST /api/auth/login` - Obtener token JWT
- `GET /api/auth/validate` - Validar token

### Camiones
- `GET /api/camiones` - Listar todos
- `GET /api/camiones/{id}` - Obtener por ID
- `POST /api/camiones` - Crear
- `PUT /api/camiones/{id}` - Actualizar
- `DELETE /api/camiones/{id}` - Eliminar

### Conductores
- `GET /api/conductores` - Listar todos
- `GET /api/conductores/{id}` - Obtener por ID
- `POST /api/conductores` - Crear
- `PUT /api/conductores/{id}` - Actualizar
- `DELETE /api/conductores/{id}` - Eliminar

### Viajes
- `GET /api/viajes` - Listar todos
- `GET /api/viajes/{id}` - Obtener por ID
- `POST /api/viajes` - Crear
- `PUT /api/viajes/{id}` - Actualizar
- `DELETE /api/viajes/{id}` - Eliminar
- `GET /api/viajes/conductor/{conductorId}/mes/{mes}/anio/{anio}` - Viajes por mes

### Liquidaciones
- `GET /api/liquidaciones/{conductorId}/mes/{mes}/anio/{anio}` - Liquidación por conductor
- `GET /api/liquidaciones/mes/{mes}/anio/{anio}` - Liquidaciones de todos

---

## 🧪 Ejemplo de Uso

### 1. Login
```
Usuario: admin
Contraseña: admin123
→ Obtiene token JWT
→ Redirige al Dashboard
```

### 2. Crear un Camión
```
Ir a → Camiones
Click en "+ Nuevo Camión"
Completar formulario:
  - Placa: ABC-123
  - Marca: Volvo
  - Modelo: FH-16
  - Color: Blanco
  - Año: 2022
  - Estado: ACTIVO
Click en "Guardar"
```

### 3. Crear un Conductor
```
Ir a → Conductores
Click en "+ Nuevo Conductor"
Completar formulario:
  - Cédula: 1234567890
  - Nombre: Juan
  - Apellido: Pérez
  - Email: juan@example.com
  - Teléfono: +573001234567
  - % Flete: 30
  - Fecha Contratación: 2024-01-15
  - Estado: ACTIVO
Click en "Guardar"
```

### 4. Registrar un Viaje
```
Ir a → Viajes
Click en "+ Nuevo Viaje"
Seleccionar:
  - Fecha, Camión, Conductor
  - Origen: Bogotá, Destino: Cali
  - Empresa Cliente: Transportes XYZ
  - Producto: Textiles
Ingresar:
  - Valor Flete: $1,000,000
  - Gastos (ACPM: $150,000, Peajes: $50,000, etc.)
  - Estado: FINALIZADO
Click en "Guardar Viaje"
```

### 5. Generar Liquidación Mensual
```
Ir a → Liquidaciones
Seleccionar:
  - Mes: Diciembre
  - Año: 2024
  - Conductor: Juan Pérez (opcional)
Click en "🔍 Buscar"
Resultado:
  - Total Viajes: 1
  - Flete: $1,000,000
  - Ganancias Conductor (30%): $300,000
  - Gastos: $200,000
  - Neto a Pagar: $300,000
```

---

## 🔒 Seguridad

### Autenticación JWT
- Tokens válidos por 24 horas
- Claim "rol" para futuras autorizaciones
- Token requerido en header: `Authorization: Bearer {token}`

### Encriptación de Contraseñas
- Uso de BCryptPasswordEncoder
- Nivel de fuerza: 10

### Protección de Endpoints
- Interceptor JWT valida todas las peticiones a `/api/**`
- Login y Validate excluidos de validación
- Páginas HTML redirigen automáticamente si no hay sesión

---

## 📊 Estructura de la Base de Datos

### Tablas Principales
```
camion (id, placa*, marca, modelo, color, anio, estado, created_at, updated_at)
conductor (id, cedula*, nombre, apellido, email, telefono, 
           porcentaje_flete, fecha_contratacion, estado, created_at, updated_at)
asignacion_camion_conductor (id, camion_id, conductor_id, 
                              fecha_inicio, fecha_fin, estado, created_at, updated_at)
viaje (id, fecha_viaje, camion_id, conductor_id, origen, destino, producto, peso,
       remision, empresa_cliente, valor_flete, anticipo, porcentaje_conductor,
       acpm, peajes, cargue, descargue, parqueo, transporte_conductor,
       montaje_llantas, cambio_aceite, lavada, engrase, tension_frenos, otros_gastos,
       estado, created_at, updated_at)
usuario (id, username*, password, email, nombre_completo, rol, activo, created_at, updated_at)
```

*: Campos únicos

---

## 🐛 Solución de Problemas

### Error de conexión a BD
- Verificar credenciales en `application.properties`
- Confirmar que AWS RDS está disponible
- Verificar conexión a internet

### Token inválido o expirado
- Volver a hacer login
- El token se regenera automáticamente

### No aparecen cambios en tablas
- Recargar la página (F5)
- Verificar que la operación fue exitosa (mensaje verde)

### Puerto 8080 en uso
- Cambiar puerto en `application.properties`: `server.port=8081`

---

## 📞 Contacto y Soporte

Para reportar bugs o sugerencias, contactar al administrador del proyecto.

---

## 📝 Notas Importantes

1. **Datos de Prueba:** La aplicación crea automáticamente el usuario admin en el primer inicio
2. **Respaldo:** Se recomienda hacer backups regulares de la BD MySQL
3. **Expiración de Sesión:** Los usuarios deben re-autenticarse cada 24 horas
4. **Capacidad:** Optimizada para hasta 10,000 registros simultáneos

---

## ✅ Checklist de Funcionalidades Implementadas

- [x] Autenticación con JWT
- [x] Gestión de Camiones (CRUD)
- [x] Gestión de Conductores (CRUD)
- [x] Registro de Viajes (CRUD + cálculos)
- [x] Reporte de Liquidaciones Mensuales
- [x] Dashboard principal
- [x] Interfaz HTML responsiva
- [x] Base de datos en AWS RDS
- [x] Validaciones de datos
- [x] Manejo de errores

---

**Versión:** 1.0.0  
**Fecha:** Diciembre 2024  
**Estado:** ✅ Producción
