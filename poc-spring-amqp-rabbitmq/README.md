# POC Spring AMPQ - RabbitMQ

It is a RESTful API built with Java 11 + Spring Boot 2.7 + Spring AMPQ.

This API is responsible for receiving requests via REST and producing messages for queues in RabbiMQ, it is also responsible for consuming messages and logging them to the console.
The RabbitMQ exhance setting is of type Topic.

## Getting Started

### Prerequisites

To run this project in development mode, you will need to have a basic environment with Java JDK 11+ and Maven 3.5.4+ installed. To use the RabbitMQ, you will need to have RabbitMQ installed and running on your machine on the default port (5672) and user **guest** and password **guest**.

### Tips: Run RabbitMQ on Docker

To run RabbitMQ on your local machine, you already need to have Docker installed, before that run the following commands:

```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

### Installing

**Cloning the Repository**
````
$ git clone https://github.com/rodrigolimasd/java-poc-projects.git
$ cd java-poc-projects/poc-spring-amqp-rabbitmq
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

**Producer a Transaction on Queue Bank**

```
curl --location --request POST 'http://localhost:8091/v1/transaction' \
--header 'Content-Type: application/json' \
--data-raw '{
        "date": "2022-03-01",
        "income": 500.00,
        "expense": 0.00,
        "balanceValue": 500.00,
        "note": "initial month",
        "type":"BANK"
}'
```

**Producer a Transaction on Queue Credit Card**

```
curl --location --request POST 'http://localhost:8091/v1/transaction' \
--header 'Content-Type: application/json' \
--data-raw '{
        "date": "2022-03-01",
        "income": 500.00,
        "expense": 0.00,
        "balanceValue": 500.00,
        "note": "initial month",
        "type":"CREDIT_CARD"
}'
```

**Producer and Consumer a Transaction on Queue General**

```
curl --location --request POST 'http://localhost:8091/v1/transaction' \
--header 'Content-Type: application/json' \
--data-raw '{
        "date": "2022-03-01",
        "income": 500.00,
        "expense": 0.00,
        "balanceValue": 500.00,
        "note": "initial month"
}'
```

### References

[Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
[RabbitMQ Message Dispatching with Spring AMQP](https://www.baeldung.com/rabbitmq-spring-amqp)