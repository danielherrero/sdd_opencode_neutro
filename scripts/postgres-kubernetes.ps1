param(
    [Parameter(Mandatory = $true)]
    [ValidateSet('start', 'restart', 'stop')]
    [string]$Action
)

$ErrorActionPreference = 'Stop'

kubectl config use-context docker-desktop

switch ($Action) {
    'start' {
        kubectl apply -f deploy/kubernetes/namespace.yaml
        kubectl apply -f deploy/kubernetes/postgresql-secret.yaml
        kubectl apply -f deploy/kubernetes/postgresql-configmap.yaml
        kubectl apply -f deploy/kubernetes/postgresql-pvc.yaml
        kubectl apply -f deploy/kubernetes/postgresql-service.yaml
        kubectl apply -f deploy/kubernetes/postgresql-statefulset.yaml
        kubectl --namespace sdd scale statefulset/postgresql --replicas=1
        kubectl --namespace sdd rollout status statefulset/postgresql
    }
    'restart' {
        kubectl --namespace sdd rollout restart statefulset/postgresql
        kubectl --namespace sdd rollout status statefulset/postgresql
    }
    'stop' { kubectl --namespace sdd scale statefulset/postgresql --replicas=0 }
}
