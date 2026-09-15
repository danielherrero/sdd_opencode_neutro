# Nombre: plan.md
# Feature: 002-DEPARTMENT-MANAGEMENT

## Descripcion
Plan tecnico para implementar el CRUD REST de departamentos siguiendo el stack de la feature `001-USER-MANAGEMENT`.

## Stack tecnologico
- Java OpenJDK 25.
- Quarkus `3.39.3` con `quarkus-rest-jackson`.
- Jakarta Bean Validation.
- Hibernate ORM with Panache.
- PostgreSQL en ejecucion real y H2 en `%test`.
- JUnit 5, Quarkus Test y RestAssured.

## Estructura
Se añadiran los paquetes:

```text
src/main/java/com/example/department/
├── controller/DepartmentResource.java
├── dto/CreateDepartmentRequest.java
├── dto/UpdateDepartmentRequest.java
├── dto/DepartmentResponse.java
├── entity/Department.java
├── repository/DepartmentRepository.java
└── service/DepartmentService.java
```

La entidad `Department` se mapeara a la tabla `departments`, con `id` generado por la base de datos, `nombre` no nulo, `descripcion` nullable y `fecha_creacion` no nula.

## Contrato REST
El recurso base sera `/departments`.

| Metodo | Ruta | Exito | Errores | Requisito |
|---|---|---|---|---|
| `POST` | `/departments` | `201 Created` | `400 Bad Request` | RF-001, RF-005, RF-007, RF-008 |
| `GET` | `/departments` | `200 OK` | - | RF-006 |
| `GET` | `/departments/{id}` | `200 OK` | `404 Not Found` | RF-002 |
| `PUT` | `/departments/{id}` | `200 OK` | `400 Bad Request`, `404 Not Found` | RF-003, RF-005, RF-008 |
| `DELETE` | `/departments/{id}` | `204 No Content` | `404 Not Found`, `409 Conflict` | RF-004 |

La respuesta JSON sera un DTO con `id`, `nombre`, `descripcion` y `fechaCreacion`. Los errores `400` reutilizaran el formato comun `{ "error": "...", "errors": { "campo": "motivo" } }`.

## Validacion y normalizacion
`CreateDepartmentRequest` exigira `nombre` mediante `@NotBlank`. `UpdateDepartmentRequest` tendra todos sus campos opcionales y validara los que se incluyan. Los constructores compactos aplicaran `trim()` a `nombre` y `descripcion`. Para asegurar la integridad de la validacion, Bean Validation se ejecutara sobre los datos normalizados, garantizando que campos como `nombre` no sean solo espacios en blanco.

Si `fechaCreacion` no se recibe en alta, el servicio asignara `LocalDate.now()`. Si se recibe, se conservara la fecha enviada. En una actualizacion parcial sólo se modificaran los campos presentes.

## Integridad referencial y conflicto `409`

El borrado de departamentos se ejecuta dentro de una transaccion y debe respetar las claves ajenas de `user_departments`. Cuando una eliminacion de un departamento referenciado provoque una excepcion de persistencia, el servicio debe traducirla al error global de integridad para devolver `409 Conflict`; si el departamento no existe se mantiene `404 Not Found`. Esta integracion queda alineada con el contrato de la spec 003 y evita exponer detalles de la base de datos.

## Persistencia
- Hibernate ORM generara la tabla `departments` cuando no exista. La estrategia comun sera `update`, pudiendo sobrescribirse mediante `DB_SCHEMA_GENERATION`.
- Las operaciones de escritura seran transaccionales.
- La eliminacion sera fisica.
- Los DTOs evitaran exponer la entidad.

## Pruebas
Las pruebas usaran H2 en memoria y no dependeran de Docker ni Kubernetes. Cada prueba indicara `RF-` en su `DisplayName` y verificara:
- `DepartmentResourceTest` configurara filtros globales `RequestLoggingFilter` y `ResponseLoggingFilter` de RestAssured. Cada interaccion imprimira metodo, URI, cabeceras, payload de peticion, codigo de estado, cabeceras de respuesta y payload de respuesta cuando exista.
- Alta, identificador y persistencia.
- Fecha del sistema cuando se omite.
- CRUD y listado.
- Validacion del nombre obligatorio con campo y motivo.
- Actualizacion parcial.
- Trim de nombre y descripcion en alta y modificacion.
- Aceptacion de numeros, espacios internos y caracteres especiales.
- Trazabilidad completa de peticiones y respuestas HTTP en las pruebas REST.

## Criterios de implementacion
- Todos los endpoints responden conforme al contrato.
- La tabla `departments` se crea mediante Hibernate ORM si no existe.
- Todas las RF tienen al menos una prueba.
- `mvnw clean verify` finaliza correctamente.
