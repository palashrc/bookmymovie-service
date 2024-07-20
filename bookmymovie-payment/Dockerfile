FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 8086 8086
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]