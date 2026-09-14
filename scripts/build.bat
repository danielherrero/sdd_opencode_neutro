@echo off
setlocal enabledelayedexpansion

set "projectRoot=%~dp0.."
pushd "%projectRoot%"
call .\mvnw.cmd --batch-mode clean verify
if %ERRORLEVEL% neq 0 (
    echo La compilacion Maven ha fallado con el codigo %ERRORLEVEL%.
    exit /b %ERRORLEVEL%
)
popd
