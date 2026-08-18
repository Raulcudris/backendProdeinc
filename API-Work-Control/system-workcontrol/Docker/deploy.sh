#!/bin/bash

# Variables
SERVICE_NAME="API-Users"
BRANCH="main"
REPO_DIR="/home/loochon/microservicio/$SERVICE_NAME/system-users/Docker"
DEPLOY_LOG="$REPO_DIR/deploy.log"

echo "======== DEPLOY $SERVICE_NAME ========" | tee -a "$DEPLOY_LOG"
cd "$REPO_DIR" || { echo "❌ No se encontró el directorio $REPO_DIR"; exit 1; }

# Git Pull desde rama main
echo "🔄 Actualizando código fuente..." | tee -a "$DEPLOY_LOG"
git fetch origin "$BRANCH" >> "$DEPLOY_LOG" 2>&1
git reset --hard origin/"$BRANCH" >> "$DEPLOY_LOG" 2>&1

# Docker Compose deploy
echo "🧱 Bajando contenedor..." | tee -a "$DEPLOY_LOG"
docker-compose down >> "$DEPLOY_LOG" 2>&1

echo "⬇️  Pull de nueva imagen..." | tee -a "$DEPLOY_LOG"
docker-compose pull >> "$DEPLOY_LOG" 2>&1

echo "🚀 Levantando servicio..." | tee -a "$DEPLOY_LOG"
docker-compose up --build -d >> "$DEPLOY_LOG" 2>&1

echo "✅ Despliegue finalizado a las $(date)" | tee -a "$DEPLOY_LOG"