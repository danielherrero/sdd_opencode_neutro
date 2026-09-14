# Nombre: plan.md
# Feature: 004-EMPRESA-SPA
# Rama: 004-EMPRESA-SPA

## Descripcion
Plan tecnico para crear la SPA de `empresa` con Angular 22.1.x standalone y Angular Material, consumiendo las APIs REST de las specs 001, 002 y 003.

## Referencias tecnicas
La decision tecnica se basa en la documentacion oficial consultada mediante Context7:
- Angular 22 es la version estable activa; se fija la linea 22.1.x para esta implementacion.
- Angular standalone bootstrap y `ApplicationConfig`.
- `provideHttpClient` para configurar `HttpClient`.
- Router con `RouterOutlet`, `RouterLink` y rutas lazy mediante `loadComponent`.
- Reactive Forms para validacion y controles select.
- Angular Material Sidenav, Toolbar, Table, Form Field, Select, Dialog y Snackbar.
- El skill `frontend-design` para definir la direccion visual, jerarquia, responsive y accesibilidad de la interfaz.

## Stack
- Angular 22.1.x y Angular CLI 22.1.x, manteniendo alineadas las versiones de `@angular/core` y CLI.
- Docker multistage con Node.js 24.15.0 para compilar y Nginx 1.26 para servir la SPA.
- Node.js `^20.19.0`, `^22.12.0` o `^24.0.0`, TypeScript `>=6.0.0 <6.1.0` y RxJS `^6.5.3` o `^7.4.0`, conforme a la matriz de compatibilidad de Angular 22.
- TypeScript y componentes standalone.
- Angular Router.
- `HttpClient` con interceptores para logging controlado y manejo de errores.
- Angular Material y CDK.
- Reactive Forms.
- Tests con el runner y herramientas recomendadas por la version de Angular seleccionada.

## Estructura propuesta
```text
src/app/
├── core/
│   ├── api/
│   ├── interceptors/
│   ├── models/
│   └── services/
├── layout/
│   ├── app-shell/
│   ├── side-navigation/
│   └── toolbar/
├── features/
│   ├── users/
│   │   ├── user-list/
│   │   ├── user-form/
│   │   └── user-detail/
│   ├── departments/
│   │   ├── department-list/
│   │   ├── department-form/
│   │   └── department-detail/
│   └── user-departments/
│       ├── association-list/
│       ├── association-form/
│       └── association-detail/
├── shared/
│   ├── components/
│   ├── forms/
│   ├── loading/
│   └── errors/
├── app.config.ts
├── app.routes.ts
└── app.component.ts
src/environments/
├── environment.ts
└── environment.prod.ts
Dockerfile
nginx-custom.conf
.dockerignore
```

## Layout y navegacion
El shell utilizara `mat-sidenav-container`, `mat-sidenav` y `mat-sidenav-content`, con toolbar persistente y menu lateral responsive. En movil el sidenav sera `over`; en escritorio se mostrara como `side`. Las rutas principales seran:
- `/users`, `/users/new`, `/users/:id`, `/users/:id/edit`.
- `/departments`, `/departments/new`, `/departments/:id`, `/departments/:id/edit`.
- `/user-departments`, `/user-departments/new`, `/user-departments/:id`, `/user-departments/:id/edit`.

Las vistas de cada feature se cargaran de forma lazy con `loadComponent`.

## Diseno frontend
Antes de implementar los componentes visuales se aplicara el skill `frontend-design` para definir una direccion visual propia, la jerarquia de contenidos, la seleccion tipografica y cromatica, los estados responsive y los criterios de accesibilidad. El resultado se mantendra coherente en el shell, las tablas, los formularios, los mensajes de estado y los dialogos de confirmacion, evitando una interfaz generica basada exclusivamente en valores por defecto de Angular Material.

## Integracion HTTP
Se creara un servicio Angular centralizado y registrado con `providedIn: 'root'` que contendra todas las llamadas al backend para usuarios, departamentos y relaciones. El servicio expondra metodos reutilizables para listar, consultar, crear, modificar y eliminar recursos, con modelos TypeScript alineados con los DTOs REST, y podra ser inyectado desde cualquier componente o servicio Angular. La URL base se definira en `src/environments/environment.ts` para desarrollo local contra Quarkus (`http://localhost:8080`) y en `src/environments/environment.prod.ts` para produccion (`/api`). El build de produccion aplicara `fileReplacements` de Angular CLI; el Ingress, Route o proxy externo publicara `/api` hacia el backend. La configuracion usara `provideHttpClient`. Un interceptor de errores convertira las respuestas comunes en errores de vista; un interceptor de logging sera activable sólo en desarrollo y tests.

