# Nombre: spec.md
# Feature: 003-USER-DEPARMENT-MANAGEMENT
# Rama: 003-USER-DEPARMENT-MANAGEMENT

## Descripcion
Esta funcionalidad permite gestionar asociaciones entre usuarios y departamentos mediante una API REST. Cada asociacion representa la pertenencia de un usuario a un departamento y se almacenara en una tabla SQL propia.

Cada registro tendra:
- Un identificador numerico generado por la base de datos.
- El identificador de un usuario existente.
- El identificador de un departamento existente.
- Una fecha de creacion asignada automaticamente por el sistema.

Un usuario podra estar asociado a cero, uno o varios departamentos. Un departamento tambien podra tener cero, uno o varios usuarios asociados.

## Requisitos Funcionales (RF)
- **RF-001: Alta de Asociacion**: Cuando el sistema reciba una solicitud valida de creacion con un identificador de usuario y un identificador de departamento existentes, debera crear la asociacion, generar su identificador numerico y asignar automaticamente la fecha de creacion.
- **RF-002: Obtencion de Asociacion**: Cuando el sistema reciba una solicitud de consulta con el identificador de una asociacion existente, debera devolver sus datos, incluyendo el identificador de usuario, el identificador de departamento y la fecha de creacion. Si no existe, debera devolver `404 Not Found`.
- **RF-003: Actualizacion de Asociacion**: Cuando el sistema reciba una solicitud de modificacion de una asociacion existente, debera permitir modificar el identificador de usuario y el identificador de departamento únicamente si ambos identificadores existen. La fecha de creacion no podra modificarse. Si la asociacion o alguna entidad referenciada no existe, debera rechazar la solicitud e informar del motivo.
- **RF-004: Eliminacion de Asociacion**: Cuando el sistema reciba una solicitud de eliminacion con el identificador de una asociacion existente, debera eliminarla fisicamente. Si no existe, debera devolver `404 Not Found`.
- **RF-005: Listado de Asociaciones**: Cuando el sistema reciba una solicitud de listado, debera devolver todas las asociaciones existentes con sus identificadores y fechas de creacion.
- **RF-005A: Cardinalidad de Usuario**: Cuando el sistema gestione las asociaciones, debera permitir que un usuario tenga cero, una o multiples asociaciones con departamentos, sin limitar el numero de departamentos asociados.
- **RF-006: Validacion de Identificadores**: Mientras el sistema valide una solicitud de alta o modificacion, si falta el identificador de usuario o el identificador de departamento, o si no corresponde a una entidad existente, debera rechazar la solicitud. La respuesta debera identificar el campo afectado y explicar el motivo.
- **RF-007: Fecha de Creacion Automatica**: Cuando el sistema cree una asociacion, debera asignar automaticamente la fecha del sistema y no aceptara una fecha de creacion proporcionada por el cliente.
- **RF-008: Integridad Referencial**: El sistema debera mantener relaciones entre las asociaciones, usuarios y departamentos mediante claves ajenas. No podra eliminarse un usuario o departamento si existen asociaciones que lo referencian; en ese caso debera devolver `409 Conflict`.
- **RF-009: Trazabilidad HTTP en Pruebas**: Durante la ejecucion de las pruebas REST, se debera imprimir la peticion y la respuesta completas, incluyendo metodo HTTP, URI, cabeceras, codigo de estado y payload cuando exista.

## Requisitos No Funcionales (RNF)
- **RNF-001: Persistencia**: Las asociaciones deberan persistir en una tabla propia `user_departments`.
- **RNF-002: Integridad**: `user_id` y `department_id` seran claves ajenas hacia `users` y `departments`.
- **RNF-003: Interoperabilidad**: La API debera seguir los estandares REST y utilizar codigos HTTP adecuados.
- **RNF-004: Separacion de Datos**: La API utilizara DTOs y no expondra directamente la entidad JPA.

## Criterios de Aceptacion
- El alta valida devuelve `201 Created` con ID y fecha generados.
- No se permite crear una asociacion si el usuario o departamento no existe.
- No se permite modificar una asociacion para apuntar a un usuario o departamento inexistente.
- La fecha de creacion se asigna automaticamente y permanece inmutable.
- Se pueden consultar, listar y eliminar asociaciones mediante la API.
- Un usuario puede existir sin departamentos, con un departamento o con multiples departamentos.
- La eliminacion de usuarios o departamentos referenciados devuelve `409 Conflict`.
- Los errores de identificadores identifican el campo y el motivo.
- Las pruebas REST imprimen cabeceras, metodo, estado y payloads de peticion y respuesta.
