# POC Spring Kafka

It is a API built with Java 11 + Spring Boot 2.7 + Spring Kafka for producer and consumer Kafka messages.

## Getting Started

### Prerequisites

To run this project in development mode, you will need to have a basic environment with Java JDK 11+ and Maven 3.5.4+ installed.
To use the Kafka, you will need to have Kafka installed and running on your machine on the port (29092).

### Tips: Run Kafka Single Node on Docker

To run Kafka on your local machine, you already need to have Docker installed, before that run the following commands:
```
cd docker
docker-compose up -d
```

### Installing

**Cloning the Repository**
````
$ git clone https://github.com/rodrigolimasd/java-poc-projects.git
$ cd java-poc-projects/poc-spring-kafka
````
### Running the Development

**Running with Maven**
```
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

To run the tests use the following command

**Running the tests**

```
$ mvn clean test
```

### Consuming the API

**Producer a Event on Topic Events**

```
curl --location --request POST 'localhost:8080/v1/events' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title":"vai",
    "description":"description"
}'
```