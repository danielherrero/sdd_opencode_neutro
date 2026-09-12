param(
    [Parameter(Mandatory = $true)]
    [ValidateSet('start', 'restart', 'stop')]
    [string]$Action
)

$ErrorActionPreference = 'Stop'
$ProjectRoot = Split-Path -Parent $PSScriptRoot

if ([string]::IsNullOrWhiteSpace($env:DB_PASSWORD)) {
    throw 'DB_PASSWORD debe estar definida para gestionar PostgreSQL en Kubernetes.'
}

kubectl config use-context docker-desktop

switch ($Action) {
    'start' {
        kubectl apply -f "$ProjectRoot/deploy/kubernetes/namespace.yaml"
        kubectl --namespace sdd create secret generic postgresql-credentials `
            --from-literal=POSTGRES_USER=admin `
            --from-literal=POSTGRES_PASSWORD="$env:DB_PASSWORD" `
            --dry-run=client -o yaml | kubectl apply -f -
        kubectl apply -f "$ProjectRoot/deploy/kubernetes/postgresql-configmap.yaml"
        kubectl apply -f "$ProjectRoot/deploy/kubernetes/postgresql-pvc.yaml"
        kubectl apply -f "$ProjectRoot/deploy/kubernetes/postgresql-service.yaml"
        kubectl apply -f "$ProjectRoot/deploy/kubernetes/postgresql-statefulset.yaml"
        kubectl --namespace sdd scale statefulset/postgresql --replicas=1
        kubectl --namespace sdd rollout status statefulset/postgresql
    }
    'restart' {
        kubectl --namespace sdd rollout restart statefulset/postgresql
        kubectl --namespace sdd rollout status statefulset/postgresql
    }
    'stop' { kubectl --namespace sdd scale statefulset/postgresql --replicas=0 }
}
