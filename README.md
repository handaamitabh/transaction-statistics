# N26 Transaction Statistics API

## API Endpoints

Process Transaction Endpoint
POST http://localhost:8081/transactions

Get Statistics Endpoint
GET http://localhost:8081/statistics

## Swagger Endpoint

http://localhost:8081/swagger-ui.html

## Testing

Functional Cucumber Tests and Unit Tests Can Be Run With
mvn clean install

## Tech Stack

Java 8
Springboot
Cucumber (Functional Tests)

## Time Complexity

Post transaction endpoint takes O(1) as data is put into a concurrent hashmap.
Get statistics endpoint takes O(1) as data is filtered and proceesed using java 8 streams.

