#!/usr/bin/env sh
# Usage: ./start-local.sh
set -eu

PROJECT_ROOT=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)

if [ -z "${DB_PASSWORD:-}" ] && [ -f "$PROJECT_ROOT/.env" ]; then
  set -a
  # .env solo debe contener asignaciones locales de variables de entorno.
  . "$PROJECT_ROOT/.env"
  set +a
fi

: "${DB_PASSWORD:?DB_PASSWORD debe estar definida para ejecutar el entorno local}"

if ! command -v node >/dev/null 2>&1 || ! command -v npm >/dev/null 2>&1; then
  echo "Node.js y npm son necesarios para ejecutar la aplicacion Angular." >&2
  exit 1
fi

if [ ! -d "$PROJECT_ROOT/frontend/node_modules" ]; then
  echo "No se encontraron las dependencias Angular. Ejecuta 'npm install' en frontend." >&2
  exit 1
fi

# Verificar si el contenedor de postgres está corriendo
if ! docker compose --project-directory "$PROJECT_ROOT" ps postgres | grep -q "running"; then
  echo "Levantando contenedor de postgresql..."
  docker compose --project-directory "$PROJECT_ROOT" up -d --wait postgres
fi

cleanup() {
  if [ -n "${FRONTEND_PID:-}" ] && kill -0 "$FRONTEND_PID" 2>/dev/null; then
    kill "$FRONTEND_PID" 2>/dev/null || true
    wait "$FRONTEND_PID" 2>/dev/null || true
  fi
}

trap cleanup EXIT

npm --prefix "$PROJECT_ROOT/frontend" start &
FRONTEND_PID=$!

cd "$PROJECT_ROOT"
./mvnw --batch-mode quarkus:dev -Dquarkus.profile=dev

