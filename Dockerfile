FROM eclipse-temurin:17-jdk-focal as builder
WORKDIR /opt/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

COPY ./src ./src
RUN ./mvnw clean install -DskipTests 
RUN find ./target -type f -name '*.jar' -exec cp {} /opt/app/app.jar \; -quit


FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /opt/app/app.jar /app/app.jar

RUN mkdir -p /app/data && chmod -R 777 /app/data

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]