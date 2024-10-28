# Usa la imagen base de Eclipse Temurin para Java 21
FROM eclipse-temurin:21-jdk

# Configura el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
COPY build/libs/server-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto que usa la aplicación (ajusta según sea necesario)
EXPOSE 9000

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]