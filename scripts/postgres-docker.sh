#!/usr/bin/env sh
set -eu

project_root=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)

case "${1:-}" in
  start) docker compose --project-directory "$project_root" up -d postgres ;;
  restart) docker compose --project-directory "$project_root" restart postgres ;;
  stop) docker compose --project-directory "$project_root" stop postgres ;;
  *) printf 'Uso: %s {start|restart|stop}\n' "$0" >&2; exit 2 ;;
esac
