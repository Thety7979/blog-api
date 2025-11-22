FROM maven:4.0.0-rc-5-amazoncorretto-21-debian AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM amazoncorretto:21.0.1

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]