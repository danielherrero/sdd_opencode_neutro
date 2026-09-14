# Ejemplos de llamadas REST con curl

Estos ejemplos suponen que Quarkus esta ejecutandose en local con el puerto `8081` y que PostgreSQL esta disponible en `localhost:5432`.

Define la URL base antes de ejecutar las llamadas:

```bash
BASE_URL=http://localhost:8081
```

Las variables `USER_ID`, `DEPARTMENT_ID` y `RELATION_ID` deben sustituirse por los identificadores devueltos por las operaciones de alta.

## Usuarios

### Crear un usuario

Todos los campos son obligatorios. La fecha debe corresponder a una persona
mayor de edad y tener formato `YYYY-MM-DD`.

```bash
curl --request POST "$BASE_URL/users" \
  --header "Content-Type: application/json" \
  --data '{
    "nombre": "Daniel",
    "apellidos": "Herrero",
    "fechaNacimiento": "1990-05-15",
    "contrasena": "PasswordDeDemo123"
  }'
```

### Listar usuarios

```bash
curl --request GET "$BASE_URL/users"
```

### Consultar un usuario

```bash
USER_ID=1
curl --request GET "$BASE_URL/users/$USER_ID"
```

La respuesta publica no incluye la contrasena ni su hash.

### Modificar un usuario

La modificacion es parcial. Solo se modifican los campos incluidos en el
payload.

```bash
USER_ID=1
curl --request PUT "$BASE_URL/users/$USER_ID" \
  --header "Content-Type: application/json" \
  --data '{
    "apellidos": "Herrero Garcia",
    "contrasena": "NuevaPasswordDeDemo123"
  }'
```

### Eliminar un usuario

```bash
USER_ID=1
curl --request DELETE "$BASE_URL/users/$USER_ID"
```

La respuesta correcta es `204 No Content`. Si el usuario tiene relaciones,
el backend devuelve `409 Conflict`.

## Departamentos

### Crear un departamento

`nombre` es obligatorio. `descripcion` y `fechaCreacion` son opcionales. Si no
se envia `fechaCreacion`, el backend utiliza la fecha actual.

```bash
curl --request POST "$BASE_URL/departments" \
  --header "Content-Type: application/json" \
  --data '{
    "nombre": "Tecnologia",
    "descripcion": "Departamento de desarrollo de software"
  }'
```

Tambien se puede enviar una fecha explicita:

```bash
curl --request POST "$BASE_URL/departments" \
  --header "Content-Type: application/json" \
  --data '{
    "nombre": "Finanzas",
    "descripcion": "Gestion financiera",
    "fechaCreacion": "2026-09-13"
  }'
```

### Listar departamentos

```bash
curl --request GET "$BASE_URL/departments"
```

### Consultar un departamento

```bash
DEPARTMENT_ID=1
curl --request GET "$BASE_URL/departments/$DEPARTMENT_ID"
```

### Modificar un departamento

La modificacion es parcial.

```bash
DEPARTMENT_ID=1
curl --request PUT "$BASE_URL/departments/$DEPARTMENT_ID" \
  --header "Content-Type: application/json" \
  --data '{
    "nombre": "Ingenieria",
    "descripcion": "Desarrollo y mantenimiento de aplicaciones"
  }'
```

### Eliminar un departamento

```bash
DEPARTMENT_ID=1
curl --request DELETE "$BASE_URL/departments/$DEPARTMENT_ID"
```

La respuesta correcta es `204 No Content`. Si el departamento tiene
relaciones, el backend devuelve `409 Conflict`.

## Relaciones usuario-departamento

Las relaciones utilizan los identificadores existentes de un usuario y un
departamento. `fechaCreacion` se asigna automaticamente y no se envia en las
peticiones de alta o modificacion.

### Crear una relacion

```bash
USER_ID=1
DEPARTMENT_ID=1
curl --request POST "$BASE_URL/user-departments" \
  --header "Content-Type: application/json" \
  --data "{\"userId\":$USER_ID,\"departmentId\":$DEPARTMENT_ID}"
```

### Listar relaciones

```bash
curl --request GET "$BASE_URL/user-departments"
```

### Consultar una relacion

```bash
RELATION_ID=1
curl --request GET "$BASE_URL/user-departments/$RELATION_ID"
```

### Modificar una relacion

Los identificadores son opcionales individualmente. Si se incluye uno, debe
corresponder a una entidad existente.

```bash
RELATION_ID=1
DEPARTMENT_ID=2
curl --request PUT "$BASE_URL/user-departments/$RELATION_ID" \
  --header "Content-Type: application/json" \
  --data "{\"departmentId\":$DEPARTMENT_ID}"
```

### Eliminar una relacion

```bash
RELATION_ID=1
curl --request DELETE "$BASE_URL/user-departments/$RELATION_ID"
```

La respuesta correcta es `204 No Content`.

## Flujo completo de ejemplo

Este flujo crea un usuario, un departamento y su relacion. Requiere `jq` para
extraer los identificadores de las respuestas JSON.

```bash
BASE_URL=http://localhost:8081

USER_ID=$(curl --silent --show-error --fail-with-body \
  --request POST "$BASE_URL/users" \
  --header "Content-Type: application/json" \
  --data '{
    "nombre": "Ana",
    "apellidos": "Garcia",
    "fechaNacimiento": "1988-10-20",
    "contrasena": "PasswordDeDemo123"
  }' | jq -r '.id')

DEPARTMENT_ID=$(curl --silent --show-error --fail-with-body \
  --request POST "$BASE_URL/departments" \
  --header "Content-Type: application/json" \
  --data '{
    "nombre": "Calidad",
    "descripcion": "Pruebas y calidad de software"
  }' | jq -r '.id')

curl --request POST "$BASE_URL/user-departments" \
  --header "Content-Type: application/json" \
  --data "{\"userId\":$USER_ID,\"departmentId\":$DEPARTMENT_ID}"
```

## Errores habituales

Para ver tambien el codigo HTTP en una llamada:

```bash
curl --include --request GET "$BASE_URL/users/999999999"
```

Codigos habituales:

- `200 OK`: consulta o modificacion correcta.
- `201 Created`: alta correcta.
- `204 No Content`: eliminacion correcta.
- `400 Bad Request`: payload invalido o campos obligatorios ausentes.
- `404 Not Found`: identificador inexistente.
- `409 Conflict`: eliminacion de un usuario o departamento con relaciones.
