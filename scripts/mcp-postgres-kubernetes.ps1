$ErrorActionPreference = 'Stop'

if ([string]::IsNullOrWhiteSpace($env:POSTGRES_MCP_PASSWORD)) {
    throw 'POSTGRES_MCP_PASSWORD debe estar definida para iniciar el MCP de PostgreSQL.'
}

kubectl config use-context docker-desktop | Out-Null
$portForward = Start-Process -FilePath kubectl -ArgumentList '--namespace', 'sdd', 'port-forward', 'service/postgresql', '15432:5432' -PassThru -WindowStyle Hidden

try {
    Start-Sleep -Seconds 2
    $encodedPassword = [Uri]::EscapeDataString($env:POSTGRES_MCP_PASSWORD)
    & npx.cmd -y '@modelcontextprotocol/server-postgres' "postgresql://admin:$encodedPassword@localhost:15432/sdd"
}
finally {
    if (-not $portForward.HasExited) {
        Stop-Process -Id $portForward.Id -Force
    }
}
