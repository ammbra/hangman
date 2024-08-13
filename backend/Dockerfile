FROM maven:3.6.3-jdk-11 AS MVN_BUILD
MAINTAINER Ana

LABEL version="1.0" \
      description="This is an image for building sample hangman app"

COPY . /code
RUN echo '{ "allow_root": true }' > rm -Rf /code/target && \
	cd /code/ && \
	chmod +x mvnw && \
    mvn clean package

FROM openjdk:11-jre

COPY --from=MVN_BUILD /code/target/*.jar /hangman.jar
RUN groupadd -r appuser && useradd -r -g appuser appuser
USER appuser
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","hangman.jar"]
