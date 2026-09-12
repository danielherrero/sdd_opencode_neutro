param(
    [Parameter(Mandatory = $true)]
    [ValidateSet('start', 'restart', 'stop')]
    [string]$Action
)

$ErrorActionPreference = 'Stop'

switch ($Action) {
    'start' { docker compose up -d postgres }
    'restart' { docker compose restart postgres }
    'stop' { docker compose stop postgres }
}
