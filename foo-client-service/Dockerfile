FROM openjdk:8u141-jre-slim
SHELL ["bash", "-c"]
ADD target/foo-client-service-exec.jar app.jar
RUN touch /app.jar
ADD entrypoint.sh entrypoint.sh
RUN chmod +x /entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["/entrypoint.sh"]
