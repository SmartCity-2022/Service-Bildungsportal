FROM gradle:7.4-jdk18-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:18-oracle

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/ /app/

ENTRYPOINT ["java","-jar","/app/backend-0.0.1-SNAPSHOT.jar"]