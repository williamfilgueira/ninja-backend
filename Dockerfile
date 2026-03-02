# ===== Build stage =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia pom primeiro para cache de dependências
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Copia o restante do projeto
COPY src ./src

# Build do jar
RUN mvn -q -DskipTests package

# ===== Runtime stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o jar gerado (ajusta se seu jar tiver nome específico)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]