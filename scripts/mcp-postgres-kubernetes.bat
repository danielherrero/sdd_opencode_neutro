@echo off
setlocal enabledelayedexpansion

if "%POSTGRES_MCP_PASSWORD%"=="" (
    echo POSTGRES_MCP_PASSWORD debe estar definida para iniciar el MCP de PostgreSQL.
    exit /b 1
)

kubectl config use-context docker-desktop | set /p=
set "portForward=..."
start /B kubectl --namespace sdd port-forward service/postgresql 15432:5432

timeout /t 2 /nobreak >nul
set "encodedPassword="
for /f "tokens=*" %%a in ('python -c "import urllib.parse; print(urllib.parse.quote('%POSTGRES_MCP_PASSWORD%'))"') do set "encodedPassword=%%a"

npx -y @modelcontextprotocol/server-postgres "postgresql://admin:%encodedPassword%@localhost:15432/sdd"
