param()

$ErrorActionPreference = 'Stop'

if ([string]::IsNullOrWhiteSpace($env:DB_PASSWORD)) {
    throw 'DB_PASSWORD debe estar definida para ejecutar el perfil dev.'
}

$projectRoot = Split-Path -Parent $PSScriptRoot
Push-Location $projectRoot
try {
    & .\mvnw.cmd --batch-mode quarkus:dev '-Dquarkus.profile=dev'
    if ($LASTEXITCODE -ne 0) {
        throw "La ejecucion local ha finalizado con el codigo $LASTEXITCODE."
    }
}
finally {
    Pop-Location
}
