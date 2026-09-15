# Nombre: tasks.md
# Feature: 003-USER-DEPARMENT-MANAGEMENT
# Rama: 003-USER-DEPARMENT-MANAGEMENT

La implementacion seguira TDD. Cada prueba identificara el requisito `RF-` evaluado.

## 1. Modelo y persistencia
- [x] T301 Crear la entidad `UserDepartment`.
- [x] T302 Configurar la tabla `user_departments`.
- [x] T303 Configurar el identificador generado por la base de datos.
- [x] T304 Crear la clave ajena `user_id` hacia `users`.
- [x] T305 Crear la clave ajena `department_id` hacia `departments`.
- [x] T306 Configurar `fecha_creacion` como campo no nulo.
- [x] T307 Crear `UserDepartmentRepository` con Panache.
- [x] T308 Configurar la creacion automatica de tabla y claves ajenas.
- [x] T308A Definir la cardinalidad usuario-departamentos como `0..N` mediante la tabla intermedia.

## 2. DTOs y validacion
- [x] T309 Crear DTO de alta sin fecha de creacion editable.
- [x] T310 Crear DTO de modificacion parcial.
- [x] T311 Crear DTO de respuesta.
- [x] T312 Validar `userId` obligatorio en alta.
- [x] T313 Validar `departmentId` obligatorio en alta.
- [ ] T314 Validar que los IDs referencien entidades existentes mediante consulta explícita en el servicio (retornar 400/404).
- [x] T315 Rechazar fecha de creacion enviada por el cliente.

## 3. Servicio y API
- [x] T316 Crear `UserDepartmentService`.
- [x] T317 Implementar `POST /user-departments`.
- [x] T318 Implementar `GET /user-departments/{id}`.
- [x] T319 Implementar `GET /user-departments`.
- [x] T320 Implementar `PUT /user-departments/{id}` parcial.
- [x] T321 Implementar `DELETE /user-departments/{id}`.
- [x] T322 Mapear asociaciones inexistentes a `404`.
- [x] T323 Mapear referencias invalidas a `404` o `400` según el contrato.
- [x] T324 Mapear conflictos de integridad a `409 Conflict`.
- [x] T325 Reutilizar el formato global de errores.
- [x] T325A Crear utilidad de preparacion que vacie asociaciones, usuarios y departamentos y cree cinco usuarios y cinco departamentos aleatorios.
- [x] T325B Documentar la integracion posterior del conflicto `409` en los servicios de borrado de las specs 001 y 002.

## 4. Tests RF-001 a RF-009
- [x] T326 Test `RF-001` de alta valida, ID generado y fecha automatica.
- [x] T327 Test `RF-001` de persistencia de la asociacion.
- [x] T328 Test `RF-002` de consulta existente e inexistente.
- [x] T329 Test `RF-003` de modificacion valida y parcial.
- [x] T330 Test `RF-003` de modificacion con usuario o departamento inexistente.
- [x] T331 Test `RF-004` de eliminacion existente e inexistente.
- [x] T332 Test `RF-005` de listado de asociaciones.
- [x] T333 Test `RF-006` de IDs ausentes y referencias inexistentes con campo y motivo.
- [x] T334 Test `RF-007` de fecha automatica e inmutabilidad.
- [x] T335 Test `RF-008` de conflictos al eliminar usuario o departamento referenciado.
- [x] T336 Test `RF-009` de trazabilidad HTTP completa con cabeceras y payloads.
- [x] T336A Test `RF-010` que verifique un usuario sin departamentos, con uno y con multiples departamentos.

## 5. Verificacion
- [x] T337 Ejecutar todos los tests.
- [x] T338 Ejecutar `mvn clean verify`.
- [x] T339 Verificar trazabilidad entre `spec.md`, `plan.md` y los tests.
- [x] T340 Actualizar la documentacion si cambia el comportamiento.
