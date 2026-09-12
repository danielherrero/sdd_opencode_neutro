# Nombre: tasks.md
# Feature: 004-EMPRESA-SPA
# Rama: 004-EMPRESA-SPA

La implementacion seguira TDD. Cada prueba identificara el requisito `RF-` evaluado.

## 1. Preparacion Angular
- [ ] T401 Crear la aplicacion Angular standalone en la ultima version estable.
- [ ] T402 Configurar TypeScript, linting y formateo.
- [ ] T403 Configurar `ApplicationConfig` y `provideHttpClient`.
- [ ] T404 Configurar Angular Router y rutas lazy.
- [ ] T405 Instalar y configurar Angular Material y CDK.
- [ ] T406 Configurar entorno de API sin versionar secretos.

## 2. Shell y navegacion
- [ ] T407 Crear shell con sidenav, toolbar y contenido principal.
- [ ] T408 Crear menu lateral con enlaces a usuarios, departamentos y relaciones.
- [ ] T409 Crear rutas de listado, alta, detalle y edicion de usuarios.
- [ ] T410 Crear rutas de listado, alta, detalle y edicion de departamentos.
- [ ] T411 Crear rutas de listado, alta, detalle y edicion de relaciones.
- [ ] T412 Adaptar sidenav y contenido a movil y escritorio.

## 3. Usuarios
- [ ] T413 Crear modelos TypeScript de usuario y errores.
- [ ] T414 Crear servicio HTTP de usuarios.
- [ ] T415 Crear tabla/listado de usuarios.
- [ ] T416 Crear formulario de alta de usuario.
- [ ] T417 Crear detalle y formulario de modificacion de usuario.
- [ ] T418 Implementar eliminacion con confirmacion y errores.

## 4. Departamentos
- [ ] T419 Crear modelos TypeScript de departamento.
- [ ] T420 Crear servicio HTTP de departamentos.
- [ ] T421 Crear tabla/listado de departamentos.
- [ ] T422 Crear formulario de alta de departamento.
- [ ] T423 Crear detalle y formulario de modificacion de departamento.
- [ ] T424 Implementar eliminacion con confirmacion y errores.

## 5. Relaciones usuario-departamento
- [ ] T425 Crear modelos TypeScript de asociacion.
- [ ] T426 Crear servicio HTTP de relaciones.
- [ ] T427 Cargar usuarios y departamentos para los combos.
- [ ] T428 Crear formulario de alta con `mat-select` de usuarios y departamentos.
- [ ] T429 Crear formulario de modificacion con combos y fecha inmutable.
- [ ] T430 Crear listado y detalle de relaciones.
- [ ] T431 Implementar eliminacion con confirmacion y errores.
- [ ] T432 Verificar que nunca se solicitan IDs manualmente.

## 6. Validacion, estados y accesibilidad
- [ ] T433 Mostrar errores de validacion por campo.
- [ ] T434 Implementar estados de carga, vacio, error y exito.
- [ ] T435 Deshabilitar acciones duplicadas durante peticiones.
- [ ] T436 Añadir labels, foco visible, mensajes accesibles y navegacion por teclado.
- [ ] T437 Implementar logging HTTP de desarrollo y tests sin secretos.

## 7. Tests RF-001 a RF-012
- [ ] T438 Test `RF-001` de shell y navegacion principal.
- [ ] T439 Test `RF-002` de navegacion CRUD de usuarios.
- [ ] T440 Test `RF-003` de gestion de usuarios y estados HTTP.
- [ ] T441 Test `RF-004` de navegacion CRUD de departamentos.
- [ ] T442 Test `RF-005` de gestion de departamentos y estados HTTP.
- [ ] T443 Test `RF-006` de navegacion de relaciones.
- [ ] T444 Test `RF-007` de CRUD de relaciones.
- [ ] T445 Test `RF-008` de carga de combos y payload con IDs seleccionados.
- [ ] T446 Test `RF-009` de validacion y errores por campo.
- [ ] T447 Test `RF-010` de estados de carga, exito y error.
- [ ] T448 Test `RF-011` de responsive y accesibilidad.
- [ ] T449 Test `RF-012` de logging HTTP completo sin secretos reales.

## 8. Verificacion
- [ ] T450 Ejecutar todos los tests frontend.
- [ ] T451 Ejecutar build de produccion.
- [ ] T452 Verificar trazabilidad entre `spec.md`, `plan.md` y tests.
- [ ] T453 Actualizar la documentacion si cambia el comportamiento.
