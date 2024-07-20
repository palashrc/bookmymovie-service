FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 8083 8083
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]