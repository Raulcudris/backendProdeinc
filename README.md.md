# 03. Microservicios

## 1. Descripción general

El sistema se desarrollará bajo una arquitectura basada en microservicios. Cada microservicio será responsable de un dominio específico del negocio.

La primera versión del sistema se enfocará en un MVP compuesto por microservicios esenciales para el control operativo de obras civiles.

---

## 2. Microservicios del MVP

```text
msvc-config
msvc-eureka
msvc-gateway
msvc-control-obras
msvc-equipos-maquinaria
msvc-documentos-obra
msvc-evidencias
```

---

## 3. Microservicios de infraestructura

### `msvc-config`

Responsabilidad:

- Centralizar configuración.
- Leer archivos desde `config-data`.
- Exponer configuración por nombre de microservicio.

Puerto sugerido:

```text
8089
```

### `msvc-eureka`

Responsabilidad:

- Registrar microservicios.
- Permitir descubrimiento de servicios.
- Facilitar comunicación por nombre lógico.

Puerto sugerido:

```text
8761
```

### `msvc-gateway`

Responsabilidad:

- Ser el punto único de entrada.
- Enrutar peticiones.
- Gestionar CORS.
- Aplicar filtros de autenticación.
- Conectarse con Eureka.

Puerto sugerido:

```text
8088
```

Rutas iniciales:

```text
/api/control-obras/**
/api/equipos-maquinaria/**
/api/documentos-obra/**
/api/evidencias/**
```

---

## 4. Microservicios funcionales

### `msvc-control-obras`

Microservicio principal del sistema.

Responsabilidades:

- Gestionar órdenes de servicio.
- Gestionar sitios de trabajo.
- Crear planes de trabajo.
- Crear planes semanales.
- Registrar reportes diarios.
- Registrar novedades.
- Controlar avance planeado vs. ejecutado.

Entidades iniciales:

```text
OrdenServicio
SitioTrabajo
PlanTrabajo
PlanSemanal
ReporteDiario
NovedadObra
```

Puerto sugerido:

```text
6091
```

### `msvc-equipos-maquinaria`

Responsabilidades:

- Gestionar tipos de equipos.
- Gestionar inventario de equipos.
- Registrar maquinaria.
- Administrar asignaciones.
- Controlar disponibilidad.
- Controlar estado operativo.

Entidades iniciales:

```text
TipoEquipo
Equipo
AsignacionEquipo
EstadoEquipo
```

Puerto sugerido:

```text
6092
```

### `msvc-documentos-obra`

Responsabilidades:

- Gestionar documentos legales.
- Gestionar documentos técnicos.
- Clasificar documentos.
- Registrar fechas de expedición.
- Registrar fechas de vencimiento.
- Consultar documentos vencidos.
- Consultar documentos próximos a vencer.

Entidades iniciales:

```text
CategoriaDocumento
TipoDocumento
DocumentoObra
VencimientoDocumento
```

Puerto sugerido:

```text
6093
```

### `msvc-evidencias`

Responsabilidades:

- Registrar fotos.
- Registrar videos.
- Registrar archivos.
- Asociar evidencias a reportes diarios.
- Asociar evidencias a novedades.
- Guardar URLs de almacenamiento.
- Registrar metadatos de evidencia.

Entidades iniciales:

```text
TipoEvidencia
Evidencia
ReferenciaEvidencia
```

Puerto sugerido:

```text
6094
```

---

## 5. Microservicios futuros

```text
msvc-proveedores
msvc-reportes-obra
msvc-catalogos-obra
msvc-usuarios-obra
msvc-notificaciones-obra
```

### `msvc-proveedores`

Gestionará proveedores de materiales, equipos, maquinaria y servicios.

### `msvc-reportes-obra`

Gestionará reportes consolidados e indicadores de avance.

### `msvc-catalogos-obra`

Gestionará catálogos generales del sistema.

### `msvc-usuarios-obra`

Gestionará usuarios, roles, permisos y accesos.

### `msvc-notificaciones-obra`

Gestionará alertas y notificaciones del sistema.

---

## 6. Estructura recomendada por microservicio

```text
src/main/java/com/system/modules/nombremodulo
├── api
├── dto
├── entity
├── repository
├── mapper
└── usecase
```

---

## 7. Orden recomendado de construcción

```text
1. msvc-eureka
2. msvc-config
3. msvc-gateway
4. msvc-control-obras
5. msvc-equipos-maquinaria
6. msvc-documentos-obra
7. msvc-evidencias
```

El primer microservicio funcional a construir debe ser `msvc-control-obras`, porque representa el núcleo del sistema.
