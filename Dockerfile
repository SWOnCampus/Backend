FROM openjdk:17
COPY ./build/libs/AIPlatform-0.0.1-SNAPSHOT.jar SwOnCampus.jar
ENTRYPOINT ["java", "-jar", "SwOnCampus.jar"]