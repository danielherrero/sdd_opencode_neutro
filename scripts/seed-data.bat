@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "BASE_URL=%BASE_URL%"
if "%BASE_URL%"=="" if not "%HTTP_PORT%"=="" set "BASE_URL=http://localhost:%HTTP_PORT%"
if "%BASE_URL%"=="" set "BASE_URL=http://localhost:8081"
if "%BASE_URL:~-1%"=="/" set "BASE_URL=%BASE_URL:~0,-1%"

where curl.exe >nul 2>&1
if errorlevel 1 (
    echo Error: curl.exe es necesario.
    exit /b 1
)

curl.exe --silent --show-error --fail "%BASE_URL%/q/health" | findstr /i /c:"status" >nul 2>&1
if errorlevel 1 (
    echo Error: no se puede verificar Quarkus en %BASE_URL%/q/health.
    echo El puerto puede estar ocupado por otro servicio o Quarkus no esta iniciado.
    echo Usa, por ejemplo: set BASE_URL=http://localhost:8082
    echo O configura HTTP_PORT con el puerto publicado por Quarkus.
    exit /b 1
)

set "TEMP_DIR=%TEMP%\empresa-seed-%RANDOM%"
mkdir "%TEMP_DIR%" >nul 2>&1

>"%TEMP_DIR%\user1.json" echo {"nombre":"Ana","apellidos":"Garcia","fechaNacimiento":"1988-02-14","contrasena":"DemoPassword1"}
>"%TEMP_DIR%\user2.json" echo {"nombre":"Luis","apellidos":"Martin","fechaNacimiento":"1990-06-22","contrasena":"DemoPassword2"}
>"%TEMP_DIR%\user3.json" echo {"nombre":"Marta","apellidos":"Lopez","fechaNacimiento":"1985-11-03","contrasena":"DemoPassword3"}
>"%TEMP_DIR%\user4.json" echo {"nombre":"Pablo","apellidos":"Sanchez","fechaNacimiento":"1992-09-18","contrasena":"DemoPassword4"}
>"%TEMP_DIR%\user5.json" echo {"nombre":"Elena","apellidos":"Ruiz","fechaNacimiento":"1987-12-27","contrasena":"DemoPassword5"}
>"%TEMP_DIR%\department1.json" echo {"nombre":"Tecnologia","descripcion":"Desarrollo y mantenimiento de aplicaciones"}
>"%TEMP_DIR%\department2.json" echo {"nombre":"Recursos Humanos","descripcion":"Gestion de personas y talento"}
>"%TEMP_DIR%\department3.json" echo {"nombre":"Finanzas","descripcion":"Gestion financiera y contable"}

echo Creando usuarios...
call :create_user user1.json USER_1 || goto :failed
call :create_user user2.json USER_2 || goto :failed
call :create_user user3.json USER_3 || goto :failed
call :create_user user4.json USER_4 || goto :failed
call :create_user user5.json USER_5 || goto :failed

echo Creando departamentos...
call :create_department department1.json DEPARTMENT_1 || goto :failed
call :create_department department2.json DEPARTMENT_2 || goto :failed
call :create_department department3.json DEPARTMENT_3 || goto :failed

echo Creando relaciones...
call :create_relation !USER_1! !DEPARTMENT_1! || goto :failed
call :create_relation !USER_2! !DEPARTMENT_2! || goto :failed
call :create_relation !USER_3! !DEPARTMENT_3! || goto :failed
call :create_relation !USER_4! !DEPARTMENT_1! || goto :failed
call :create_relation !USER_5! !DEPARTMENT_2! || goto :failed

echo Datos iniciales creados correctamente.
echo Usuarios: !USER_1!, !USER_2!, !USER_3!, !USER_4!, !USER_5!
echo Departamentos: !DEPARTMENT_1!, !DEPARTMENT_2!, !DEPARTMENT_3!
rmdir /s /q "%TEMP_DIR%" >nul 2>&1
exit /b 0

:create_user
set "RESULT_FILE=%TEMP_DIR%\%~n1-result.json"
curl.exe --silent --show-error --fail-with-body --request POST "%BASE_URL%/users" --header "Content-Type: application/json" --data-binary "@%TEMP_DIR%\%~1" --output "%RESULT_FILE%"
if errorlevel 1 exit /b 1
for /f "usebackq delims=" %%I in (`powershell -NoProfile -Command "(Get-Content -Raw '%RESULT_FILE%' | ConvertFrom-Json).id"`) do set "%~2=%%I"
if "!%~2!"=="" exit /b 1
exit /b 0

:create_department
set "RESULT_FILE=%TEMP_DIR%\%~n1-result.json"
curl.exe --silent --show-error --fail-with-body --request POST "%BASE_URL%/departments" --header "Content-Type: application/json" --data-binary "@%TEMP_DIR%\%~1" --output "%RESULT_FILE%"
if errorlevel 1 exit /b 1
for /f "usebackq delims=" %%I in (`powershell -NoProfile -Command "(Get-Content -Raw '%RESULT_FILE%' | ConvertFrom-Json).id"`) do set "%~2=%%I"
if "!%~2!"=="" exit /b 1
exit /b 0

:create_relation
>"%TEMP_DIR%\relation.json" echo {"userId":%~1,"departmentId":%~2}
curl.exe --silent --show-error --fail-with-body --request POST "%BASE_URL%/user-departments" --header "Content-Type: application/json" --data-binary "@%TEMP_DIR%\relation.json" --output nul
if errorlevel 1 exit /b 1
exit /b 0

:failed
echo Error: no se pudieron crear los datos iniciales.
rmdir /s /q "%TEMP_DIR%" >nul 2>&1
exit /b 1
