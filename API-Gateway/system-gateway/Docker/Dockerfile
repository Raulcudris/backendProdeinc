# Etapa 1: Build
FROM eclipse-temurin:11-jdk AS builder

RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /home

# Copiar todo el contexto primero
COPY . .

# Dar permisos y construir
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test --no-daemon

# Etapa 2: Runtime
FROM eclipse-temurin:11-jre

ENV TZ=America/Bogota

WORKDIR /home
COPY --from=builder /home/build/libs/*.jar app.jar

RUN adduser --system --group appuser && \
    chown appuser:appuser app.jar

USER appuser

EXPOSE 8088

CMD ["java", "-jar", "app.jar"]