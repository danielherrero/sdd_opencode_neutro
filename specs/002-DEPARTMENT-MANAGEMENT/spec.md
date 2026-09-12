# Nombre: spec.md
# Feature: 002-DEPARTMENT-MANAGEMENT

## Descripcion
Esta funcionalidad permite gestionar departamentos mediante una interfaz REST. Cada departamento tiene un identificador numerico generado por la base de datos, nombre, descripcion y fecha de creacion.

## Requisitos Funcionales (RF)
- **RF-001: Creacion de Departamento**: Cuando el sistema reciba una solicitud valida de creacion con nombre y, opcionalmente, descripcion y fecha de creacion, debera crear el departamento y generar automaticamente su identificador numerico unico. Si no se envia la fecha de creacion, debera utilizar la fecha del sistema.
- **RF-002: Obtencion de Departamento**: Cuando el sistema reciba una solicitud de consulta con el identificador unico de un departamento existente, debera devolver sus detalles. Si el identificador no existe, debera devolver un error indicando que el departamento no existe.
- **RF-003: Actualizacion de Departamento**: Cuando el sistema reciba una solicitud de actualizacion con el identificador de un departamento existente, debera modificar exclusivamente los campos proporcionados entre nombre, descripcion y fecha de creacion. Cada campo proporcionado debera superar sus validaciones. Si el identificador no existe, debera rechazar la actualizacion e informar del motivo.
- **RF-004: Eliminacion de Departamento**: Cuando el sistema reciba una solicitud de eliminacion con el identificador de un departamento existente, debera eliminarlo fisicamente. Si el identificador no existe, debera rechazar la eliminacion e indicar que el departamento no existe.
- **RF-005: Validacion de Campos**: Mientras el sistema valide una solicitud de creacion, si falta el nombre debera rechazarla. Mientras valide una actualizacion, debera permitir la ausencia de los campos opcionales y rechazar cada campo proporcionado cuyo valor no sea valido. Los errores deberan identificar el campo afectado y explicar el motivo.
- **RF-006: Listado de Departamentos**: Cuando el sistema reciba una solicitud de listado de departamentos, debera devolver la coleccion de todos los departamentos existentes.
- **RF-007: Fecha de Creacion Predeterminada**: Cuando el sistema reciba una solicitud de creacion sin fecha de creacion, debera asignar la fecha del sistema antes de persistir el departamento.
- **RF-008: Normalizacion de Texto**: Cuando el sistema reciba una solicitud de creacion o actualizacion que incluya nombre o descripcion, debera eliminar los espacios al inicio y al final antes de validar y persistir dichos campos. Se permitiran letras, numeros, espacios internos y caracteres especiales.

## Requisitos No Funcionales (RNF)
- **RNF-001: Persistencia**: Los departamentos deberan persistir en una tabla propia `departments` de una base de datos relacional.
- **RNF-002: Interoperabilidad**: La API debera seguir los estandares REST y utilizar codigos HTTP adecuados.
- **RNF-003: Separacion de Datos**: La API utilizara DTOs y no expondra directamente la entidad JPA.
- **RNF-004: Trazabilidad HTTP en Pruebas**: Durante la ejecucion de las pruebas REST, se debera imprimir la peticion y la respuesta completas, incluyendo metodo HTTP, URI, cabeceras, codigo de estado y payload cuando exista.

## Criterios de Aceptacion
- El alta valida devuelve `201 Created` con identificador generado.
- El nombre es obligatorio y se rechaza ausente, nulo o vacio.
- La fecha se asigna automaticamente cuando no se envia.
- Nombre y descripcion se devuelven sin espacios exteriores.
- Se pueden realizar consultas, actualizaciones, eliminaciones y listados por API.
- Cada error de validacion identifica el campo y el motivo.
- Las pruebas REST imprimen el metodo, URI, cabeceras, codigo de estado y payloads de peticion y respuesta.
