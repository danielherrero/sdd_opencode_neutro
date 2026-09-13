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
- [x] T119 Crear el `Secret` Kubernetes `postgresql-credentials` desde `DB_PASSWORD` durante el inicio, sin versionar credenciales.
- [x] T120 Crear el manifiesto YAML `deploy/kubernetes/postgresql-configmap.yaml` con la configuracion no sensible de PostgreSQL.
- [x] T147 Definir el contexto `docker-desktop`, el namespace `sdd`, la base de datos `sdd` y el usuario `admin` para PostgreSQL.
- [x] T148 Implementar y verificar el inicio del contenedor Docker de PostgreSQL mediante Docker Compose.
- [x] T149 Implementar y verificar el reinicio del contenedor Docker de PostgreSQL mediante Docker Compose.
- [x] T150 Implementar y verificar la detencion del contenedor Docker de PostgreSQL mediante Docker Compose.
- [x] T151 Implementar y verificar el inicio del `StatefulSet` de PostgreSQL en el namespace `sdd` usando el contexto `docker-desktop`.
- [x] T152 Implementar y verificar el reinicio del pod de PostgreSQL en el namespace `sdd` usando el contexto `docker-desktop`.
- [x] T153 Implementar y verificar la detencion del pod de PostgreSQL en el namespace `sdd` usando el contexto `docker-desktop`.

## 3. Scripts de compilacion y ejecucion

- [x] T020 Crear `scripts/build.ps1`.
- [x] T021 Crear `scripts/build.sh`.
- [x] T022 Crear `scripts/run-local.ps1`.
- [x] T023 Crear `scripts/run-local.sh`.
- [x] T024 Configurar los scripts para utilizar el Maven Wrapper.
- [x] T025 Configurar los scripts para devolver error cuando falle un comando.
- [x] T026 Configurar los scripts para ejecutarse desde la raiz del proyecto.
- [x] T027 Ejecutar la compilacion mediante los scripts en Windows y shell POSIX.
- [x] T028 Ejecutar la aplicacion localmente con el perfil `dev`.

### Modos de ejecucion y conectividad

- [x] T154 Configurar la ejecucion local de Quarkus con el perfil `dev`.
- [x] T155 Configurar la ejecucion local para conectarse al PostgreSQL de Docker mediante `localhost` y el puerto publicado por Docker.
- [x] T156 Configurar la ejecucion de la aplicacion en un contenedor Docker con el perfil `docker`.
- [x] T157 Configurar la aplicacion Docker para conectarse al servicio PostgreSQL de Docker mediante el nombre del servicio, sin usar `localhost`.
- [x] T158 Configurar la ejecucion de la aplicacion como pod en el cluster `docker-desktop`, dentro del namespace `sdd`.
- [x] T159 Configurar la aplicacion en Kubernetes con el perfil `kubernetes`.
- [x] T160 Configurar la aplicacion en Kubernetes para conectarse al Service PostgreSQL del namespace `sdd`.
- [x] T161 Verificar que los tres modos utilizan la base de datos PostgreSQL `sdd` y el usuario `admin`.

## 4. Tests de requisitos funcionales

Los tests se crearan antes de la implementacion. El nombre, `@DisplayName` o documentacion de cada test debe incluir el requisito `RF-` que evalua.

- [x] T165 Configurar el registro en consola de todas las peticiones y respuestas HTTP de RestAssured, incluidos verbo, cabeceras, codigo de estado y payload.

- [x] T162 Configurar el perfil `%test` con H2 en memoria mediante Hibernate ORM with Panache, sin usar Docker ni Kubernetes.

### RF-001: Creacion de usuario

- [x] T029 Crear test `RF-001` para registrar un usuario correctamente.
- [x] T030 Crear test `RF-001` para verificar la respuesta HTTP `201 Created`.
- [x] T031 Crear test `RF-001` para comprobar que se genera automaticamente el identificador.
- [x] T032 Crear test `RF-001` para rechazar una peticion con datos invalidos.
- [x] T033 Crear test `RF-001` para verificar que el usuario se persiste en PostgreSQL.

### RF-002: Obtencion de usuario

- [x] T034 Crear test `RF-002` para consultar un usuario existente.
- [x] T035 Crear test `RF-002` para verificar la respuesta HTTP `200 OK`.
- [x] T036 Crear test `RF-002` para consultar un identificador inexistente.
- [x] T037 Crear test `RF-002` para verificar la respuesta HTTP `404 Not Found`.

### RF-006: Listado de usuarios

- [x] T163 Crear test `RF-006` que elimine los usuarios existentes, cree cinco usuarios aleatorios y compruebe mediante `GET /users` que se listan los cinco.

### RF-007: Mayoria de edad

- [x] T169 Crear test `RF-007` que rechace el alta de una persona menor de 18 anos e identifique `fechaNacimiento`.
- [x] T170 Crear test `RF-007` que acepte el alta de una persona que cumple exactamente 18 anos.
- [x] T171 Crear test `RF-007` que rechace una actualizacion de fecha de nacimiento para una persona menor de 18 anos.

