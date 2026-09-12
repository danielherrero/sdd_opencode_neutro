#!/usr/bin/env sh
set -eu

: "${DB_PASSWORD:?DB_PASSWORD debe estar definida para ejecutar el perfil dev}"

PROJECT_ROOT=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
cd "$PROJECT_ROOT"

exec ./mvnw --batch-mode quarkus:dev -Dquarkus.profile=dev
