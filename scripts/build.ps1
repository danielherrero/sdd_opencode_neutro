param()

$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
Push-Location $projectRoot
try {
    & .\mvnw.cmd --batch-mode clean verify
    if ($LASTEXITCODE -ne 0) {
        throw "La compilacion Maven ha fallado con el codigo $LASTEXITCODE."
    }
}
finally {
    Pop-Location
}
