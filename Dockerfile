FROM tomcat:9.0.48-jdk16-openjdk-buster
RUN rm -rf /usr/local/tomcat/webapps/*
COPY /target/untitled.war /usr/local/tomcat/webapps/