# Alpine openjdk base container to minimize size
FROM openjdk:15-jdk-alpine

# Get the jar package as argument
ARG JAR_PACKAGE

# User setup for security
USER 1000

WORKDIR /sogo
COPY ${JAR_PACKAGE} /sogo/main.jar

CMD ["java", "-jar", "/sogo/main.jar", "--nogui"]
