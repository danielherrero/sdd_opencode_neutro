#!/usr/bin/env sh
set -eu

case "${1:-}" in
  start) docker compose up -d postgres ;;
  restart) docker compose restart postgres ;;
  stop) docker compose stop postgres ;;
  *) printf 'Uso: %s {start|restart|stop}\n' "$0" >&2; exit 2 ;;
esac
