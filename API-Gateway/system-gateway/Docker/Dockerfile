
FROM openjdk:11.0-jdk-slim-stretch

RUN apt-get install -y tzdata

ENV TZ=America/Bogota

# Set the working directory to /home
WORKDIR /home

# Copy the built JAR file into the container
COPY ./build/libs/*.jar system-gateway-0.0.1-SNAPSHOT.jar

# Expose the default port for the app
EXPOSE 8088

# Start the app when the container launches
CMD ["java", "-jar", "system-gateway-0.0.1-SNAPSHOT.jar"]
