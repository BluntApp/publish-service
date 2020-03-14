FROM java:8
ADD target/publish-service.jar publish-service.jar
ENTRYPOINT ["java","-jar","publish-service.jar"]
