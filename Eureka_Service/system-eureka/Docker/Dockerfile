# Etapa 1: Build
FROM eclipse-temurin:11-jdk AS builder

RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /home

# Copiar archivos de configuración primero para mejor caching
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle/ gradle/

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

# Copiar código fuente
COPY src/ src/

RUN ./gradlew clean build -x test --no-daemon

# Etapa 2: Runtime
FROM eclipse-temurin:11-jre

ENV TZ=America/Bogota

WORKDIR /home
COPY --from=builder /home/build/libs/*.jar app.jar

# Usar usuario no-root para seguridad
RUN adduser --system --group appuser && \
    chown appuser:appuser app.jar

USER appuser

EXPOSE 8761

CMD ["java", "-jar", "app.jar"]