FROM eclipse-temurin:17-jdk-alpine\nVOLUME /tmp\nCOPY target/*.jar app.jar\nENTRYPOINT ["java","-jar","/app.jar"]
