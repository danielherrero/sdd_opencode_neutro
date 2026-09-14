# Nombre: constitution.md

## Descripcion del archivo
En este archivo se especifican las reglas de obligado cumplimiento para todo el proyecto y para todas sus funcionalidades.

## Reglas
* ** Gestiónado por GIT **: El proyecto se debe gestionar con un repositorio GIT
* ** Aislamiento de features **: Cada feature se desarrollara de forma independiente a las demás, sin tocar su código, utilizando librerías o cualquier otro mecanismo que asegure que cambios en una funcionalidad no rompan otra. 
* ** Cada feature se desarrolla en su propia rama GIT**: Cada feature exige ser desarrollada en su propia rama GIT
* ** No inventar nada **: El agente no debe inventar nada. Cada duda que tenga se lo preguntará al usuario
* ** Basado en tests (TDD) **: La primera tarea de cada especificación será generar casos de tests, tanto happy cases como unhappy cases, así como casos límite. Si un test no se pasa, no se continúa con el siguiente
* ** Trazabilidad y cobertura de requisitos **: Cada test debe indicar explícitamente qué requisito funcional `RF-` evalúa, y todo requisito funcional debe estar probado por al menos un caso de prueba
* ** Idioma **: Todos los mensajes al usuario se darán en castellano
* ** El agente puede consultar en internet todo lo que considere necesario
* ** Cualquier cambio en código debe quedar reflejado en su spec
* ** Sincronizacion de requisitos y pruebas **: Siempre que se modifique un requisito funcional en `spec.md`, se actualizaran en consecuencia `plan.md` y `tasks.md`, y se crearan o actualizaran los casos de prueba necesarios antes de dar el cambio por terminado.
* ** Cada spec debe identificar los requisitos funcionales, que se definen como RF-<número secuencia>-breve descripción
* ** Formato EARS para requisitos **: Los requisitos funcionales de todas las specs deben redactarse utilizando el formato EARS (Easy Approach to Requirements Syntax)
* ** Arquitectura por Capas **: El backend debe seguir una estructura clara de capas (Controller, Service, Repository, DTO, Entity) para asegurar la separación de responsabilidades.
* ** Manejo de Excepciones Global **: Todas las excepciones deben ser capturadas de forma centralizada para devolver respuestas REST consistentes.
* ** Validación de Datos **: Es obligatorio validar los datos a nivel de DTO antes de que lleguen a la lógica de negocio.
* ** Seguridad y Hashing **: Queda prohibido almacenar contraseñas en texto plano; se debe usar un algoritmo de hashing seguro (ej. BCrypt).
* ** Estándares de API REST **: Se deben utilizar correctamente los códigos de estado HTTP (200, 201, 400, 404, 500) y un formato de respuesta JSON estándar.
* ** Contratos de API **: Cada plan.md debe definir el contrato de la API antes de implementar el backend para facilitar el desarrollo paralelo del frontend.
* ** Desacoplamiento de Datos **: Se deben utilizar DTOs para la comunicación externa, evitando exponer directamente las entidades de la base de datos.
* ** Calidad de Código **: Se debe mantener la consistencia en la nomenclatura y documentar las funciones críticas con Javadoc.\n* ** Entornos de ejecución **: Tanto el frontend web como el backend podrán ejecutarse de forma nativa en un host, o bien en un contenedor Docker o bien en un pod de Kubernetes.
