FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 8089 8089
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]