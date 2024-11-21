# Étape 1 : Build
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn package

# Étape 2 : Run
FROM maven:3.9.4-eclipse-temurin-21 AS runner
WORKDIR /app

RUN apt-get update && apt-get -y upgrade
RUN apt-get install -y inotify-tools dos2unix postgresql-client

COPY --from=builder /app/target/*.jar app.jar
# ENTRYPOINT ["java", "-jar", "/app.jar"]
# CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.profiles=dev", \
#     "-Dspring.devtools.restart.enabled=true", \
#     "-Dspring.devtools.livereload.enabled=true", \
#     "-Dspring.devtools.remote.secret=mysecret"]


