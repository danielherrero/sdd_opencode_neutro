@echo off
setlocal

set "PROJECT_ROOT=%~dp0.."

if "%DB_PASSWORD%"=="" if exist "%PROJECT_ROOT%\.env" (
    for /f "usebackq tokens=1,* delims==" %%A in ("%PROJECT_ROOT%\.env") do if "%%A"=="DB_PASSWORD" set "DB_PASSWORD=%%B"
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

if not exist "%PROJECT_ROOT%\frontend\node_modules" (
    echo No se encontraron las dependencias Angular. Ejecuta "npm install" en frontend.
    exit /b 1
)

docker compose --project-directory "%PROJECT_ROOT%" up -d --wait postgres
if errorlevel 1 exit /b %errorlevel%

start "Empresa SPA - Angular" /D "%PROJECT_ROOT%\frontend" cmd /c "npm start"

pushd "%PROJECT_ROOT%"
call mvnw.cmd --batch-mode quarkus:dev -Dquarkus.profile=dev
set "EXIT_CODE=%errorlevel%"
popd

taskkill /FI "WINDOWTITLE eq Empresa SPA - Angular" /T /F >nul 2>&1

exit /b %EXIT_CODE%
