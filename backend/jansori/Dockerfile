FROM gradle:8.1.1-jdk11
WORKDIR /server
COPY ./build/libs/jansori-0.0.1-SNAPSHOT.jar jansori.jar
ENTRYPOINT ["java", "-jar", "jansori.jar"]