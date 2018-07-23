# transaction-statistics

transaction-statistics is a restful API for our statistics. The main use case for this API is to
calculate realtime statistic from the last 60 seconds. There will be two APIs, one of them is
called every time a transaction is made. It is also the sole input of this rest API. The other one
returns the statistic based of the transactions of the last 60 seconds.
Specs

### APIS

##### POST /transactions

Every Time a new transaction happened, this endpoint will be called.
Body:
{
"amount": 12.3,
"timestamp": 1478192204000
}

Where:
+ amount - transaction amount
+ timestamp - transaction time in epoch in millis in UTC time zone (this is not current timestamp)

Returns: Empty body with either 201 or 204.
201 - in case of success
204 - if transaction is older than 60 seconds

Sample request 




##### GET /statistics

This is the main endpoint of this task, this endpoint have to execute in constant time and
memory (O(1)). It returns the statistic based on the transactions which happened in the last 60
seconds.

Returns:
{
"sum": 1000,
"avg": 100,
"max": 200,
"min": 50,
"count": 10
}

Where:
  + sum is a double specifying the total sum of transaction value in the last 60 seconds
  + avg is a double specifying the average amount of transaction value in the last 60 seconds
  + max is a double specifying single highest transaction value in the last 60 seconds
  + min is a double specifying single lowest transaction value in the last 60 seconds
  + count is a long specifying the total number of transactions happened in the last 60seconds
  
  
##### To build JAR
`mvn clean install`

##### To RUN JAR
`Java -jar transaction-statistics-1.0-SNAPSHOT.jar`

##### To access apis in localhost
+ for /transactions `http://localhost:8080/transactions`
+ for /statistics `http://localhost:8080/statistics`






