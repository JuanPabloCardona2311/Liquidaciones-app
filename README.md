# Liquidaciones App

<div align="center">

**Sistema web para liquidaciones de transporte**

Una aplicación pensada para administrar flota, conductores, viajes y pagos mensuales desde una interfaz web clara y funcional.

</div>

<p align="center">
	<img src="https://img.shields.io/badge/Java-17-blue.svg" alt="Java 17">
	<img src="https://img.shields.io/badge/Spring%20Boot-4-green.svg" alt="Spring Boot 4">
	<img src="https://img.shields.io/badge/MySQL-Database-orange.svg" alt="MySQL">
	<img src="https://img.shields.io/badge/Thymeleaf-UI-brown.svg" alt="Thymeleaf">
</p>

> Administra camiones, conductores, asignaciones, viajes y liquidaciones mensuales con autenticación JWT.

## Vista rápida

- Gestiona el catálogo de camiones y conductores.
- Registra asignaciones entre camión y conductor.
- Registra viajes con datos operativos y gastos detallados.
- Calcula liquidaciones mensuales por conductor o para todos los conductores.
- Protege el acceso con autenticación basada en JWT.
- Incluye dashboard y estadísticas para seguimiento operativo.

## Captura conceptual

```text
┌──────────────────────────────────────────────┐
│  Liquidaciones App                           │
├───────────────┬──────────────────────────────┤
│ Menú lateral  │  Dashboard / Tablas / Forms  │
│ - Dashboard   │  - Camiones                  │
│ - Camiones    │  - Conductores               │
│ - Conductores │  - Viajes                    │
│ - Viajes      │  - Liquidaciones             │
│ - Liquidaciones│  - Estadísticas             │
└───────────────┴──────────────────────────────┘
```

## Tecnologías

- Java 17
- Spring Boot 4
- Spring MVC
- Spring Data JPA
- Spring Security
- Thymeleaf
- JWT
- MySQL
- Maven

## Estructura general

```text
src/main/java/com/example/demo/
├── config/            Configuración general, seguridad y carga inicial de datos
├── controller/        Controladores REST y controladores de vistas
├── dto/               Objetos de transferencia de datos
├── model/             Entidades JPA y enums
├── repository/        Acceso a datos
├── security/          JWT y validación de peticiones
└── service/           Lógica de negocio

src/main/resources/
├── application.properties
├── application-local.properties
├── db/migration/
└── templates/
```

## Requisitos

- Java 17 o superior
- Maven 3.8 o superior, o usar el wrapper incluido (`mvnw` / `mvnw.cmd`)
- MySQL 8 o una instancia compatible

## Cómo ejecutar la aplicación

### Opción 1: con Maven Wrapper

En Windows:

```bash
mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

### Opción 2: usando tu IDE

Ejecuta la clase principal `DemoApplication.java` desde IntelliJ, Eclipse o VS Code.

## Acceso local

- Aplicación principal: `http://localhost:8080/`
- Perfil local opcional: `http://localhost:8081/`

Para activar el perfil local:

```bash
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```

## Credenciales iniciales

La aplicación crea un usuario administrador inicial para facilitar la primera entrada al sistema.

- Usuario: `admin`
- Contraseña: `admin123`

Usa estas credenciales solo para pruebas o entorno local y cámbialas en un entorno real.

## Funcionalidades principales

### 1. Autenticación

- Inicio de sesión con JWT.
- Validación de token para proteger las rutas API.
- Redirección automática al login si no existe sesión.

### 2. Gestión de camiones

- Crear, listar, editar y eliminar camiones.
- Estados de operación para seguimiento de la flota.

### 3. Gestión de conductores

- CRUD completo de conductores.
- Datos de contacto, porcentaje de flete y estado.

### 4. Asignaciones

- Relaciona temporalmente un camión con un conductor.
- Permite mantener historial de asignaciones.

### 5. Viajes

- Registro detallado de cada viaje.
- Incluye origen, destino, empresa cliente, producto, remisión, valor del flete y gastos.

### 6. Liquidaciones

- Consulta liquidaciones por conductor y por periodo.
- Genera resúmenes mensuales con total de viajes, flete, gastos y valor neto a pagar.

### 7. Estadísticas y panel

- Dashboard para navegación rápida.
- Vista de estadísticas para apoyo operativo.

## Flujo de uso

1. Abre la aplicación desde el navegador.
2. Inicia sesión con el usuario administrador.
3. Registra camiones y conductores.
4. Crea asignaciones y viajes.
5. Genera liquidaciones por mes y conductor.
6. Revisa el dashboard y las estadísticas.

## Vistas web disponibles

- `/login`
- `/dashboard`
- `/camiones`
- `/conductores`
- `/viajes`
- `/liquidaciones`
- `/estadisticas`

## Endpoints principales

### Autenticación

- `POST /api/auth/login` para iniciar sesión.
- `GET /api/auth/validate` para validar token.

### Camiones

- `GET /api/camiones`
- `GET /api/camiones/{id}`
- `POST /api/camiones`
- `PUT /api/camiones/{id}`
- `DELETE /api/camiones/{id}`

### Conductores

- `GET /api/conductores`
- `GET /api/conductores/{id}`
- `POST /api/conductores`
- `PUT /api/conductores/{id}`
- `DELETE /api/conductores/{id}`

### Asignaciones

- `GET /api/asignaciones`
- `GET /api/asignaciones/{id}`
- `POST /api/asignaciones`
- `PUT /api/asignaciones/{id}`
- `DELETE /api/asignaciones/{id}`

### Viajes

- `GET /api/viajes`
- `GET /api/viajes/{id}`
- `POST /api/viajes`
- `PUT /api/viajes/{id}`
- `DELETE /api/viajes/{id}`
- `GET /api/viajes/conductor/{conductorId}/mes/{mes}/anio/{anio}`

### Liquidaciones

- `GET /api/liquidaciones/{conductorId}/mes/{mes}/anio/{anio}`
- `GET /api/liquidaciones/mes/{mes}/anio/{anio}`

### Estadísticas

- `GET /api/estadisticas/camiones`

## Base de datos

La aplicación trabaja con una base MySQL y utiliza JPA para persistencia.

Tablas principales:

- `camion`
- `conductor`
- `asignacion_camion_conductor`
- `viaje`
- `usuario`

El proyecto también incluye migraciones SQL iniciales en `src/main/resources/db/migration/`.

## Configuración

Los parámetros principales se definen en `src/main/resources/application.properties`.

Debes revisar y ajustar:

- URL de la base de datos.
- Usuario y contraseña de MySQL.
- Clave secreta JWT.
- Tiempo de expiración del token.

Para desarrollo local también puedes usar `application-local.properties`, que actualmente solo cambia el puerto.

## Notas importantes

- La aplicación usa JPA con creación y actualización automática de esquema.
- El proyecto incluye migraciones SQL iniciales en `src/main/resources/db/migration/`.
- Si vas a publicarlo en GitHub o moverlo a otro entorno, evita dejar credenciales reales en el repositorio.

## Documentación adicional

- `README_APP.md` contiene una explicación más extensa de las funcionalidades.
- `RESUMEN_FINAL.md` resume la implementación completa del proyecto.

## Sugerencia para GitHub

Cuando publiques este archivo en GitHub, el README se verá automáticamente en la portada del repositorio. No necesitas hacer nada más aparte de subir los cambios al remoto.

## Estado del proyecto

Aplicación funcional orientada a administración de liquidaciones de transporte.