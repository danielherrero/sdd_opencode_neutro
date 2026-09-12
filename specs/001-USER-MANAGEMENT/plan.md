# Nombre: plan.md
# Feature: 001-USER-MANAGEMENT

## Descripcion
Este documento define la implementacion tecnica de la feature de gestion CRUD de usuarios descrita en `spec.md`.

## Stack tecnologico

- Java: OpenJDK 25.
- Framework: Quarkus `3.39.3`.
- Gestion de dependencias y ciclo de vida: Apache Maven, usando el Maven Wrapper (`mvnw`/`mvnw.cmd`).
- API: REST JSON mediante Jakarta REST.
- Validacion: Bean Validation mediante Jakarta Validation.
- Persistencia: Hibernate ORM with Panache y PostgreSQL mediante JDBC.
- Seguridad de contrasenas: BCrypt. La contrasena nunca se almacenara en texto plano.
- Pruebas: JUnit 5, Quarkus Test y RestAssured para pruebas de integracion HTTP.
- Contenedorizacion: Docker.
- Orquestacion: Kubernetes.

El driver JDBC de PostgreSQL se gestionara mediante la extension de Quarkus correspondiente y su version quedara alineada con el BOM de Quarkus.

## Estructura del proyecto

```text
.
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .mvn/
├── scripts/
│   ├── build.ps1
│   ├── build.sh
│   ├── run-local.ps1
│   └── run-local.sh
├── src/
│   ├── main/
│   │   ├── java/<paquete>/user/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   └── exception/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/<paquete>/user/
└── deploy/
    └── kubernetes/
```

El paquete Java base se definira antes de crear las clases. La organizacion por capas sera obligatoria: `Controller`, `Service`, `Repository`, `DTO` y `Entity`.

## Configuracion de Maven y Quarkus

El `pom.xml` debe:

- Declarar `quarkus.platform.version` con el valor `3.39.3`.
- Importar el BOM de Quarkus para mantener alineadas las extensiones.
- Configurar el plugin `quarkus-maven-plugin`.
- Configurar compilacion, pruebas unitarias y pruebas de integracion.
- Incluir solo las extensiones necesarias para REST JSON, validacion, persistencia, JDBC, BCrypt y pruebas.
- Permitir empaquetado JVM para ejecucion local y contenedorizada.

La compilacion y ejecucion se realizaran con OpenJDK 25. El motor de base de datos sera PostgreSQL y la configuracion del datasource se realizara mediante las propiedades estandar de Quarkus.

## Perfiles de configuracion

La configuracion se centralizara en `src/main/resources/application.properties`, usando los perfiles de Quarkus:

- `%dev`: ejecucion local en modo desarrollo. Usara los valores de desarrollo y no incluira secretos en el repositorio.
- `%docker`: ejecucion dentro de un contenedor Docker. La conexion a la base de datos se resolvera mediante variables de entorno y nombres de servicio de la red Docker.
- `%kubernetes`: ejecucion en Kubernetes. La configuracion se obtendra de variables de entorno inyectadas desde `ConfigMap` y `Secret`, sin credenciales dentro de la imagen.

La configuracion comun contendra el puerto HTTP, el formato JSON, el datasource PostgreSQL `sdd`, el usuario `admin`, el modo de generacion de esquema y los parametros de observabilidad que se definan. Para este proyecto de prueba, la contrasena se declarara explicitamente en `docker-compose.yml` y en el `Secret` de Kubernetes.

Los perfiles se activaran con `-Dquarkus.profile=dev`, `-Dquarkus.profile=docker` o `-Dquarkus.profile=kubernetes`, segun el entorno.

## Scripts

Se crearan scripts equivalentes para PowerShell y shell POSIX:

- `scripts/build.ps1` y `scripts/build.sh`: ejecutaran el Maven Wrapper en modo no interactivo, limpiaran y compilaran el proyecto, ejecutando las pruebas.
- `scripts/run-local.ps1` y `scripts/run-local.sh`: iniciaran Quarkus en modo desarrollo con el perfil `dev`.
- Opcionalmente se anadira un script de empaquetado de imagen cuando se concrete el nombre y registro de la imagen Docker.

Los scripts deben:

- Fallar inmediatamente cuando un comando termine con error.
- Usar el Maven Wrapper en lugar de depender de una instalacion global de Maven.
- Documentar las variables de entorno requeridas.
- Ser ejecutables desde la raiz del proyecto.

Comandos equivalentes previstos:

```text
./mvnw clean verify
./mvnw quarkus:dev -Dquarkus.profile=dev
```

En Windows se utilizara `mvnw.cmd` desde los scripts PowerShell.

