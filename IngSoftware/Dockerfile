FROM openjdk:8
VOLUME \tmp
EXPOSE 8080
ADD ./target/ejemplobackend-0.0.1-SNAPSHOT.jar CRUD.jar
ENTRYPOINT ["java","-jar","/CRUD.jar"]