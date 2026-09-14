@echo off
:: Usage: postgres-docker.bat [start|restart|stop]
setlocal enabledelayedexpansion


set "ProjectRoot=%~dp0.."

if "%~1"=="start" (
    docker compose --project-directory "%ProjectRoot%" up -d postgres
) else if "%~1"=="restart" (
    docker compose --project-directory "%ProjectRoot%" restart postgres
) else if "%~1"=="stop" (
    docker compose --project-directory "%ProjectRoot%" stop postgres
)
