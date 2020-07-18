FROM adoptopenjdk/openjdk11-openj9:ubi-minimal
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port=80 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV AB_ENABLED=jmx_exporter
ENV twitter.consumerKey=twitter.consumerKey
ENV twitter.secret=twitter.secret
ENV twitter.woeid=twitter.woeid
RUN mkdir /opt/app
COPY target/lib/* /opt/app/lib/
COPY target/*-runner.jar /opt/app/app.jar
EXPOSE 80
CMD ["java","-jar","/opt/app/app.jar"]
