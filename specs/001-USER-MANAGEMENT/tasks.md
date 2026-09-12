# Nombre: tasks.md
# Feature: 001-USER-MANAGEMENT

## Descripcion

Lista ordenada de tareas necesarias para implementar la gestion CRUD de usuarios definida en `spec.md` y tecnicamente detallada en `plan.md`.

La implementacion seguira TDD: primero se crearan los tests y despues el codigo necesario para hacerlos pasar. Cada test debera indicar expresamente el requisito funcional `RF-` que evalua.

## 1. Preparacion del proyecto

- [x] T001 Crear la estructura Maven del proyecto backend.
- [x] T002 Configurar OpenJDK 25 como version de compilacion.
- [x] T003 Configurar el Maven Wrapper (`mvnw`, `mvnw.cmd` y `.mvn`).
- [x] T004 Configurar Quarkus `3.39.3` en el `pom.xml`.
- [x] T005 Importar el BOM de Quarkus.
- [x] T006 Anadir las extensiones para REST JSON, validacion, persistencia, PostgreSQL, BCrypt y pruebas.
- [x] T007 Configurar el plugin de Maven de Quarkus.
- [x] T008 Configurar compilacion, pruebas unitarias y pruebas de integracion.
- [x] T009 Verificar que el proyecto inicial compila correctamente con Maven.

## 2. Modelo de datos y persistencia

- [x] T057 Crear la entidad `User`.
- [x] T058 Configurar el identificador numerico generado por PostgreSQL.
- [x] T059 Crear los campos nombre, apellidos, fecha de nacimiento y contrasena.
- [x] T060 Configurar las restricciones de persistencia de los campos obligatorios.
- [x] T061 Crear el repositorio de usuarios usando Hibernate ORM with Panache.
- [x] T062 Configurar las consultas de usuarios por identificador.
- [x] T063 Configurar las operaciones de alta, actualizacion y eliminacion.
- [x] T064 Definir la estrategia de eliminacion fisica o logica.
- [x] T065 Configurar el tratamiento de conflictos de integridad referencial.
- [x] T101 Crear el `Dockerfile` multi-stage para la aplicacion Java en la raiz del proyecto.
- [x] T104 Crear `docker-compose.yml` para ejecutar la aplicacion y PostgreSQL.
- [x] T116 Crear el manifiesto YAML `deploy/kubernetes/postgresql-statefulset.yaml`.
- [x] T117 Crear el manifiesto YAML `deploy/kubernetes/postgresql-service.yaml` para el `Service` interno de PostgreSQL.
- [x] T118 Crear el manifiesto YAML `deploy/kubernetes/postgresql-pvc.yaml` para el `PersistentVolumeClaim` de PostgreSQL.
- [x] T119 Crear el manifiesto YAML `deploy/kubernetes/postgresql-secret.yaml` con las credenciales de PostgreSQL.
- [x] T120 Crear el manifiesto YAML `deploy/kubernetes/postgresql-configmap.yaml` con la configuracion no sensible de PostgreSQL.
- [x] T147 Definir el contexto `docker-desktop`, el namespace `sdd`, la base de datos `sdd` y el usuario `admin` para PostgreSQL.
- [x] T148 Implementar y verificar el inicio del contenedor Docker de PostgreSQL mediante Docker Compose.
- [x] T149 Implementar y verificar el reinicio del contenedor Docker de PostgreSQL mediante Docker Compose.
- [x] T150 Implementar y verificar la detencion del contenedor Docker de PostgreSQL mediante Docker Compose.
- [x] T151 Implementar y verificar el inicio del `StatefulSet` de PostgreSQL en el namespace `sdd` usando el contexto `docker-desktop`.
- [x] T152 Implementar y verificar el reinicio del pod de PostgreSQL en el namespace `sdd` usando el contexto `docker-desktop`.
- [x] T153 Implementar y verificar la detencion del pod de PostgreSQL en el namespace `sdd` usando el contexto `docker-desktop`.

## 3. Scripts de compilacion y ejecucion

- [ ] T020 Crear `scripts/build.ps1`.
- [ ] T021 Crear `scripts/build.sh`.
- [ ] T022 Crear `scripts/run-local.ps1`.
- [ ] T023 Crear `scripts/run-local.sh`.
- [ ] T024 Configurar los scripts para utilizar el Maven Wrapper.
- [ ] T025 Configurar los scripts para devolver error cuando falle un comando.
- [ ] T026 Configurar los scripts para ejecutarse desde la raiz del proyecto.
- [ ] T027 Ejecutar la compilacion mediante los scripts en Windows y shell POSIX.
- [ ] T028 Ejecutar la aplicacion localmente con el perfil `dev`.

## 4. Tests de requisitos funcionales

Los tests se crearan antes de la implementacion. El nombre, `@DisplayName` o documentacion de cada test debe incluir el requisito `RF-` que evalua.

