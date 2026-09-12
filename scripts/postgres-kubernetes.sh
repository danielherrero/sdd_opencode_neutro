#!/usr/bin/env sh
set -eu

kubectl config use-context docker-desktop

case "${1:-}" in
  start)
    kubectl apply -f deploy/kubernetes/namespace.yaml
    kubectl apply -f deploy/kubernetes/postgresql-secret.yaml
    kubectl apply -f deploy/kubernetes/postgresql-configmap.yaml
    kubectl apply -f deploy/kubernetes/postgresql-pvc.yaml
    kubectl apply -f deploy/kubernetes/postgresql-service.yaml
    kubectl apply -f deploy/kubernetes/postgresql-statefulset.yaml
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
