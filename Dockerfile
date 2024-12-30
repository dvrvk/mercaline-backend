# Usar una imagen base de OpenJDK 19
FROM openjdk:19-jdk-slim

# Crear y establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR del backend
COPY target/mercaline-0.0.1-SNAPSHOT.jar .

# Exponer el puerto en el que corre la aplicación Spring Boot (por ejemplo, 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "mercaline-0.0.1-SNAPSHOT.jar"]
