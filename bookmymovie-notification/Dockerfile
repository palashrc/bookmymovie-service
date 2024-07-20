FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 8087 8087
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]