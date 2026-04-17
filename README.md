# Shopping Cart API

Spring Boot based e-commerce shopping cart REST API. This project provides user registration, product management, user addresses, cart creation, cart item management, Swagger/OpenAPI documentation, and a Postman collection for API testing.

## Features

- User registration and user CRUD APIs
- Product CRUD APIs
- User address CRUD APIs
- Default address and address type filtering
- One cart per user
- Add, update, list, clear, and delete cart items
- Product reuse in cart items by existing product id
- Global exception handling with structured error response
- Bean validation for request payloads
- Swagger UI and OpenAPI JSON documentation
- H2 in-memory database for local development
- MySQL driver included for future database setup

## Tech Stack

- Java 21
- Spring Boot 3.5.13
- Spring Web
- Spring Data JPA
- Spring Validation
- H2 Database
- MySQL Connector
- Lombok
- Springdoc OpenAPI / Swagger UI
- Maven

## Project Structure

```text
src/main/java/com/ganesh/ecommerce/cart
|-- Controller
|   |-- ProductController.java
|   |-- UserAddressController.java
|   |-- UserCartController.java
|   |-- UserCartItemController.java
|   `-- UserController.java
|-- config
|   `-- OpenApiConfig.java
|-- dto
|-- entity
|-- exception
|-- mapper
|-- repository
|-- service
`-- ShoppingCartApplication.java
```

## Entity Relationships

```text
User 1 -> many UserAddress
User 1 -> 1 UserCart
UserCart 1 -> many UserCartItem
Product 1 -> many UserCartItem
```

## Requirements

- Java 21
- Maven 3.9 or Maven wrapper included in the project
- Postman, optional

## Run The Project

From the project root:

```powershell
cd D:\SpringBootEcommerceProject\shopping-cart
mvn spring-boot:run
```

Or with Maven wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

The app runs at:

```text
http://localhost:8080
```

## Build And Test

```powershell
mvn -q -DskipTests compile
mvn -q test
```

## Swagger Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/api-docs
```

Grouped docs:

```text
http://localhost:8080/api-docs/users
http://localhost:8080/api-docs/cart
```

## Postman

You can import this collection into Postman:

```text
shopping-cart.postman_collection.json
```

Use this base URL in Postman:

```text
http://localhost:8080
```

Common header:

```text
Content-Type: application/json
```

## Database Configuration

Current local database is H2 in-memory.