El formulario de relaciones cargara usuarios y departamentos mediante los metodos correspondientes del servicio centralizado. Los `mat-select` mostraran nombres y enviaran internamente `userId` y `departmentId` al API. Si una carga falla, el control mostrara un estado de error y no se permitira guardar una relacion incompleta.

## Formularios y tablas
- Usuarios y departamentos tendran formularios reactivos con reglas de campos obligatorios y mensajes del backend.
- Relaciones tendra dos selects obligatorios y no mostrara inputs libres para IDs.
- Las tablas usaran Material Table con filtro, ordenacion y paginacion cuando el volumen lo requiera.
- Las eliminaciones requeriran confirmacion y mostraran `409 Conflict` como error accionable.

## Estados y accesibilidad
Cada vista gestionara carga inicial, carga de guardado, estado vacio, error y exito. Los botones se deshabilitaran durante operaciones pendientes. Se usaran labels asociados, foco visible, `aria-label` cuando sea necesario, mensajes `mat-error` y navegacion por teclado.

## Pruebas
- Tests de componentes del shell y navegacion.
- Tests de servicios HTTP con backend simulado.
- Tests de formularios y validaciones.
- Test de relaciones verificando que los combos cargan usuarios/departamentos y que el payload contiene sus IDs.
- Tests responsive y accesibilidad de los estados principales.
- Tests de logging HTTP verificando metodo, cabeceras, payload, status y respuesta sin secretos reales.
- Test de `RF-013` verificando que la interfaz implementada sigue las decisiones visuales y responsive definidas mediante `frontend-design`.

## Criterios de implementacion
- La SPA arranca con Angular standalone.
- Las tres areas son navegables desde el sidenav.
- Los CRUD consumen los endpoints de las specs 001-003.
- El requisito `RF-014` queda cubierto implementando en la SPA todas las operaciones REST expuestas por el backend.
- Todas las llamadas al backend estan centralizadas en un servicio Angular reutilizable e inyectable.
- La URL del backend se selecciona mediante el environment de Angular correspondiente al modo de ejecucion.
- El build de produccion sustituye `environment.ts` por `environment.prod.ts` y utiliza `/api` como ruta del backend publicada por Nginx.
- La imagen de produccion se construye en dos etapas: Node.js compila `dist/empresa-spa` y Nginx sirve esos archivos en el puerto `80`.
- Nginx sirve la SPA en el puerto `80`, aplica `try_files` para soportar sus rutas y habilita compresion Gzip para los tipos de contenido habituales.
- El enrutamiento de `/api` hacia Quarkus se realizara mediante el Ingress, Route o proxy externo del entorno de despliegue; Nginx solo sirve los artefactos estaticos.
- La imagen final expone el puerto `80`, siguiendo la configuracion Nginx definida para el contenedor.
- Las relaciones usan combos, nunca entrada manual de IDs.
- La interfaz aplica las decisiones de diseno definidas con `frontend-design`.
- `ng test` y `ng build` finalizan correctamente.

## Implementacion realizada
La aplicacion se encuentra en `frontend/` y se ejecuta con `npm install`, `npm start`, `npm test`, `npm run lint` y `npm run build`. Se implemento un shell standalone con Material, rutas lazy y una pagina parametrizada por recurso para mantener consistencia entre usuarios, departamentos y relaciones. El servidor de desarrollo Angular escucha en `http://localhost:4200` y el entorno de desarrollo consume Quarkus en `http://localhost:8081`; el build de produccion reemplaza ese entorno por `environment.prod.ts`, que configura `/api` para el proxy de Nginx hacia el backend. No se incluyen secretos en los entornos versionados.

La imagen se construye desde `frontend/` con `docker build --build-arg configuration=production -t empresa-spa:latest .`. En Kubernetes u OpenShift, el recurso Ingress o Route debera publicar la SPA y dirigir las peticiones `/api` al servicio Quarkus.

La direccion visual aplicada por `frontend-design` usa azul tinta (`#101b2d`), lima electrica (`#b8ef63`) y superficies marfil (`#f3f5f3`), con `Space Grotesk` para la jerarquia y `DM Sans` para lectura. El sidenav se convierte en drawer en pantallas de hasta 800px, los controles tienen foco visible y las vistas exponen estados de carga, vacio, error, exito y confirmacion de eliminacion.

La cobertura inicial trazable se encuentra en `src/app/app.component.spec.ts` (`RF-001`, `RF-011`), `src/app/api.spec.ts` (`RF-003`, `RF-012`) y `src/app/resource.page.spec.ts` (`RF-008`, `RF-009`). La suite ejecutada contiene 3 pruebas exitosas; build y lint tambien finalizan correctamente.
