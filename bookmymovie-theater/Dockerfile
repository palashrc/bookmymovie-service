FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 8082 8082
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]