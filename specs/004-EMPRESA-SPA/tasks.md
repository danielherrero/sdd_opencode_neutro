# Nombre: tasks.md
# Feature: 004-EMPRESA-SPA
# Rama: 004-EMPRESA-SPA

La implementacion seguira TDD. Cada prueba identificara el requisito `RF-` evaluado.

## 1. Preparacion Angular
- [x] T401 Crear la aplicacion Angular standalone con Angular 22.1.x y Angular CLI 22.1.x.
- [x] T402 Configurar TypeScript, linting y formateo.
- [x] T403 Configurar `ApplicationConfig` y `provideHttpClient`.
- [x] T404 Configurar Angular Router y rutas lazy.
- [x] T405 Instalar y configurar Angular Material y CDK.
- [x] T406 Configurar entorno de API sin versionar secretos.
- [x] T406a Crear `environment.ts` para desarrollo local y `environment.prod.ts` para la URL de produccion.
- [x] T406b Configurar `fileReplacements` de Angular CLI para seleccionar el environment de produccion y documentar el enrutamiento externo de `/api` hacia Quarkus.
- [x] T406c Crear el `Dockerfile` multistage del frontend con Node.js para compilar y Nginx para servir la SPA.
- [x] T406d Crear la configuracion de Nginx con fallback de rutas Angular, compresion Gzip y escucha en el puerto `80`.
- [x] T406e Configurar la imagen final para exponer el puerto `80` y servir la SPA con Nginx.

## 2. Shell y navegacion
- [x] T407 Crear shell con sidenav, toolbar y contenido principal.
- [x] T408 Crear menu lateral con enlaces a usuarios, departamentos y relaciones.
- [x] T409 Crear rutas de listado, alta, detalle y edicion de usuarios.
- [x] T410 Crear rutas de listado, alta, detalle y edicion de departamentos.
- [x] T411 Crear rutas de listado, alta, detalle y edicion de relaciones.
- [x] T412 Adaptar sidenav y contenido a movil y escritorio.
- [x] T412a Aplicar el skill `frontend-design` y documentar la direccion visual, jerarquia, tipografia, color, responsive y accesibilidad.

## 3. Usuarios
- [x] T413 Crear modelos TypeScript de usuario y errores.
- [x] T414 Crear servicio HTTP de usuarios.
- [x] T415 Crear tabla/listado de usuarios.
- [x] T416 Crear formulario de alta de usuario.
- [x] T417 Crear detalle y formulario de modificacion de usuario.
- [x] T418 Implementar eliminacion con confirmacion y errores.

## 4. Departamentos
- [x] T419 Crear modelos TypeScript de departamento.
- [x] T420 Crear servicio HTTP de departamentos.
- [x] T421 Crear tabla/listado de departamentos.
- [x] T422 Crear formulario de alta de departamento.
- [x] T423 Crear detalle y formulario de modificacion de departamento.
- [x] T424 Implementar eliminacion con confirmacion y errores.

## 5. Relaciones usuario-departamento
- [x] T425 Crear modelos TypeScript de asociacion.
- [x] T426 Crear servicio HTTP de relaciones.
- [x] T427 Cargar usuarios y departamentos para los combos.
- [x] T428 Crear formulario de alta con `mat-select` de usuarios y departamentos.
- [x] T429 Crear formulario de modificacion con combos y fecha inmutable.
- [x] T430 Crear listado y detalle de relaciones.
- [x] T431 Implementar eliminacion con confirmacion y errores.
- [x] T432 Verificar que nunca se solicitan IDs manualmente.

## 6. Validacion, estados y accesibilidad
- [x] T433 Mostrar errores de validacion por campo.
- [x] T434 Implementar estados de carga, vacio, error y exito.
- [x] T435 Deshabilitar acciones duplicadas durante peticiones.
- [x] T436 Añadir labels, foco visible, mensajes accesibles y navegacion por teclado.
- [x] T437 Implementar logging HTTP de desarrollo y tests sin secretos.
- [ ] T437a Centralizar todas las llamadas al backend en un servicio Angular reutilizable e inyectable desde cualquier componente.
- [ ] T437b Implementar todas las llamadas REST requeridas por `RF-014` en la SPA y centralizarlas en el servicio Angular reutilizable.

## 7. Tests RF-001 a RF-014 y RNF-006
- [x] T438 Test `RF-001` de shell y navegacion principal.
- [x] T439 Test `RF-002` de navegacion CRUD de usuarios.
- [x] T440 Test `RF-003` de gestion de usuarios y estados HTTP.
- [x] T441 Test `RF-004` de navegacion CRUD de departamentos.
- [x] T442 Test `RF-005` de gestion de departamentos y estados HTTP.
- [x] T443 Test `RF-006` de navegacion de relaciones.
- [x] T444 Test `RF-007` de CRUD de relaciones.
- [x] T445 Test `RF-008` de carga de combos y payload con IDs seleccionados.
- [x] T446 Test `RF-009` de validacion y errores por campo.
- [x] T447 Test `RF-010` de estados de carga, exito y error.
- [x] T448 Test `RF-011` de responsive y accesibilidad.
- [x] T449 Test `RF-012` de logging HTTP completo sin secretos reales.
- [x] T449a Test `RF-013` de cumplimiento de las decisiones visuales y responsive definidas con `frontend-design`.
- [ ] T449b Test `RNF-006` verificando que todas las operaciones REST se exponen mediante el servicio Angular centralizado.
- [ ] T449c Test `RF-014` verificando las llamadas REST de usuarios, departamentos y relaciones, incluyendo sus respuestas y errores.
- [x] T449d Test de configuracion verificando que el servicio usa la URL definida por el environment activo.
- [ ] T449e Verificar la construccion de la imagen Docker multistage y la publicacion de la SPA mediante Nginx.

## 8. Verificacion
- [x] T450 Ejecutar todos los tests frontend.
- [x] T451 Ejecutar build de produccion.
- [x] T452 Verificar trazabilidad entre `spec.md`, `plan.md` y tests.
- [x] T453 Actualizar la documentacion si cambia el comportamiento.
- [x] T454 Actualizar los scripts de arranque local para iniciar Angular junto con Quarkus y limpiar el proceso frontend al finalizar.
- [x] T455 Configurar el entorno Angular de desarrollo para consumir Quarkus en el puerto local `8081`, separado del servidor Angular en `4200`.
