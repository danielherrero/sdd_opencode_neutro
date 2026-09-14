@echo off
:: Script para recompilar el backend y reconstruir la imagen Docker
setlocal enabledelayedexpansion

echo Iniciando compilacion del backend con Maven...
call mvnw.cmd clean package -DskipTests

echo Reconstruyendo la imagen Docker...
docker build -t empresa:latest .

echo Proceso finalizado con exito.
pause
