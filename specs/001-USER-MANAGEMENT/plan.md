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
- Pruebas: JUnit 5, Quarkus Test y RestAssured para pruebas de integracion HTTP. Las pruebas usaran H2 en memoria mediante Hibernate ORM with Panache, sin depender de Docker ni Kubernetes.
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

### Referencia de Quarkus

La configuracion de esta feature se ha contrastado con la documentacion oficial de Quarkus consultada mediante Context7. Las guias de referencia son:

- [Maven tooling](https://quarkus.io/guides/maven-tooling): BOM, `quarkus-maven-plugin`, modo desarrollo y empaquetado JVM.
- [Configuration reference](https://quarkus.io/guides/config-reference): perfiles de configuracion y variables de entorno por perfil.
- [Datasources](https://quarkus.io/guides/datasource): propiedades `quarkus.datasource` para conexiones JDBC.
- [Hibernate ORM with Panache](https://quarkus.io/guides/hibernate-orm-panache): configuracion de Hibernate ORM y Panache.

La version fijada para la feature es Quarkus `3.39.3`; las extensiones no declararan versiones individuales, ya que las gestiona el BOM de esa version.

El `pom.xml` debe:

- Declarar `<packaging>quarkus</packaging>`.
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

La configuracion comun contendra el puerto HTTP, el formato JSON, el datasource PostgreSQL `sdd`, el usuario `admin`, el modo de generacion de esquema y los parametros de observabilidad que se definan. La contrasena se proporcionara con la variable de entorno `DB_PASSWORD`; no se declarara en archivos versionados ni dentro de la imagen.

Los perfiles se activaran con `-Dquarkus.profile=dev`, `-Dquarkus.profile=docker` o `-Dquarkus.profile=kubernetes`, segun el entorno. `QUARKUS_PROFILE` podra utilizarse como alternativa mediante variable de entorno. Quarkus permite varios perfiles separados por comas y aplica primero el ultimo perfil indicado; esta feature activara uno solo para evitar ambiguedades.

### Variables de entorno

| Variable | Perfiles | Uso |
|---|---|---|
| `DB_PASSWORD` | `dev`, `docker`, `kubernetes` | Contrasena del usuario PostgreSQL. Es obligatoria y no se versiona. |
| `DB_JDBC_URL` | `dev`, `docker`, `kubernetes` | URL JDBC. Si se omite se usa la URL predeterminada del perfil activo. |
| `DB_USERNAME` | `dev`, `docker`, `kubernetes` | Usuario PostgreSQL; su valor predeterminado es `admin`. |
| `HTTP_PORT` | Todos | Puerto HTTP; el valor predeterminado es `8081`. |
| `DB_SCHEMA_GENERATION` | Todos | Estrategia de esquema de Hibernate ORM; el valor predeterminado es `validate`. |

Para Docker Compose se debe crear un archivo local `.env` a partir de `.env.example` y asignar `DB_PASSWORD`. Este archivo esta excluido de Git. El script de Kubernetes crea o actualiza el `Secret` `postgresql-credentials` desde `DB_PASSWORD`; el manifiesto con una contrasena no se versiona.

Las variables de entorno que representen propiedades especificas de un perfil usaran el patron de Quarkus `_PERFIL_PROPIEDAD`. Por ejemplo, `_DOCKER_QUARKUS_HTTP_PORT=8080` representa `%docker.quarkus.http.port=8080`. Las propiedades de datasource se recibirian preferentemente mediante las variables estandar sin perfil cuando el entorno ya selecciona uno: `QUARKUS_DATASOURCE_USERNAME`, `QUARKUS_DATASOURCE_PASSWORD` y `QUARKUS_DATASOURCE_JDBC_URL`.

La configuracion declarara explicitamente el tipo de base de datos cuando convivan los drivers de PostgreSQL y H2 en el classpath:

```properties
quarkus.datasource.db-kind=postgresql
%dev.quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/sdd
%docker.quarkus.datasource.jdbc.url=jdbc:postgresql://postgresql:5432/sdd
%kubernetes.quarkus.datasource.jdbc.url=jdbc:postgresql://postgresql.sdd.svc:5432/sdd
%test.quarkus.datasource.db-kind=h2
%test.quarkus.datasource.jdbc.url=jdbc:h2:mem:users;DB_CLOSE_DELAY=-1
%test.quarkus.hibernate-orm.schema-management.strategy=drop-and-create
```

Los nombres definitivos de los servicios Docker y Kubernetes se confirmaran al crear sus manifiestos; las URL de ejemplo anteriores deben actualizarse si dichos nombres difieren.

## Modos de ejecucion y conectividad

La aplicacion backend debera poder ejecutarse en los siguientes modos:

- **Ejecucion local**: Quarkus se ejecutara directamente en el equipo mediante los scripts locales y utilizara el servicio PostgreSQL levantado con Docker. La conexion se realizara mediante el puerto publicado por Docker.
- **Aplicacion en contenedor Docker**: La aplicacion se ejecutara dentro de un contenedor Docker y se conectara al servicio PostgreSQL de Docker mediante el nombre del servicio dentro de la red de Docker, sin utilizar `localhost`.
- **Aplicacion en Kubernetes**: La aplicacion se ejecutara como un pod en el cluster `docker-desktop`, dentro del namespace `sdd`, y utilizara el servicio PostgreSQL desplegado en Kubernetes en ese mismo namespace.

En todos los modos se utilizara la base de datos PostgreSQL `sdd` y el usuario `admin`. El perfil `dev` correspondera a la ejecucion local, el perfil `docker` a la aplicacion contenedorizada y el perfil `kubernetes` a la ejecucion como pod.

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
./mvnw package -Dquarkus.profile=docker
```

En Windows se utilizara `mvnw.cmd` desde los scripts PowerShell.

## Contrato de la API

El contrato REST se definira antes de implementar el backend y tendra como recurso base `/users`.

| Metodo | Ruta | Exito | Errores principales | Requisito |
|---|---|---|---|---|
| `POST` | `/users` | `201 Created` | `400 Bad Request` | RF-001, RF-005 |
| `GET` | `/users` | `200 OK` | - | RF-006 |
| `GET` | `/users/{id}` | `200 OK` | `404 Not Found` | RF-002 |
| `PUT` | `/users/{id}` | `200 OK` | `400 Bad Request`, `404 Not Found` | RF-003, RF-005 |
| `DELETE` | `/users/{id}` | `204 No Content` | `404 Not Found`, `409 Conflict` | RF-004 |

El contrato detallara para cada operacion:

- Estructura de las peticiones y respuestas JSON.
- Campos obligatorios y reglas de validacion.
- Tipos de datos y formato de la fecha de nacimiento.
- La fecha de nacimiento incluida en una creacion o actualizacion debe corresponder a una persona de al menos 18 anos en la fecha de evaluacion. El limite se calcula con `Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18`.
- `nombre` y `apellidos` se validaran con la expresion regular Unicode `\\p{L}+`, por lo que solo aceptaran letras, incluidas letras acentuadas. Las cadenas vacias, compuestas exclusivamente por espacios, y las que contengan digitos o simbolos devolveran `400 Bad Request` con el campo y el motivo del rechazo.
- Los DTOs de creacion y actualizacion aplicaran `trim()` de forma segura para valores no nulos a `nombre` y `apellidos` en su constructor compacto, antes de Bean Validation y de que el servicio persista los valores.
- Identificador generado por la base de datos.
- Ausencia de la contrasena y de su hash en las respuestas publicas.
- Formato comun de errores para `400`, `404`, `409` y `500`. Los errores de validacion `400` incluiran un objeto `errors` que asocia cada campo invalido con el motivo de su rechazo.

## Persistencia y seguridad

- La entidad `User` representara el usuario persistido y tendra un identificador numerico generado automaticamente.
- La eliminacion de usuarios sera fisica; no se anadira una marca de borrado logico mientras no existan relaciones que requieran conservar registros.
- Los DTO evitaran exponer directamente la entidad JPA.
- El repositorio encapsulara las consultas a la base de datos.
- La contrasena se transformara a BCrypt antes de persistirse.
- La actualizacion sera parcial: el identificador de la ruta es obligatorio y los demas campos son opcionales. Cada campo incluido se validara igual que durante la creacion y, cuando corresponda, se persistira; la contrasena incluida se almacenara como hash BCrypt.
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

Las pruebas HTTP imprimiran la peticion y respuesta completas, incluidas las contrasenas de datos de prueba, por decision explicita para facilitar el diagnostico. Este registro esta limitado a la ejecucion de pruebas y no forma parte de los logs de la aplicacion.

El perfil `%test` usara una base de datos H2 en memoria y generara el esquema para cada ejecucion. Las pruebas no se conectaran al PostgreSQL de Docker ni al PostgreSQL de Kubernetes.

- `RF-001`: alta correcta y rechazo de datos invalidos.
- `RF-002`: consulta correcta y usuario inexistente.
- `RF-003`: actualizacion correcta y usuario inexistente.
- `RF-004`: eliminacion correcta, usuario inexistente y conflicto de integridad.
- `RF-005`: ausencia de cada campo obligatorio y validacion de formatos.
- `RF-006`: limpieza de los usuarios existentes, creacion de cinco usuarios aleatorios y listado de los cinco mediante `GET /users`.
- `RF-007`: rechazo de fechas de menores de edad en creacion y actualizacion, y aceptacion del limite exacto de 18 anos.
- `RF-008`: rechazo en creacion y actualizacion de `nombre` y `apellidos` con solo espacios, numeros o simbolos.
- `RF-009`: recorte de espacios iniciales y finales de `nombre` y `apellidos` en creacion y actualizacion.
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
