FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8081
ADD server/target/server.jar app.jar
RUN sh -c 'touch /app.jar'
CMD java -jar app.jar