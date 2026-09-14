@echo off
:: Usage: postgres-kubernetes.bat [start|restart|stop]
setlocal enabledelayedexpansion


set "ProjectRoot=%~dp0.."

if "%DB_PASSWORD%"=="" (
    echo DB_PASSWORD debe estar definida para gestionar PostgreSQL en Kubernetes.
    exit /b 1
)

kubectl config use-context docker-desktop

if "%~1"=="start" (
    kubectl apply -f "%ProjectRoot%\deploy\kubernetes\namespace.yaml"
    kubectl --namespace sdd create secret generic postgresql-credentials ^
        --from-literal=POSTGRES_USER=admin ^
        --from-literal=POSTGRES_PASSWORD="%DB_PASSWORD%" ^
        --dry-run=client -o yaml | kubectl apply -f -
    kubectl apply -f "%ProjectRoot%\deploy\kubernetes\postgresql-configmap.yaml"
    kubectl apply -f "%ProjectRoot%\deploy\kubernetes\postgresql-pvc.yaml"
    kubectl apply -f "%ProjectRoot%\deploy\kubernetes\postgresql-service.yaml"
    kubectl apply -f "%ProjectRoot%\deploy\kubernetes\postgresql-statefulset.yaml"
    kubectl --namespace sdd scale statefulset/postgresql --replicas=1
    kubectl --namespace sdd rollout status statefulset/postgresql
) else if "%~1"=="restart" (
    kubectl --namespace sdd rollout restart statefulset/postgresql
    kubectl --namespace sdd rollout status statefulset/postgresql
) else if "%~1"=="stop" (
    kubectl --namespace sdd scale statefulset/postgresql --replicas=0
) else (
    echo Uso: %~nx0 [start|restart|stop]
    exit /b 1
)
