# POC Spring Data JPA

It is a RESTful API built with Java 11 + Spring Boot 2.7 + Spring Data + MySQL 8 + Flyway 8.5 for CRUD data in MySQL.
This API is responsible for storing and maintaining data in a relational database and exposing advanced query endpoints in the database to explore Spring Data Jpa.

## Getting Started

### Prerequisites

To run this project in development mode, you will need to have a basic environment with Java JDK 11+ and Maven 3.5.4+ installed. To use the database, you will need to have MySQL installed and running on your machine on the default port (3306).

### Tips: Run MySQL on Docker

To run MySQL on your local machine, you already need to have Docker installed, before that run the following commands:
```
docker run --name name-mysql -e MYSQL_ROOT_PASSWORD=mysqlpass -p 3306:3306 -d mysql:8
```
For arm64 architectures run the following
```
docker run --name name-mysql -e MYSQL_ROOT_PASSWORD=mysqlpass -p 3306:3306 -d arm64v8/mysql:8.0-oracle
```

### Installing

**Cloning the Repository**
````
$ git clone https://github.com/rodrigolimasd/java-poc-projects.git
$ cd java-poc-projects/poc-spring-data-jpa
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
