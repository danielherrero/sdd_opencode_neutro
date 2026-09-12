# Nombre: tasks.md
# Feature: 002-DEPARTMENT-MANAGEMENT
# Rama: 002-DEPARTMENT-MANAGEMENT

La implementacion seguira TDD y cada prueba identificara el requisito `RF-` evaluado.

## 1. Modelo y persistencia
- [x] T201 Crear la entidad `Department`.
- [x] T202 Configurar el identificador generado por la base de datos.
- [x] T203 Crear la tabla `departments` con nombre, descripcion y fecha de creacion.
- [x] T204 Configurar nombre y fecha de creacion como no nulos.
- [x] T205 Crear `DepartmentRepository` con Panache.
- [x] T206 Configurar la generacion de la tabla si no existe.

## 2. DTOs y validacion
- [x] T207 Crear DTO de alta.
- [x] T208 Crear DTO de actualizacion parcial.
- [x] T209 Crear DTO de respuesta.
- [x] T210 Validar nombre obligatorio en alta.
- [x] T211 Permitir numeros, espacios internos y caracteres especiales en nombre y descripcion.
- [x] T212 Aplicar `trim()` a nombre y descripcion antes de validar y persistir.
- [x] T213 Asignar `LocalDate.now()` si falta fecha de creacion.

## 3. Servicio y API
- [x] T214 Crear `DepartmentService`.
- [x] T215 Implementar `POST /departments`.
- [x] T216 Implementar `GET /departments/{id}`.
- [x] T217 Implementar `GET /departments`.
- [x] T218 Implementar `PUT /departments/{id}` parcial.
- [x] T219 Implementar `DELETE /departments/{id}`.
- [x] T220 Mapear departamentos inexistentes a `404`.
- [x] T221 Reutilizar el formato global de errores `400`.

## 4. Tests RF-001 a RF-008
- [x] T222 Test `RF-001` de alta correcta y `201`.
- [x] T223 Test `RF-001` de identificador generado.
- [x] T224 Test `RF-001` de persistencia del departamento.
- [x] T225 Test `RF-002` de consulta existente e inexistente.
- [x] T226 Test `RF-003` de actualizacion completa y parcial.
- [x] T227 Test `RF-003` de actualizacion inexistente.
- [x] T228 Test `RF-004` de eliminacion existente e inexistente.
- [x] T229 Test `RF-005` de nombre ausente, nulo y vacio con campo y motivo.
- [x] T230 Test `RF-006` de listado de departamentos.
- [x] T231 Test `RF-007` de fecha predeterminada del sistema.
- [x] T232 Test `RF-008` de trim en alta y modificacion.
- [x] T233 Test `RF-008` aceptando numeros, espacios internos y caracteres especiales.

## 5. Verificacion
- [x] T234 Ejecutar todos los tests.
- [x] T235 Ejecutar `mvn clean verify`.
- [x] T236 Verificar trazabilidad entre `spec.md`, `plan.md` y los tests.
- [x] T237 Actualizar la documentación si cambia el comportamiento.
- [x] T238 Configurar el registro global de peticiones y respuestas HTTP de RestAssured con metodo, cabeceras, estado y payload.
- [x] T239 Renombrar los recursos de despliegue de la aplicacion a `empresa` para representar el backend completo.
