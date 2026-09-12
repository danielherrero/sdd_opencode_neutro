# Nombre: spec.md
# Feature: 001-USER-MANAGEMENT

## Descripción
Esta funcionalidad permite la gestión completa (CRUD) de usuarios en el sistema mediante una interfaz REST. Es el componente base para la autenticación y el control de acceso del backend.

## Requisitos Funcionales (RF)
- **RF-001: Creación de Usuario**: Cuando el sistema reciba una solicitud válida de creación con nombre, apellidos, fecha de nacimiento y contraseña, deberá crear el usuario, generar automáticamente su identificador numérico único y almacenar la contraseña como hash.
- **RF-002: Obtención de Usuario**: Cuando el sistema reciba una solicitud de consulta con el identificador único de un usuario existente, deberá devolver los detalles del usuario asociado.
- **RF-003: Actualización de Usuario**: Cuando el sistema reciba una solicitud de actualización con el identificador de un usuario existente, deberá modificar exclusivamente los campos proporcionados entre nombre, apellidos, fecha de nacimiento y contraseña, siempre que cada campo proporcionado pase las mismas validaciones aplicadas durante la creación. Si el identificador no existe en la base de datos, deberá rechazar la actualización e indicar que el usuario no existe.
- **RF-004: Eliminación de Usuario**: Cuando el sistema reciba una solicitud de eliminación con el identificador de un usuario existente, deberá eliminarlo física o lógicamente según la estrategia de persistencia definida. Si el identificador no existe en la base de datos, deberá rechazar la eliminación e indicar que el usuario no existe. Si el identificador está siendo utilizado como clave ajena en otra tabla, deberá rechazar la eliminación e informar del conflicto de integridad.
- **RF-005: Validación de Campos**: Mientras el sistema valide una solicitud de creación, si falta el nombre, los apellidos, la fecha de nacimiento o la contraseña, o si uno de esos campos tiene un valor o formato no válido, deberá rechazar la solicitud. Mientras valide una actualización, deberá permitir la ausencia de los campos opcionales y rechazar cada campo proporcionado cuyo valor o formato no sea válido. Cuando la respuesta contenga un error de validación de un campo, deberá identificar el campo afectado y explicar la causa del error.
- **RF-006: Listado de Usuarios**: Cuando el sistema reciba una solicitud de listado de usuarios, deberá devolver la colección de todos los usuarios existentes sin exponer contraseñas ni hashes.
- **RF-007: Mayoría de Edad**: Cuando el sistema reciba una solicitud de creación o una actualización que incluya la fecha de nacimiento, deberá aceptar la fecha únicamente si la persona tiene al menos 18 años en la fecha de evaluación; en caso contrario deberá rechazar la solicitud e identificar `fechaNacimiento` y el motivo del error.
- **RF-008: Formato de Nombre y Apellidos**: Mientras el sistema valide una solicitud de creación o una actualización que incluya `nombre` o `apellidos`, deberá rechazar el campo si es una cadena vacía, contiene sólo espacios, incluye números o símbolos; cada uno de esos campos sólo podrá contener letras.
- **RF-009: Normalización de Nombre y Apellidos**: Cuando el sistema reciba una solicitud de creación o una actualización que incluya `nombre` o `apellidos`, deberá eliminar los espacios al inicio y al final de cada cadena antes de validarla y persistirla.

## Requisitos No Funcionales (RNF)
- **RNF-001: Seguridad**: Las contraseñas nunca deben almacenarse en texto plano; se deben usar hashes seguros (ej. BCrypt).
- **RNF-002: Disponibilidad**: El sistema debe estar disponible en un entorno contenedorizado (Docker/Kubernetes).
- **RNF-003: Persistencia**: Los datos de usuario deben persistir en una base de datos relacional.
- **RNF-004: Escalabilidad**: La arquitectura debe permitir la gestión de múltiples tablas (ej. departamentos) en el futuro.
- **RNF-005: Interoperabilidad**: La API debe seguir estándares RESTful y devolver códigos de estado HTTP adecuados.

## Criterios de Aceptación
- El usuario puede registrarse y recibir una confirmación con código 201.
- El sistema devuelve un error 400 si falta algún campo obligatorio.
- Cada error de validación identifica el campo incorrecto y el motivo por el que no es válido.
- La contraseña en la base de datos debe ser un hash y no el texto plano.
- Se pueden realizar consultas, actualizaciones y eliminaciones correctamente por ID.
- El sistema devuelve mediante la API los usuarios creados en una solicitud de listado.
- El sistema rechaza con código 400 el alta o la actualización de una fecha de nacimiento que corresponda a una persona menor de 18 años y acepta una persona que cumple 18 años en la fecha de evaluación.
- El sistema rechaza con código 400 un nombre o unos apellidos vacíos, compuestos sólo por espacios o que contengan números o símbolos.
- El sistema devuelve y persiste el nombre y los apellidos sin espacios al inicio ni al final.
