#!/bin/bash
# Script para recompilar el backend y reconstruir la imagen Docker
set -e

echo "Iniciando compilación del backend con Maven..."
./mvnw clean package -DskipTests

echo "Reconstruyendo la imagen Docker..."
docker build -t empresa:latest .

echo "Proceso finalizado con éxito."
