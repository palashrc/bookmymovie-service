FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 8081 8081
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]