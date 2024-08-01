FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 8090 8090
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]