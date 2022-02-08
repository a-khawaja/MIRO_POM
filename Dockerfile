FROM maven:3.6.0-jdk-8-alpine

COPY . /home/AutomatedTests

COPY pom.xml /home/AutomatedTests

COPY dockerUp.sh /home/AutomatedTests
COPY dockerDown.sh /home/AutomatedTests

COPY testng.xml /home/AutomatedTests

COPY log4j.properties /home/AutomatedTests

COPY src/config/config.properties /home/AutomatedTests

COPY src/data/MIRO_Data.xlsx /home/AutomatedTests

RUN mvn -f /home/AutomatedTests/pom.xml clean test -DskipTests=true