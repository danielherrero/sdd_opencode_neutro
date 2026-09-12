# Nombre: plan.md
# Feature: 004-EMPRESA-SPA
# Rama: 004-EMPRESA-SPA

## Descripcion
Plan tecnico para crear la SPA de `empresa` con Angular standalone y Angular Material, consumiendo las APIs REST de las specs 001, 002 y 003.

## Referencias tecnicas
La decision tecnica se basa en la documentacion oficial consultada mediante Context7:
- Angular standalone bootstrap y `ApplicationConfig`.
- `provideHttpClient` para configurar `HttpClient`.
- Router con `RouterOutlet`, `RouterLink` y rutas lazy mediante `loadComponent`.
- Reactive Forms para validacion y controles select.
- Angular Material Sidenav, Toolbar, Table, Form Field, Select, Dialog y Snackbar.

## Stack
- Angular en la ultima version estable disponible al iniciar la implementacion.
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
```

## Layout y navegacion
El shell utilizara `mat-sidenav-container`, `mat-sidenav` y `mat-sidenav-content`, con toolbar persistente y menu lateral responsive. En movil el sidenav sera `over`; en escritorio se mostrara como `side`. Las rutas principales seran:
- `/users`, `/users/new`, `/users/:id`, `/users/:id/edit`.
- `/departments`, `/departments/new`, `/departments/:id`, `/departments/:id/edit`.
- `/user-departments`, `/user-departments/new`, `/user-departments/:id`, `/user-departments/:id/edit`.

Las vistas de cada feature se cargaran de forma lazy con `loadComponent`.

## Integracion HTTP
Se crearan servicios `UserApi`, `DepartmentApi` y `UserDepartmentApi` con modelos TypeScript alineados con los DTOs REST. La configuracion usara `provideHttpClient`. Un interceptor de errores convertira las respuestas comunes en errores de vista; un interceptor de logging sera activable sólo en desarrollo y tests.

El formulario de relaciones cargara usuarios mediante `UserApi.list()` y departamentos mediante `DepartmentApi.list()`. Los `mat-select` mostraran nombres y enviaran internamente `userId` y `departmentId` al API. Si una carga falla, el control mostrara un estado de error y no se permitira guardar una relacion incompleta.

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

## Criterios de implementacion
- La SPA arranca con Angular standalone.
- Las tres areas son navegables desde el sidenav.
- Los CRUD consumen los endpoints de las specs 001-003.
- Las relaciones usan combos, nunca entrada manual de IDs.
- `ng test` y `ng build` finalizan correctamente.
