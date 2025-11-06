##
# Build the Spring Boot application in a two-stage Docker build.  The
# first stage uses a Maven image to assemble the fat jar.  The second
# stage runs the jar on a slim JDK base.  Using a two‑stage build
# ensures that the final image is lightweight and does not contain
# development dependencies.
##

# ---------- Build stage ----------
FROM maven:3.9.8-amazoncorretto-17 AS build
WORKDIR /build

# Copy the pom and download dependencies (this layer is cached if the
# dependencies do not change).
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Copy source code and run the package goal.  We skip tests because
# multi-stage builds don't persist test results, but they could be
# executed in a separate CI pipeline.
COPY src ./src
RUN mvn -q -e -DskipTests package

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre-alpine
ENV JAVA_OPTS=""
WORKDIR /app

# Copy the fat jar produced in the build stage.  The target directory
# includes all JAR artifacts; the Spring Boot plugin creates
# paper-mill-billing-1.0.0.jar.
COPY --from=build /build/target/paper-mill-billing-*.jar app.jar

# Expose the application port (matching application.properties).  When
# running under docker-compose, the mapping can be changed without
# rebuilding this image.
EXPOSE 8080

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]