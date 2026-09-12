param(
    [Parameter(Mandatory = $true)]
    [ValidateSet('start', 'restart', 'stop')]
    [string]$Action
)

$ErrorActionPreference = 'Stop'
$ProjectRoot = Split-Path -Parent $PSScriptRoot

switch ($Action) {
    'start' { docker compose --project-directory $ProjectRoot up -d postgres }
    'restart' { docker compose --project-directory $ProjectRoot restart postgres }
    'stop' { docker compose --project-directory $ProjectRoot stop postgres }
}
