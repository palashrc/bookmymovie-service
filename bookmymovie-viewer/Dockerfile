FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 8084 8084
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]