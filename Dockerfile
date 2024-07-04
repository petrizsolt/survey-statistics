FROM amazoncorretto:17.0.7-alpine

WORKDIR /app

CMD ["dos2unix", "mvnw"]
CMD ["./mvnw", "spring-boot:run"]