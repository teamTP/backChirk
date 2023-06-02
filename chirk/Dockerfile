FROM maven:3.8.5-openjdk-17-slim AS build
RUN mkdir -p /backChirk
WORKDIR /backChirk
COPY pom.xml /backChirk
COPY src /backChirk/src
RUN mvn -f /backChirk/pom.xml -Dmaven.test.skip clean package

FROM openjdk:17-jdk-slim
COPY --from=build /backChirk/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=production
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]