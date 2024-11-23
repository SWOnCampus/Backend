FROM openjdk:17
COPY ./build/libs/SwOnCampus-0.0.1-SNAPSHOT.jar SwOnCampus.jar
ENTRYPOINT ["java", "-jar", "SwOnCampus.jar"]