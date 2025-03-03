FROM openjdk:17-jdk-slim as build

WORKDIR /app

# Maven bağımlılıklarını kopyala ve indir
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x ./mvnw
# Bağımlılıkları çek
RUN ./mvnw dependency:go-offline -B

# Kaynak kodunu kopyala ve derle
COPY src src
RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

# Sadece üretilen JAR dosyasını kopyala
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]