### RF-003: Actualizacion de usuario

- [x] T038 Crear test `RF-003` para actualizar un usuario existente.
- [x] T039 Crear test `RF-003` para verificar la respuesta HTTP de actualizacion correcta.
- [x] T040 Crear test `RF-003` para actualizar un usuario con datos invalidos.
- [x] T041 Crear test `RF-003` para actualizar un usuario inexistente.
- [x] T042 Crear test `RF-003` para verificar la respuesta HTTP `404 Not Found`.

### RF-004: Eliminacion de usuario

- [x] T043 Crear test `RF-004` para eliminar un usuario existente.
- [x] T044 Crear test `RF-004` para verificar la respuesta HTTP `204 No Content`.
- [x] T045 Crear test `RF-004` para eliminar un usuario inexistente.
- [x] T046 Crear test `RF-004` para verificar el conflicto de integridad referencial.
- [x] T047 Crear test `RF-004` para verificar la respuesta HTTP `409 Conflict` cuando corresponda.

### RF-005: Validacion de campos

- [x] T048 Crear test `RF-005` para rechazar un nombre ausente.
- [x] T049 Crear test `RF-005` para rechazar unos apellidos ausentes.
- [x] T050 Crear test `RF-005` para rechazar una fecha de nacimiento ausente.
- [x] T051 Crear test `RF-005` para rechazar una contrasena ausente.
- [x] T052 Crear test `RF-005` para rechazar formatos invalidos.
- [x] T053 Crear test `RF-005` para verificar la respuesta HTTP `400 Bad Request`.
- [x] T166 Crear test `RF-005` para comprobar que cada error de validacion identifica el campo afectado.
- [x] T167 Crear test `RF-005` para comprobar que cada error de validacion explica el motivo del rechazo.

### RNF-001: Seguridad

- [x] T054 Crear test `RNF-001` para comprobar que la contrasena persistida es un hash BCrypt.
- [x] T055 Crear test `RNF-001` para comprobar que el texto original no se persiste.
- [x] T056 Crear test `RNF-001` para comprobar que la contrasena no aparece en las respuestas REST.

## 5. Configuracion de PostgreSQL y Quarkus

- [x] T010 Crear `src/main/resources/application.properties`.
- [x] T011 Configurar la conexion JDBC con PostgreSQL.
- [x] T012 Configurar el perfil `%dev` para ejecucion local.
- [x] T013 Configurar el perfil `%docker` para ejecucion dentro de Docker.
- [x] T014 Configurar el perfil `%kubernetes` para ejecucion en Kubernetes.
- [x] T015 Configurar URL, usuario y contrasena del datasource mediante variables de entorno.
- [x] T016 Configurar el modo de generacion y actualizacion del esquema de base de datos.
- [x] T017 Evitar credenciales y secretos en el repositorio.
- [x] T018 Documentar las variables de entorno requeridas para cada perfil.
- [x] T019 Verificar la activacion de cada perfil con `-Dquarkus.profile`.

## 6. DTOs y validacion

- [x] T066 Crear el DTO de entrada para crear usuarios.
- [x] T067 Crear el DTO de entrada para actualizar usuarios.
- [x] T068 Crear el DTO de salida de usuario.
- [x] T069 Evitar que la contrasena o su hash aparezcan en las respuestas publicas.
- [x] T070 Anadir validaciones Bean Validation a los DTOs.
- [x] T071 Validar los campos obligatorios en creacion y los campos incluidos en actualizacion.
- [x] T072 Validar el formato de la fecha de nacimiento.
- [x] T172 Validar que la fecha de nacimiento incluida en el alta o modificacion corresponda a una persona mayor de edad.

### RF-008: Formato de nombre y apellidos

- [x] T173 Crear test `RF-008` que rechace en el alta nombres y apellidos compuestos solo por espacios, con numeros o con simbolos.
- [x] T174 Crear test `RF-008` que rechace en la modificacion nombres y apellidos compuestos solo por espacios, con numeros o con simbolos.
- [x] T175 Validar en los DTOs que `nombre` y `apellidos` solo contengan letras Unicode.

### RF-009: Normalizacion de nombre y apellidos

- [x] T176 Crear test `RF-009` que compruebe el recorte de espacios iniciales y finales en el alta.
- [x] T177 Crear test `RF-009` que compruebe el recorte de espacios iniciales y finales en la modificacion.
- [x] T178 Aplicar `trim()` a `nombre` y `apellidos` antes de validarlos y persistirlos.
- [x] T073 Verificar que los DTOs no expongan directamente la entidad persistente.

## 7. Logica de negocio y seguridad

