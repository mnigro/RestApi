FROM java:8
EXPOSE 8080
ADD /target/RestApi-itaccelerator.jar RestApi-itaccelerator.jar
ENTRYPOINT ["java","-jar","RestApi-itaccelerator.jar"]