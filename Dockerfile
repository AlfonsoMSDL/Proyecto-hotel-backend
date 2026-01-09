
# ETAPA 1: BUILD
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código
COPY src ./src

# Construir el JAR
RUN mvn clean package -DskipTests



# ETAPA 2: RUN

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