- [x] T074 Crear el servicio de usuarios.
- [x] T075 Implementar la creacion de usuarios.
- [x] T076 Implementar la consulta de usuarios por identificador.
- [x] T077 Implementar la actualizacion de usuarios.
- [x] T078 Implementar la eliminacion de usuarios.
- [x] T079 Implementar la deteccion de usuarios inexistentes.
- [x] T080 Implementar la gestion de conflictos de integridad.
- [x] T081 Implementar el hashing de contrasenas mediante BCrypt.
- [x] T082 Registrar las peticiones y respuestas HTTP completas durante las pruebas, incluidos los datos de prueba de contrasena, por decision explicita.
- [x] T083 Ejecutar los tests despues de cada implementacion.
- [x] T084 No continuar con la siguiente tarea si existen tests fallidos.

## 8. API REST

- [x] T085 Crear el controlador REST de usuarios.
- [x] T086 Implementar `POST /users`.
- [x] T087 Implementar `GET /users/{id}`.
- [x] T164 Implementar `GET /users`.
- [x] T088 Implementar `PUT /users/{id}`.
- [x] T089 Implementar `DELETE /users/{id}`.
- [x] T090 Configurar los codigos HTTP definidos en el contrato.
- [x] T091 Verificar el formato JSON de las peticiones y respuestas.
- [x] T092 Verificar que las entidades no se exponen directamente.

## 9. Manejo global de errores

- [x] T093 Crear el manejador global de excepciones.
- [x] T094 Crear el formato comun de respuesta de error.
- [x] T095 Mapear errores de validacion a `400 Bad Request`.
- [x] T168 Incluir en los errores de validacion el campo afectado y el motivo del error.
- [x] T096 Mapear recursos inexistentes a `404 Not Found`.
- [x] T097 Mapear conflictos de integridad a `409 Conflict`.
- [x] T098 Mapear errores inesperados a `500 Internal Server Error`.
- [x] T099 Evitar devolver trazas o informacion sensible al cliente.
- [x] T100 Crear tests para cada tipo de error.

## 10. PostgreSQL en Docker

- [x] T102 Configurar la compilacion de la aplicacion con OpenJDK 25.
- [x] T103 Configurar la imagen runtime de la aplicacion con OpenJDK 25.
- [x] T105 Configurar el servicio PostgreSQL con la imagen oficial.
- [x] T106 Configurar usuario, contrasena y nombre de base de datos mediante variables de entorno.
- [x] T107 Crear un volumen persistente para los datos PostgreSQL.
- [x] T108 Crear un `healthcheck` para PostgreSQL.
- [x] T109 Configurar la aplicacion para conectarse al servicio Docker de PostgreSQL, no a `localhost`.
- [x] T110 Configurar el perfil `docker` en la aplicacion.
- [x] T111 Exponer el puerto HTTP de la aplicacion.
- [x] T112 Verificar que la aplicacion espera a que PostgreSQL este disponible.
- [x] T113 Ejecutar la aplicacion y PostgreSQL con Docker Compose.
- [x] T114 Verificar que la API persiste y consulta usuarios en PostgreSQL dentro de Docker.
- [x] T115 Verificar que no existen secretos dentro de la imagen Docker.

## 11. PostgreSQL en Kubernetes

- [x] T121 Configurar las variables de entorno de PostgreSQL mediante `Secret` y `ConfigMap`.
- [x] T122 Configurar las probes de disponibilidad de PostgreSQL.
- [x] T123 Configurar la persistencia de datos tras reiniciar el pod de PostgreSQL.
- [x] T124 Crear el `Deployment` de la aplicacion.
- [x] T125 Crear el `Service` de la aplicacion.
- [x] T126 Configurar el perfil `kubernetes`.
- [x] T127 Configurar la aplicacion para conectarse al nombre DNS del `Service` de PostgreSQL.
- [x] T128 Configurar las variables de entorno de la aplicacion desde `ConfigMap` y `Secret`.
- [x] T129 Configurar las probes de disponibilidad y estado de la aplicacion.
- [x] T130 Configurar los puertos del contenedor y del servicio.
- [x] T131 Definir recursos de CPU y memoria.
- [x] T132 Desplegar PostgreSQL y la aplicacion en un cluster Kubernetes local.
- [x] T133 Verificar la conectividad entre la aplicacion y PostgreSQL.
- [x] T134 Verificar que la API persiste y consulta usuarios en Kubernetes.
- [x] T135 Verificar la persistencia de los datos tras reiniciar los pods.

## 12. Verificacion final

- [x] T136 Ejecutar todos los tests unitarios.
- [x] T137 Ejecutar todos los tests de integracion.
- [x] T138 Ejecutar `mvnw clean verify`.
- [x] T139 Verificar que todas las RF tienen al menos un caso de prueba.
- [x] T140 Verificar la trazabilidad entre `spec.md`, `plan.md` y los tests.
- [x] T141 Verificar la compilacion con OpenJDK 25.
- [x] T142 Verificar la ejecucion local mediante los scripts.
- [x] T143 Verificar la ejecucion mediante Docker Compose.
- [x] T144 Verificar el despliegue de PostgreSQL y la aplicacion mediante Kubernetes.
- [x] T145 Actualizar `spec.md` si se produce cualquier cambio funcional.
- [x] T146 Documentar cualquier decision tecnica adicional.
