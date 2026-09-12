# Nombre: plan.md
# Feature: 003-USER-DEPARMENT-MANAGEMENT
# Rama: 003-USER-DEPARMENT-MANAGEMENT

## Descripcion
Plan tecnico para implementar el CRUD de asociaciones entre usuarios y departamentos sobre la aplicacion `empresa`.

## Stack tecnologico
- Java OpenJDK 25.
- Quarkus `3.39.3`.
- Jakarta REST con JSON.
- Jakarta Bean Validation.
- Hibernate ORM with Panache.
- PostgreSQL para ejecucion persistente y H2 en `%test`.
- JUnit 5, Quarkus Test y RestAssured.

## Estructura
Se añadiran los paquetes:

```text
src/main/java/com/example/userdepartment/
├── controller/UserDepartmentResource.java
├── dto/CreateUserDepartmentRequest.java
├── dto/UpdateUserDepartmentRequest.java
├── dto/UserDepartmentResponse.java
├── entity/UserDepartment.java
├── exception/UserDepartmentNotFoundException.java
├── repository/UserDepartmentRepository.java
└── service/UserDepartmentService.java
```

La entidad `UserDepartment` se mapeara a `user_departments` con:
- `id` generado mediante `GenerationType.IDENTITY`.
- Relacion `@ManyToOne` obligatoria con `User`, usando `user_id` como clave ajena.
- Relacion `@ManyToOne` obligatoria con `Department`, usando `department_id` como clave ajena.
- `fecha_creacion` no nula.

Las relaciones se validaran antes de persistir y tambien quedaran protegidas por claves ajenas en la base de datos. No se expondra la entidad directamente.

## Contrato REST
El recurso base sera `/user-departments`.

| Metodo | Ruta | Exito | Errores | Requisito |
|---|---|---|---|---|
| `POST` | `/user-departments` | `201 Created` | `400 Bad Request`, `404 Not Found` | RF-001, RF-006, RF-007 |
| `GET` | `/user-departments` | `200 OK` | - | RF-005 |
| `GET` | `/user-departments/{id}` | `200 OK` | `404 Not Found` | RF-002 |
| `PUT` | `/user-departments/{id}` | `200 OK` | `400 Bad Request`, `404 Not Found` | RF-003, RF-006 |
| `DELETE` | `/user-departments/{id}` | `204 No Content` | `404 Not Found` | RF-004 |

La respuesta contendra `id`, `userId`, `departmentId` y `fechaCreacion`. Las peticiones de alta y modificacion no aceptaran `fechaCreacion` como campo modificable. Los errores utilizaran el formato comun con `error` y, para validaciones, `errors` por campo.

## Reglas de negocio
- En el alta, `userId` y `departmentId` seran obligatorios.
- En la modificacion, ambos campos seran opcionales de forma individual; si se incluyen, deberan referenciar entidades existentes.
- Una peticion de modificacion no podra cambiar `fechaCreacion`.
- `fechaCreacion` se asignara con `LocalDate.now()` dentro del servicio al crear.
- Las operaciones de escritura seran transaccionales.
- El borrado fisico de usuarios o departamentos referenciados producira `409 Conflict` mediante el manejo global de integridad referencial.

## Persistencia y configuracion
Hibernate ORM utilizara la estrategia comun `update`, configurable mediante `DB_SCHEMA_GENERATION`, para crear `user_departments` y sus claves ajenas cuando no existan. Las pruebas usaran H2 con el mismo modelo relacional.

## Estrategia de pruebas
Las pruebas se ejecutaran con H2 en memoria y cada `DisplayName` incluira el requisito `RF-`. `UserDepartmentResourceTest` configurara filtros globales `RequestLoggingFilter` y `ResponseLoggingFilter` de RestAssured para imprimir metodo, URI, cabeceras, estado y payload de todas las peticiones y respuestas.

Se probaran:
- Alta con IDs validos y persistencia.
- Alta con usuario o departamento inexistente.
- Consulta, listado y eliminacion.
- Modificacion con IDs validos e invalidos.
- Inmutabilidad de fecha de creacion.
- Conflictos de integridad al eliminar entidades referenciadas.
- Ausencia de fecha proporcionada por el cliente.

## Criterios de implementacion
- La tabla y claves ajenas se crean automaticamente si no existen.
- Todos los endpoints respetan el contrato y los codigos HTTP.
- Todas las RF tienen pruebas trazables.
- `mvn clean verify` finaliza correctamente.
