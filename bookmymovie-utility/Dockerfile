FROM openjdk:17
ADD service/target/service.jar /deployments/service.jar
EXPOSE 8088 8088
ENTRYPOINT ["java", "-jar", "/deployments/service.jar"]