```properties
spring.datasource.url=jdbc:h2:mem:shoppingcart
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

Data is cleared when the application stops because H2 is running in memory.

## API Documentation

### User APIs

#### Register User

```http
POST /user/register
```

Request body:

```json
{
  "username": "ganesh_123",
  "email": "ganesh@example.com",
  "password": "securePass@123",
  "userAddress": [
    {
      "street": "123 Main Street",
      "city": "Mumbai",
      "state": "Maharashtra",
      "zip_code": 400001,
      "isDefault": true,
      "addressType": "HOME"
    }
  ],
  "userCart": {
    "userCartItems": [
      {
        "quantity": 2,
        "product": {
          "name": "iPhone 15 Pro",
          "description": "Apple smartphone 256GB",
          "price": 129900.0
        }
      }
    ]
  }
}
```

#### Get All Users

```http
GET /user
```

#### Get User By Id

```http
GET /user/{userId}
```

Example:

```http
GET /user/1
```

#### Update User

```http
PUT /user/{userId}
```

Request body:

```json
{
  "username": "ganesh_updated",
  "email": "ganesh.updated@example.com",
  "password": "newPass@123"
}
```

#### Delete User

```http
DELETE /user/{userId}
```

### Product APIs

#### Create Product

```http
POST /user/product
```

Request body:

```json
{
  "name": "AirPods Pro 2",
  "description": "Wireless earbuds with ANC",
  "price": 24900.0
}
```

#### Get All Products

```http
GET /user/product
```

#### Get Product By Id

```http
GET /user/product/{productId}
```

#### Update Product

```http
PUT /user/product/{productId}
```

Request body:

```json
{
  "name": "AirPods Pro 2",
  "description": "Wireless earbuds with active noise cancellation",
  "price": 23900.0
}
```

#### Delete Product

```http
DELETE /user/product/{productId}
```

### Address APIs

Allowed address types:

```text
HOME, OFFICE, SHIPPING
```

#### Add Address To User

```http
POST /user/address/{userId}
```

Request body:

```json
{
  "street": "456 Park Road",
  "city": "Pune",
  "state": "Maharashtra",
  "zip_code": 411001,
  "isDefault": true,
  "addressType": "HOME"
}
```

#### Get All Addresses For User

```http
GET /user/address/user/{userId}
```

#### Get Address By Id

```http
GET /user/address/{addressId}
```

#### Update Address

```http
PUT /user/address/{addressId}
```

Request body:

```json
{
  "street": "789 New Street",
  "city": "Mumbai",
  "state": "Maharashtra",
  "zip_code": 400002,
  "isDefault": true,
  "addressType": "OFFICE"
}
```

#### Delete Address

```http
DELETE /user/address/{addressId}
```

#### Get Default Address

```http
GET /user/address/user/{userId}/default
```

#### Get Addresses By Type

```http
GET /user/address/user/{userId}/type/{addressType}
```

Example:

```http
GET /user/address/user/1/type/HOME
```

### Cart APIs

Important: Registering a user creates a cart automatically. If a cart already exists for a user, creating another cart for the same user returns a bad request response.

#### Create Cart For User

```http
POST /user/usercart/{userId}
```

Request body:

```json
{
  "userCartItems": [
    {
      "quantity": 1,
      "product": {
        "name": "MacBook Air M3",
        "description": "Apple laptop 13 inch",
        "price": 114900.0
      }
    }
  ]
}
```

#### Get Cart By Cart Id

```http
GET /user/usercart/{userCartId}
```

#### Get Cart By User Id

```http
GET /user/usercart/user/{userId}
```

#### Replace All Cart Items

```http
PUT /user/usercart/{userCartId}
```

Request body with new product:

```json
{
  "userCartItems": [
    {
      "quantity": 3,
      "product": {
        "name": "USB-C Charger",
        "description": "65W fast charger",
        "price": 1999.0
      }
    }
  ]
}
```

Request body with existing product:

```json
{
  "userCartItems": [
    {
      "quantity": 2,
      "product": {
        "id": 1
      }
    }
  ]
}
```

#### Clear Cart Items

```http
DELETE /user/usercart/{userCartId}/items
```

#### Delete Cart

```http
DELETE /user/usercart/{userCartId}
```

### Cart Item APIs

#### Add Item To Cart

```http
POST /user/usercartitem/cart/{userCartId}
```

Request body with new product:

```json
{
  "quantity": 2,
  "product": {
    "name": "Samsung Galaxy S24",
    "description": "Android smartphone 256GB",
    "price": 79999.0
  }
}
```

Request body with existing product:

```json
{
  "quantity": 2,
  "product": {
    "id": 1
  }
}
```

Old URL is also supported:

```http
POST /user/usercartitem/user/{userCartId}
```

#### Get Cart Item By Id

```http
GET /user/usercartitem/{userCartItemId}
```

#### Get All Items For Cart

```http
GET /user/usercartitem/cart/{userCartId}
```

#### Update Cart Item

```http
PUT /user/usercartitem/{userCartItemId}
```

Request body for quantity update:

```json
{
  "quantity": 5
}
```

Request body for product change:

```json
{
  "quantity": 1,
  "product": {
    "id": 2
  }
}
```

#### Delete Cart Item

```http
DELETE /user/usercartitem/{userCartItemId}
```

## Common Response Codes

| Code | Meaning |
| --- | --- |
| 200 | Request successful |
| 201 | Resource created |
| 204 | Resource deleted successfully |
| 400 | Invalid request body, duplicate email, duplicate cart, or invalid enum |
| 404 | User, product, cart, address, or cart item not found |
| 500 | Unexpected server error |

## Error Response Format

```json
{
  "apiPath": "uri=/user/1",
  "statusCode": "NOT_FOUND",
  "errorMessage": "User not found with id: 1",
  "errorTime": "2026-04-17T15:30:45.123456"
}
```

## Recommended Postman Testing Flow

1. Register user using `POST /user/register`.
2. Get users using `GET /user`.
3. Get cart by user id using `GET /user/usercart/user/{userId}`.
4. Create product using `POST /user/product`.
5. Add item to cart using `POST /user/usercartitem/cart/{cartId}`.
6. Get cart details using `GET /user/usercart/{cartId}`.
7. Update cart item using `PUT /user/usercartitem/{itemId}`.
8. Delete cart item using `DELETE /user/usercartitem/{itemId}`.

## Security Status

Spring Security dependency is currently not enabled in the application. The recommended next step for this REST API is JWT based authentication with:

- `/auth/register`
- `/auth/login`
- BCrypt password encoding
- `Authorization: Bearer <token>`
- Role based access for `USER` and `ADMIN`

## Author

Ganesh
