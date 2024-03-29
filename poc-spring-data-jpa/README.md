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

### Consuming the API

**Create A Transaction**

```
curl --location --request POST 'http://localhost:8080/v1/transaction' \
--header 'Content-Type: application/json' \
--data-raw '{
    "date":"2022-05-01",
    "income": 10000.00,
    "expense": 0.0,
    "balanceValue": 10000.00,
    "note":"salary"
}'
```
Response Status Code: **201**

**Create Bath Transactions**

```
curl --location --request POST 'http://localhost:8080/v1/transaction/batch' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "date": "2022-05-01",
        "income": 0.00,
        "expense": 0.00,
        "balanceValue": 0.00,
        "note": "initial month"
    },
    {
        "date": "2022-03-03",
        "income": 0.00,
        "expense": 0.00,
        "balanceValue": 0.00,
        "note": null
    }
]'
```
Response Status Code: **201**

---
**Find A Transaction By ID**

```
curl --location --request GET 'http://localhost:8080/v1/transaction/1'
```
Response Status Code **200**
```
{"id":1,"created":"2022-05-01T11:00:59","edit":null,"date":"2022-05-01","income":10000.00,"expense":0.00,"balanceValue":10000.00,"note":"salary"}
```

**Find Transactions By Date**
```
curl --location --request GET 'http://localhost:8080/v1/transaction/date/2022-05-31?page=0&size=200'
```
Response Status Code **200**
```
[
    {
        "id": 24,
        "created": "2022-05-01T12:21:01",
        "edit": "2022-05-01T13:04:00",
        "date": "2022-05-31",
        "income": 0.00,
        "expense": 0.00,
        "balanceValue": 0.00,
        "note": "end month"
    }
]
```
---
**Update A Transaction**
```
curl --location --request PUT 'http://localhost:8080/v1/transaction' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1,
    "date": "2022-06-07",
    "income": 10500.00,
    "expense": 0.00,
    "balanceValue": 10500.00,
    "note": "salary"
}'
```
Response Status Code **204** No Content 

---
**Filter Transactions**

```
curl --location --request GET 'http://localhost:8080/v1/transaction?page=0&size=2'
```
Response Status Code **200**
```
{
    "content": [
        {
            "id": 1,
            "created": "2022-05-01T11:00:59",
            "edit": "2022-05-01T11:10:59",
            "date": "2022-05-01",
            "income": 10500.00,
            "expense": 0.00,
            "balanceValue": 10500.00,
            "note": "salary"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 2,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "numberOfElements": 1,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "first": true,
    "number": 0,
    "size": 2,
    "empty": false
}
```

**Filter transaction By Year and Month**

```
curl --location --request GET 'http://localhost:8080/v1/transaction/year/2022/month/5?page=0&size=200'
```

Response Status Code **200**
```
{
    "content": [
        {
            ...
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 2,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "numberOfElements": 1,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "first": true,
    "number": 0,
    "size": 2,
    "empty": false
}
```

**Filter Transaction With Query Params**

```
curl --location --request GET 'http://localhost:8080/v1/transaction/filter?startDate=2022-03-01&endDate=2022-05-31&page=0&size=50&incomeFrom=10500.00&incomeUpTo=10000.00&expense=1000&expenseFrom=1000&expenseUpTo=2000&balanceValue=500&balanceValueFrom=3000&balanceValueUpTo=5000'
```
Response Status Code **200**

```
{
    "content": [
            ...
    ],
    "pageable": {
        "sort": {
            "unsorted": true,
            "sorted": false,
            "empty": true
        },
        "pageSize": 50,
        "pageNumber": 0,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalElements": 0,
    "totalPages": 0,
    "last": true,
    "numberOfElements": 0,
    "sort": {
        "unsorted": true,
        "sorted": false,
        "empty": true
    },
    "number": 0,
    "first": true,
    "size": 50,
    "empty": true
}
```
---
**Delete A Transaction**
```
curl --location --request DELETE 'http://localhost:8080/v1/transaction/1'
```
Response Status Code **204**

---

### Consuming the API with swagger

It is also possible to test and consume the API with Swagger.

**Swagger** Endpoint: http://localhost:8080/swagger-ui.html

###References

[Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
[Flyway Mysql](https://flywaydb.org/documentation/database/mysql)