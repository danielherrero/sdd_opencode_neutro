FROM eclipse-temurin:25-jdk AS build

WORKDIR /workspace
COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY src src

RUN chmod +x mvnw && ./mvnw -B package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /deployments
COPY --from=build /workspace/target/quarkus-app/lib/ ./lib/
COPY --from=build /workspace/target/quarkus-app/*.jar ./
COPY --from=build /workspace/target/quarkus-app/app/ ./app/
COPY --from=build /workspace/target/quarkus-app/quarkus/ ./quarkus/

RUN useradd --system --create-home --uid 10001 quarkus
USER 10001

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]
