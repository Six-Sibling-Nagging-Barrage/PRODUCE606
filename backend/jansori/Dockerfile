FROM openjdk:11.0.19
WORKDIR /server
COPY ./build/libs/jansori-0.0.1-SNAPSHOT.jar jansori.jar
ENTRYPOINT ["java", "-jar", "jansori.jar"]