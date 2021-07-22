FROM openjdk:11
ADD target/WebBook.jar WebBook.jar
EXPOSE 9433
ENTRYPOINT ["java","-jar","WebBook.jar"]