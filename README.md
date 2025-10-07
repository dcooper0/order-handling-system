Order Handling System (Spring Boot + ActiveMQ + WebSocket)

A two-application demo showing asynchronous order processing using Spring Boot, ActiveMQ JMS, and WebSockets.

This project simulates an e-commerce order workflow:
1. An Order Handling Application accepts orders via REST.
2. Orders are placed on a JMS queue for payment and inventory processing.
3. A separate Order Consumer Application consumes and processes those orders.
4. Processed and rejected orders are pushed back to the frontend via WebSocket.

Designed to demonstrate clean architecture, message-driven communication, and real-time updates.

---------------------------------------------------------------

Architecture Overview

Frontend (index.html) 
v
OrderHandlingApplication
- REST Controller (/orders/placeOrder)
- Sends Order to ActiveMQ Queue (PaymentAndStockChecks)
v
ActiveMQ Broker
v
OrderConsumerApplication
- Listens for orders
- Simulates payment + inventory checks
- Sends processed/rejected Orders to JMS Queues
v
OrderHandlingApplication
- JMS listener updates browser clients via WebSocket (/topic/orders)
---------------------------------------------------------------

Technologies used

- Java 17
- Spring Boot 3
- Spring JMS (ActiveMQ)
- Spring WebSocket / STOMP
- Spring REST Client
- ActiveMQ classic
- SockJS + STOMP.js frontend
- Maven (multi-module project)

---------------------------------------------------------------

Modules

- order-shared - Shared domain models ('Order', 'Customer', 'Address', 'OrderStatus'). 
- OrderHandlingApplication - REST API and WebSocket endpoint. Produces JMS messages. 
- OrderConsumerApplication - JMS consumer that validates and processes orders. 

---------------------------------------------------------------
Running the demo

1. Start ActiveMQ (UI available at http://localhost:8161) with user/pass "admin".

2. Run the Applications:
	In two terminals:
    
		cd OrderHandlingApplication
		mvn spring-boot:run
		
		cd OrderConsumerApplication
		mvn spring-boot:run
		
	By default OrderHandlingApplication uses port 8080 and OrderConsumerApplication uses port 8081.

3. Open the demo frontend: [http://localhost:8080/index.html](http://localhost:8080/index.html), you’ll see a simple order form and live order updates via WebSocket.

---------------------------------------------------------------

Demo Details

Available Stock:
  Guitar: 3
  Amplifier: 2
  Microphone: 0 (out of stock)

Customer Names:
  You must use a valid customer name from
  [https://jsonplaceholder.typicode.com/users](https://jsonplaceholder.typicode.com/users)
  (e.g. "Leanne Graham")
  
  Mock Payment System:
  - The consumer simulates payment validation with hardcoded logic ('dummy valid details').
  - This keeps the focus on architecture, message flow, and error handling — not third-party payment integration.
  - Failures can be triggered manually by changing the dummy details in 'OrderHandler'.

Flow:
  1. Place order via form.
  2. Order sent to queue 'PaymentAndStockChecks'.
  3. Consumer validates stock & payment, then sends to 'ProcessedOrders' or 'RejectedOrders'.
  4. Handling app WebSocket updates UI in real time.

---------------------------------------------------------------

Example POST Request 

```json
{
    "customerName": "Patricia Lebsack",
    "productName": "Guitar"
}

---------------------------------------------------------------

Logging Highlights

- All services use 'SLF4J' with consistent structured logging.
- JMS errors are wrapped in custom exceptions (QueueProcessingException).
- Both apps include centralised error handling.

---------------------------------------------------------------

Possible Enhancements

- Docker Compose setup for ActiveMQ + both services.
- Swagger UI (OpenAPI) for the REST endpoint.
- Externalised payment API mock.
- JPA persistence for order history.

---------------------------------------------------------------

License

MIT - free for use and modification.
