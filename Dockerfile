#FROM gradle:jdk8-jammy AS builder
#COPY --chown=gradle:gradle . /src
#WORKDIR /src
#RUN gradle clean buildFatJar --no-daemon

FROM amazoncorretto:21.0.1
RUN mkdir /app
#COPY --from=builder /src/build/libs/*.jar /app/ktor-docker-sample.jar
COPY /build/libs/*.jar /app/ktor-docker-sample.jar
ENTRYPOINT ["java","-jar","/app/ktor-docker-sample.jar"]