## Contrato de la API

El contrato REST se definira antes de implementar el backend y tendra como recurso base `/users`.

| Metodo | Ruta | Exito | Errores principales | Requisito |
|---|---|---|---|---|
| `POST` | `/users` | `201 Created` | `400 Bad Request` | RF-001, RF-005 |
| `GET` | `/users/{id}` | `200 OK` | `404 Not Found` | RF-002 |
| `PUT` | `/users/{id}` | `200 OK` | `400 Bad Request`, `404 Not Found` | RF-003, RF-005 |
| `DELETE` | `/users/{id}` | `204 No Content` | `404 Not Found`, `409 Conflict` | RF-004 |

El contrato detallara para cada operacion:

- Estructura de las peticiones y respuestas JSON.
- Campos obligatorios y reglas de validacion.
- Tipos de datos y formato de la fecha de nacimiento.
- Identificador generado por la base de datos.
- Ausencia de la contrasena y de su hash en las respuestas publicas.
- Formato comun de errores para `400`, `404`, `409` y `500`.

## Persistencia y seguridad

- La entidad `User` representara el usuario persistido y tendra un identificador numerico generado automaticamente.
- La eliminacion de usuarios sera fisica; no se anadira una marca de borrado logico mientras no existan relaciones que requieran conservar registros.
- Los DTO evitaran exponer directamente la entidad JPA.
- El repositorio encapsulara las consultas a la base de datos.
- La contrasena se transformara a BCrypt antes de persistirse.
- Las operaciones de escritura se ejecutaran dentro de transacciones.
- La eliminacion seguira la estrategia definida para la feature: fisica o logica. Si existen claves ajenas que impidan eliminar, se devolvera `409 Conflict`.
- Las credenciales y secretos se excluiran del control de versiones.

## Manejo de errores

Se implementara un manejador global de excepciones que traduzca las excepciones de validacion, recurso no encontrado, conflicto de integridad y errores inesperados al formato JSON comun de la API. No se devolveran trazas ni detalles sensibles al cliente.

## Docker

Se creara un `Dockerfile` multi-stage que:

- Compile la aplicacion usando Maven Wrapper.
- Genere el artefacto Quarkus JVM.
- Ejecute la aplicacion con una imagen runtime reducida y no privilegiada.
- Exponga el puerto HTTP configurado.
- Permita activar el perfil `docker` y configurar el datasource mediante variables de entorno.

La imagen no incluira secretos ni configuracion especifica de un entorno.

## Kubernetes

Se crearan manifiestos en `deploy/kubernetes/` para:

- `Deployment` de la aplicacion.
- `Service` para exponerla dentro del cluster.
- `ConfigMap` para configuracion no sensible.
- `Secret` para credenciales y valores sensibles.
- Probes de disponibilidad y estado.
- Recursos de CPU y memoria cuando se definan los limites del entorno.

El `Deployment` activara el perfil `kubernetes` y recibira la configuracion mediante `envFrom` o referencias explicitas a `ConfigMap` y `Secret`.
El contexto de Kubernetes utilizado por el proyecto sera `docker-desktop` y PostgreSQL se desplegara en el namespace `sdd`. Las operaciones de inicio, reinicio y parada de PostgreSQL se realizaran mediante los scripts de ciclo de vida de `scripts/`.

## Estrategia de pruebas

La implementacion seguira TDD. Cada test incluira en su nombre, etiqueta o documentacion el requisito funcional `RF-` que evalua. Como minimo se crearan pruebas para:

- `RF-001`: alta correcta y rechazo de datos invalidos.
- `RF-002`: consulta correcta y usuario inexistente.
- `RF-003`: actualizacion correcta y usuario inexistente.
- `RF-004`: eliminacion correcta, usuario inexistente y conflicto de integridad.
- `RF-005`: ausencia de cada campo obligatorio y validacion de formatos.
- `RNF-001`: verificacion de que la contrasena persistida es un hash BCrypt y no el valor original.

No se considerara terminada la feature si algun `RF-` carece de al menos un caso de prueba o si las pruebas no pasan.

## Criterios de implementacion

- El proyecto compila y verifica correctamente con Maven.
- La aplicacion arranca localmente mediante el script definido.
- Los perfiles `dev`, `docker` y `kubernetes` son seleccionables sin modificar el codigo.
- La API respeta el contrato y los codigos HTTP definidos.
- Todas las RF tienen trazabilidad desde la especificacion hasta uno o mas tests.
- Las contrasenas no se almacenan ni se exponen en texto plano.
- La aplicacion puede empaquetarse en Docker y desplegarse en Kubernetes con configuracion externa.
