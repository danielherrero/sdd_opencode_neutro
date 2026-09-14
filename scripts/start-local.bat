@echo off
set "PROJECT_ROOT=%~dp0.."
cd /d "%PROJECT_ROOT%"

if "%DB_PASSWORD%"=="" if exist ".env" (
    for /f "usebackq tokens=1,* delims==" %%A in (".env") do if "%%A"=="DB_PASSWORD" set "DB_PASSWORD=%%B"
)

if "%DB_PASSWORD%"=="" (
    echo DB_PASSWORD debe estar definida o indicada en .env.
    exit /b 1
)

where node >nul 2>&1
if errorlevel 1 (
    echo Node.js es necesario para ejecutar la aplicacion Angular.
    exit /b 1
)

where npm >nul 2>&1
if errorlevel 1 (
    echo npm es necesario para ejecutar la aplicacion Angular.
    exit /b 1
)

if not exist "frontend\node_modules" (
    echo No se encontraron las dependencias Angular. Ejecuta "npm install" en frontend.
    exit /b 1
)

docker compose ps postgres | findstr "running" >nul
if %errorlevel% neq 0 (
    echo Levantando contenedor de postgresql...
    docker compose up -d --wait postgres
)

start "Empresa SPA - Angular" /D "frontend" cmd /c "npm start"

call mvnw.cmd --batch-mode quarkus:dev -Dquarkus.profile=dev
set "EXIT_CODE=%errorlevel%"

taskkill /FI "WINDOWTITLE eq Empresa SPA - Angular" /T /F >nul 2>&1

exit /b %EXIT_CODE%
