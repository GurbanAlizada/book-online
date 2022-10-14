FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE}  book-online-spring-boot-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java" , "-jar" , "/book-online-spring-boot-0.0.1-SNAPSHOT.jar"]

