# Nombre: spec.md
# Feature: 004-EMPRESA-SPA
# Rama: 004-EMPRESA-SPA

## Descripcion
Esta funcionalidad define una aplicacion web SPA para `empresa`, desarrollada con la ultima version estable de Angular. La aplicacion ofrecera una interfaz responsive con un menu lateral izquierdo para gestionar usuarios, departamentos y asociaciones entre usuarios y departamentos.

## Requisitos Funcionales (RF)
- **RF-001: Shell de Aplicacion**: Cuando el usuario acceda a la SPA, el sistema debera mostrar una estructura comun con cabecera, menu lateral izquierdo, area principal de contenido y navegacion entre las secciones disponibles.
- **RF-002: Navegacion de Usuarios**: Cuando el usuario seleccione la seccion de usuarios, la SPA debera permitir acceder al listado, alta, detalle y modificacion de usuarios mediante rutas diferenciadas.
- **RF-003: Gestion de Usuarios**: Cuando el usuario utilice las vistas de usuarios, la SPA debera permitir listar, crear, consultar, modificar y eliminar usuarios consumiendo la API REST de usuarios y mostrando sus estados de carga, exito y error.
- **RF-004: Navegacion de Departamentos**: Cuando el usuario seleccione la seccion de departamentos, la SPA debera permitir acceder al listado, alta, detalle y modificacion de departamentos mediante rutas diferenciadas.
- **RF-005: Gestion de Departamentos**: Cuando el usuario utilice las vistas de departamentos, la SPA debera permitir listar, crear, consultar, modificar y eliminar departamentos consumiendo la API REST de departamentos y mostrando sus estados de carga, exito y error.
- **RF-006: Navegacion de Relaciones**: Cuando el usuario seleccione la seccion de relaciones, la SPA debera mostrar la gestion de asociaciones entre usuarios y departamentos.
- **RF-007: Gestion de Relaciones**: Cuando el usuario utilice las vistas de relaciones, la SPA debera permitir listar, crear, consultar, modificar y eliminar asociaciones mediante la API REST `/user-departments`.
- **RF-008: Seleccion de Usuario y Departamento**: Cuando el usuario cree o modifique una relacion, la SPA debera mostrar combos cargados desde las APIs de usuarios y departamentos en lugar de solicitar la introduccion manual de sus identificadores.
- **RF-009: Validacion de Formularios**: Mientras el usuario complete un formulario, la SPA debera validar los campos obligatorios y mostrar el campo afectado y el motivo del error devuelto por la API.
- **RF-010: Estados de Interaccion**: Cuando una operacion REST este en curso, la SPA debera indicar carga y evitar acciones duplicadas. Cuando termine, debera mostrar confirmacion o error sin perder el contexto de la vista.
- **RF-011: Responsive y Accesibilidad**: Cuando la SPA se visualice en escritorio, tablet o movil, debera adaptar el menu lateral y el contenido manteniendo navegacion por teclado, etiquetas accesibles y contraste suficiente.
- **RF-012: Trazabilidad HTTP en Desarrollo**: Durante las pruebas frontend, las peticiones HTTP deberan poder inspeccionarse con metodo, URI, cabeceras, payload, status y payload de respuesta sin exponer secretos reales.
- **RF-013: Aplicacion del Skill de Diseno Frontend**: Cuando se diseñe o implemente la interfaz de la SPA, el equipo debera utilizar el skill `frontend-design` para definir y aplicar una interfaz distintiva, responsive y coherente con los requisitos de accesibilidad.
- **RF-014: Implementacion de Operaciones del Backend**: Cuando el backend exponga una operacion REST para usuarios, departamentos o relaciones entre usuarios y departamentos, la SPA debera implementar la llamada correspondiente y gestionar sus peticiones, respuestas y errores conforme al contrato REST definido.

## Requisitos No Funcionales (RNF)
- **RNF-001: Tecnologia**: La SPA utilizara la ultima version estable de Angular disponible al iniciar la implementacion.
- **RNF-002: Arquitectura**: Se utilizara Angular standalone, rutas lazy, servicios por recurso y componentes presentacionales reutilizables.
- **RNF-003: Integracion**: La SPA consumira exclusivamente los contratos REST existentes de usuarios, departamentos y relaciones.
- **RNF-004: Seguridad**: No se almacenaran credenciales ni secretos en el codigo fuente ni en la configuracion versionada.
- **RNF-005: Mantenibilidad**: Los formularios, tablas, estados de carga y errores compartiran patrones consistentes.
- **RNF-006: Servicio Centralizado de API**: La SPA debera disponer de un servicio Angular reutilizable que contenga todas las llamadas al backend y pueda ser inyectado y utilizado desde cualquier componente Angular.
- **RNF-007: Configuracion por Entorno**: La SPA debera utilizar un archivo de entorno para conectarse al backend Quarkus durante el desarrollo local y otro archivo de entorno para configurar la URL del backend cuando se empaquete para produccion dentro de un pod de Nginx.
- **RNF-008: Imagen Multistage del Frontend**: La SPA debera empaquetarse mediante una imagen Docker multistage que compile la aplicacion con Node.js y sirva los artefactos compilados con Nginx en una imagen final apta para ejecutarse como pod.

## Criterios de Aceptacion
- El menu lateral permite navegar a Usuarios, Departamentos y Relaciones.
- Cada recurso dispone de listado, alta, detalle, modificacion y eliminacion.
- El formulario de relaciones usa combos de usuarios y departamentos cargados desde API.
- Los errores REST por campo se muestran junto al control correspondiente.
- La SPA implementa todas las operaciones REST expuestas por el backend para usuarios, departamentos y relaciones.
- Todas las llamadas al backend se realizan a traves de un servicio Angular centralizado y reutilizable.
- El entorno de desarrollo apunta al backend Quarkus local y el entorno de produccion permite configurar la URL del backend publicada por Nginx.
- La imagen Docker del frontend compila la SPA en una etapa Node.js y sirve los artefactos con Nginx; el enrutamiento de `/api` hacia Quarkus lo proporciona el Ingress, Route o proxy externo.
- La interfaz funciona en escritorio y movil.
- Las pruebas frontend cubren navegacion, formularios, combos y llamadas HTTP.
