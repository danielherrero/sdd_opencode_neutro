#!/usr/bin/env sh
set -eu

project_root=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
: "${DB_PASSWORD:?DB_PASSWORD debe estar definida para gestionar PostgreSQL en Kubernetes}"

kubectl config use-context docker-desktop

case "${1:-}" in
  start)
    kubectl apply -f "$project_root/deploy/kubernetes/namespace.yaml"
    kubectl --namespace sdd create secret generic postgresql-credentials \
      --from-literal=POSTGRES_USER=admin \
      --from-literal=POSTGRES_PASSWORD="$DB_PASSWORD" \
      --dry-run=client -o yaml | kubectl apply -f -
    kubectl apply -f "$project_root/deploy/kubernetes/postgresql-configmap.yaml"
    kubectl apply -f "$project_root/deploy/kubernetes/postgresql-pvc.yaml"
    kubectl apply -f "$project_root/deploy/kubernetes/postgresql-service.yaml"
    kubectl apply -f "$project_root/deploy/kubernetes/postgresql-statefulset.yaml"
    kubectl --namespace sdd scale statefulset/postgresql --replicas=1
    kubectl --namespace sdd rollout status statefulset/postgresql
    ;;
  restart)
    kubectl --namespace sdd rollout restart statefulset/postgresql
    kubectl --namespace sdd rollout status statefulset/postgresql
    ;;
  stop) kubectl --namespace sdd scale statefulset/postgresql --replicas=0 ;;
  *) printf 'Uso: %s {start|restart|stop}\n' "$0" >&2; exit 2 ;;
esac
