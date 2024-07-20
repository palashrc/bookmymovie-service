FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 8085 8085
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]