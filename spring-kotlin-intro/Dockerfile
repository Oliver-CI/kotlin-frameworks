FROM maven:3-openjdk-17 AS build
COPY . /home/maven/src
WORKDIR /home/maven/src
RUN mvn package

FROM eclipse-temurin:17.0.2_8-jre
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/maven/src/target/*.jar /app/spring-docker.jar
ENTRYPOINT ["java","-jar","/app/spring-docker.jar"]
