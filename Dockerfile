FROM openjdk:8-jre-alpine
EXPOSE 8080
COPY ./target/bets-0.0.1-SNAPSHOT.jar bets-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "bets-0.0.1-SNAPSHOT.jar"]