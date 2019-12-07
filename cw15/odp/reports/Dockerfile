FROM adoptopenjdk:11-jre-hotspot

ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS
ENV DELAY_START=0

COPY target/*.jar app.jar

CMD echo "The application will start in ${DELAY_START}s..." && sleep ${DELAY_START} && java -jar /app.jar

EXPOSE ${SERVER_PORT}
