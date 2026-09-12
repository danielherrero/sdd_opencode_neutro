# Nombre: tasks.md
# Feature: 003-USER-DEPARMENT-MANAGEMENT
# Rama: 003-USER-DEPARMENT-MANAGEMENT

La implementacion seguira TDD. Cada prueba identificara el requisito `RF-` evaluado.

## 1. Modelo y persistencia
- [ ] T301 Crear la entidad `UserDepartment`.
- [ ] T302 Configurar la tabla `user_departments`.
- [ ] T303 Configurar el identificador generado por la base de datos.
- [ ] T304 Crear la clave ajena `user_id` hacia `users`.
- [ ] T305 Crear la clave ajena `department_id` hacia `departments`.
- [ ] T306 Configurar `fecha_creacion` como campo no nulo.
- [ ] T307 Crear `UserDepartmentRepository` con Panache.
- [ ] T308 Configurar la creacion automatica de tabla y claves ajenas.

## 2. DTOs y validacion
- [ ] T309 Crear DTO de alta sin fecha de creacion editable.
- [ ] T310 Crear DTO de modificacion parcial.
- [ ] T311 Crear DTO de respuesta.
- [ ] T312 Validar `userId` obligatorio en alta.
- [ ] T313 Validar `departmentId` obligatorio en alta.
- [ ] T314 Validar que los IDs referencien entidades existentes.
- [ ] T315 Rechazar fecha de creacion enviada por el cliente.

## 3. Servicio y API
- [ ] T316 Crear `UserDepartmentService`.
- [ ] T317 Implementar `POST /user-departments`.
- [ ] T318 Implementar `GET /user-departments/{id}`.
- [ ] T319 Implementar `GET /user-departments`.
- [ ] T320 Implementar `PUT /user-departments/{id}` parcial.
- [ ] T321 Implementar `DELETE /user-departments/{id}`.
- [ ] T322 Mapear asociaciones inexistentes a `404`.
- [ ] T323 Mapear referencias inválidas a `404` o `400` según el contrato.
- [ ] T324 Mapear conflictos de integridad a `409 Conflict`.
- [ ] T325 Reutilizar el formato global de errores.

## 4. Tests RF-001 a RF-009
- [ ] T326 Test `RF-001` de alta valida, ID generado y fecha automática.
- [ ] T327 Test `RF-001` de persistencia de la asociación.
- [ ] T328 Test `RF-002` de consulta existente e inexistente.
- [ ] T329 Test `RF-003` de modificación valida y parcial.
- [ ] T330 Test `RF-003` de modificación con usuario o departamento inexistente.
- [ ] T331 Test `RF-004` de eliminación existente e inexistente.
- [ ] T332 Test `RF-005` de listado de asociaciones.
- [ ] T333 Test `RF-006` de IDs ausentes y referencias inexistentes con campo y motivo.
- [ ] T334 Test `RF-007` de fecha automática e inmutabilidad.
- [ ] T335 Test `RF-008` de conflictos al eliminar usuario o departamento referenciado.
- [ ] T336 Test `RF-009` de trazabilidad HTTP completa con cabeceras y payloads.

## 5. Verificacion
- [ ] T337 Ejecutar todos los tests.
- [ ] T338 Ejecutar `mvn clean verify`.
- [ ] T339 Verificar trazabilidad entre `spec.md`, `plan.md` y los tests.
- [ ] T340 Actualizar la documentacion si cambia el comportamiento.