### RF-001: Creacion de usuario

- [ ] T029 Crear test `RF-001` para registrar un usuario correctamente.
- [ ] T030 Crear test `RF-001` para verificar la respuesta HTTP `201 Created`.
- [ ] T031 Crear test `RF-001` para comprobar que se genera automaticamente el identificador.
- [ ] T032 Crear test `RF-001` para rechazar una peticion con datos invalidos.
- [ ] T033 Crear test `RF-001` para verificar que el usuario se persiste en PostgreSQL.

### RF-002: Obtencion de usuario

- [ ] T034 Crear test `RF-002` para consultar un usuario existente.
- [ ] T035 Crear test `RF-002` para verificar la respuesta HTTP `200 OK`.
- [ ] T036 Crear test `RF-002` para consultar un identificador inexistente.
- [ ] T037 Crear test `RF-002` para verificar la respuesta HTTP `404 Not Found`.

### RF-003: Actualizacion de usuario

- [ ] T038 Crear test `RF-003` para actualizar un usuario existente.
- [ ] T039 Crear test `RF-003` para verificar la respuesta HTTP de actualizacion correcta.
- [ ] T040 Crear test `RF-003` para actualizar un usuario con datos invalidos.
- [ ] T041 Crear test `RF-003` para actualizar un usuario inexistente.
- [ ] T042 Crear test `RF-003` para verificar la respuesta HTTP `404 Not Found`.

### RF-004: Eliminacion de usuario

- [ ] T043 Crear test `RF-004` para eliminar un usuario existente.
- [ ] T044 Crear test `RF-004` para verificar la respuesta HTTP `204 No Content`.
- [ ] T045 Crear test `RF-004` para eliminar un usuario inexistente.
- [ ] T046 Crear test `RF-004` para verificar el conflicto de integridad referencial.
- [ ] T047 Crear test `RF-004` para verificar la respuesta HTTP `409 Conflict` cuando corresponda.

### RF-005: Validacion de campos

- [ ] T048 Crear test `RF-005` para rechazar un nombre ausente.
- [ ] T049 Crear test `RF-005` para rechazar unos apellidos ausentes.
- [ ] T050 Crear test `RF-005` para rechazar una fecha de nacimiento ausente.
- [ ] T051 Crear test `RF-005` para rechazar una contrasena ausente.
- [ ] T052 Crear test `RF-005` para rechazar formatos invalidos.
- [ ] T053 Crear test `RF-005` para verificar la respuesta HTTP `400 Bad Request`.

### RNF-001: Seguridad

- [ ] T054 Crear test `RNF-001` para comprobar que la contrasena persistida es un hash BCrypt.
- [ ] T055 Crear test `RNF-001` para comprobar que el texto original no se persiste.
- [ ] T056 Crear test `RNF-001` para comprobar que la contrasena no aparece en las respuestas REST.

## 5. Configuracion de PostgreSQL y Quarkus

- [ ] T010 Crear `src/main/resources/application.properties`.
- [ ] T011 Configurar la conexion JDBC con PostgreSQL.
- [ ] T012 Configurar el perfil `%dev` para ejecucion local.
- [ ] T013 Configurar el perfil `%docker` para ejecucion dentro de Docker.
- [ ] T014 Configurar el perfil `%kubernetes` para ejecucion en Kubernetes.
- [ ] T015 Configurar URL, usuario y contrasena del datasource mediante variables de entorno.
- [ ] T016 Configurar el modo de generacion y actualizacion del esquema de base de datos.
- [ ] T017 Evitar credenciales y secretos en el repositorio.
- [ ] T018 Documentar las variables de entorno requeridas para cada perfil.
- [ ] T019 Verificar la activacion de cada perfil con `-Dquarkus.profile`.

## 6. DTOs y validacion

- [ ] T066 Crear el DTO de entrada para crear usuarios.
- [ ] T067 Crear el DTO de entrada para actualizar usuarios.
- [ ] T068 Crear el DTO de salida de usuario.
- [ ] T069 Evitar que la contrasena o su hash aparezcan en las respuestas publicas.
- [ ] T070 Anadir validaciones Bean Validation a los DTOs.
- [ ] T071 Validar que nombre, apellidos, fecha de nacimiento y contrasena sean obligatorios.
- [ ] T072 Validar el formato de la fecha de nacimiento.
- [ ] T073 Verificar que los DTOs no expongan directamente la entidad persistente.

## 7. Logica de negocio y seguridad

- [ ] T074 Crear el servicio de usuarios.
- [ ] T075 Implementar la creacion de usuarios.
- [ ] T076 Implementar la consulta de usuarios por identificador.
- [ ] T077 Implementar la actualizacion de usuarios.
- [ ] T078 Implementar la eliminacion de usuarios.
- [ ] T079 Implementar la deteccion de usuarios inexistentes.
- [ ] T080 Implementar la gestion de conflictos de integridad.
- [ ] T081 Implementar el hashing de contrasenas mediante BCrypt.
- [ ] T082 Verificar que no se registran contrasenas ni secretos en logs.
- [ ] T083 Ejecutar los tests despues de cada implementacion.
- [ ] T084 No continuar con la siguiente tarea si existen tests fallidos.

