#!/usr/bin/env sh
set -eu

BASE_URL=${BASE_URL:-http://localhost:8081}

command -v curl >/dev/null 2>&1 || { printf 'Error: curl es necesario.\n' >&2; exit 1; }
command -v jq >/dev/null 2>&1 || { printf 'Error: jq es necesario para extraer los identificadores.\n' >&2; exit 1; }

create_user() {
  curl --silent --show-error --fail-with-body \
    --request POST "$BASE_URL/users" \
    --header 'Content-Type: application/json' \
    --data "$1"
}

create_department() {
  curl --silent --show-error --fail-with-body \
    --request POST "$BASE_URL/departments" \
    --header 'Content-Type: application/json' \
    --data "$1"
}

printf 'Creando usuarios...\n'
USER_1=$(create_user '{"nombre":"Ana","apellidos":"Garcia","fechaNacimiento":"1988-02-14","contrasena":"DemoPassword1"}' | jq -r '.id')
USER_2=$(create_user '{"nombre":"Luis","apellidos":"Martin","fechaNacimiento":"1990-06-22","contrasena":"DemoPassword2"}' | jq -r '.id')
USER_3=$(create_user '{"nombre":"Marta","apellidos":"Lopez","fechaNacimiento":"1985-11-03","contrasena":"DemoPassword3"}' | jq -r '.id')
USER_4=$(create_user '{"nombre":"Pablo","apellidos":"Sanchez","fechaNacimiento":"1992-09-18","contrasena":"DemoPassword4"}' | jq -r '.id')
USER_5=$(create_user '{"nombre":"Elena","apellidos":"Ruiz","fechaNacimiento":"1987-12-27","contrasena":"DemoPassword5"}' | jq -r '.id')

printf 'Creando departamentos...\n'
DEPARTMENT_1=$(create_department '{"nombre":"Tecnologia","descripcion":"Desarrollo y mantenimiento de aplicaciones"}' | jq -r '.id')
DEPARTMENT_2=$(create_department '{"nombre":"Recursos Humanos","descripcion":"Gestion de personas y talento"}' | jq -r '.id')
DEPARTMENT_3=$(create_department '{"nombre":"Finanzas","descripcion":"Gestion financiera y contable"}' | jq -r '.id')

create_relation() {
  curl --silent --show-error --fail-with-body \
    --request POST "$BASE_URL/user-departments" \
    --header 'Content-Type: application/json' \
    --data "{\"userId\":$1,\"departmentId\":$2}" >/dev/null
}

printf 'Creando relaciones...\n'
create_relation "$USER_1" "$DEPARTMENT_1"
create_relation "$USER_2" "$DEPARTMENT_2"
create_relation "$USER_3" "$DEPARTMENT_3"
create_relation "$USER_4" "$DEPARTMENT_1"
create_relation "$USER_5" "$DEPARTMENT_2"

printf 'Datos iniciales creados correctamente.\n'
printf 'Usuarios: %s, %s, %s, %s, %s\n' "$USER_1" "$USER_2" "$USER_3" "$USER_4" "$USER_5"
printf 'Departamentos: %s, %s, %s\n' "$DEPARTMENT_1" "$DEPARTMENT_2" "$DEPARTMENT_3"
