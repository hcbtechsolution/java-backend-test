# Build Stage
FROM maven:3.9.8 AS build

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline --no-transfer-progress

COPY src ./src

RUN mvn -B clean package -DskipTests --no-transfer-progress

################################################################################

# Exec Stage
FROM eclipse-temurin:17-jre-ubi9-minimal AS exec

WORKDIR /opt/app

COPY --from=build /build/target/establishment-service*.jar ./establishment-service.jar

ARG UID=10001

RUN useradd \
    --uid "${UID}" \
    --home-dir "/nonexistent" \
    --shell "/sbin/nologin" \
    appuser \
    && mkdir -p /opt/app/logs \
    && chown appuser:appuser /opt/app/logs

USER appuser

EXPOSE 8010 

ENTRYPOINT ["java","-jar","./establishment-service.jar"]