## 8. API REST

- [ ] T085 Crear el controlador REST de usuarios.
- [ ] T086 Implementar `POST /users`.
- [ ] T087 Implementar `GET /users/{id}`.
- [ ] T088 Implementar `PUT /users/{id}`.
- [ ] T089 Implementar `DELETE /users/{id}`.
- [ ] T090 Configurar los codigos HTTP definidos en el contrato.
- [ ] T091 Verificar el formato JSON de las peticiones y respuestas.
- [ ] T092 Verificar que las entidades no se exponen directamente.

## 9. Manejo global de errores

- [ ] T093 Crear el manejador global de excepciones.
- [ ] T094 Crear el formato comun de respuesta de error.
- [ ] T095 Mapear errores de validacion a `400 Bad Request`.
- [ ] T096 Mapear recursos inexistentes a `404 Not Found`.
- [ ] T097 Mapear conflictos de integridad a `409 Conflict`.
- [ ] T098 Mapear errores inesperados a `500 Internal Server Error`.
- [ ] T099 Evitar devolver trazas o informacion sensible al cliente.
- [ ] T100 Crear tests para cada tipo de error.

## 10. PostgreSQL en Docker

- [ ] T102 Configurar la compilacion de la aplicacion con OpenJDK 25.
- [ ] T103 Configurar la imagen runtime de la aplicacion con OpenJDK 25.
- [ ] T105 Configurar el servicio PostgreSQL con la imagen oficial.
- [ ] T106 Configurar usuario, contrasena y nombre de base de datos mediante variables de entorno.
- [ ] T107 Crear un volumen persistente para los datos PostgreSQL.
- [ ] T108 Crear un `healthcheck` para PostgreSQL.
- [ ] T109 Configurar la aplicacion para conectarse al servicio Docker de PostgreSQL, no a `localhost`.
- [ ] T110 Configurar el perfil `docker` en la aplicacion.
- [ ] T111 Exponer el puerto HTTP de la aplicacion.
- [ ] T112 Verificar que la aplicacion espera a que PostgreSQL este disponible.
- [ ] T113 Ejecutar la aplicacion y PostgreSQL con Docker Compose.
- [ ] T114 Verificar que la API persiste y consulta usuarios en PostgreSQL dentro de Docker.
- [ ] T115 Verificar que no existen secretos dentro de la imagen Docker.

## 11. PostgreSQL en Kubernetes

- [ ] T121 Configurar las variables de entorno de PostgreSQL mediante `Secret` y `ConfigMap`.
- [ ] T122 Configurar las probes de disponibilidad de PostgreSQL.
- [ ] T123 Configurar la persistencia de datos tras reiniciar el pod de PostgreSQL.
- [ ] T124 Crear el `Deployment` de la aplicacion.
- [ ] T125 Crear el `Service` de la aplicacion.
- [ ] T126 Configurar el perfil `kubernetes`.
- [ ] T127 Configurar la aplicacion para conectarse al nombre DNS del `Service` de PostgreSQL.
- [ ] T128 Configurar las variables de entorno de la aplicacion desde `ConfigMap` y `Secret`.
- [ ] T129 Configurar las probes de disponibilidad y estado de la aplicacion.
- [ ] T130 Configurar los puertos del contenedor y del servicio.
- [ ] T131 Definir recursos de CPU y memoria.
- [ ] T132 Desplegar PostgreSQL y la aplicacion en un cluster Kubernetes local.
- [ ] T133 Verificar la conectividad entre la aplicacion y PostgreSQL.
- [ ] T134 Verificar que la API persiste y consulta usuarios en Kubernetes.
- [ ] T135 Verificar la persistencia de los datos tras reiniciar los pods.

## 12. Verificacion final

- [ ] T136 Ejecutar todos los tests unitarios.
- [ ] T137 Ejecutar todos los tests de integracion.
- [ ] T138 Ejecutar `mvnw clean verify`.
- [ ] T139 Verificar que todas las RF tienen al menos un caso de prueba.
- [ ] T140 Verificar la trazabilidad entre `spec.md`, `plan.md` y los tests.
- [ ] T141 Verificar la compilacion con OpenJDK 25.
- [ ] T142 Verificar la ejecucion local mediante los scripts.
- [ ] T143 Verificar la ejecucion mediante Docker Compose.
- [ ] T144 Verificar el despliegue de PostgreSQL y la aplicacion mediante Kubernetes.
- [ ] T145 Actualizar `spec.md` si se produce cualquier cambio funcional.
- [ ] T146 Documentar cualquier decision tecnica adicional.
