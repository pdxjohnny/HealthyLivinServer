FROM java:openjdk-8-jre-alpine

ADD out/artifacts/Web/Web.jar /usr/bin/Web
ADD out/artifacts/Database/Database.jar /usr/bin/Database

ENTRYPOINT ["/usr/bin/java", "-jar"]
CMD ["/usr/bin/Web"]
