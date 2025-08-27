# AdamTech E-commerce API Documentation

## Project Overview

AdamTech is a Spring Boot-based e-commerce application that provides RESTful APIs for managing customers, products, orders, payments, shopping carts, wishlists, and inventory.

## Technologies Used

- **Framework**: Spring Boot 3.2.5
- **Java Version**: 21
- **Build Tool**: Maven
- **Database**: MySQL
- **ORM**: Spring Data JPA with Hibernate
- **Validation**: Spring Boot Starter Validation + Commons Validator
- **Development**: Spring Boot DevTools
- **Testing**: Spring Boot Test Starter

## Dependencies

### Core Dependencies
- `spring-boot-starter-web` - Web layer and REST API support
- `spring-boot-starter-data-jpa` - JPA/Hibernate database integration
- `spring-boot-starter-validation` - Input validation support
- `mysql-connector-j` (v9.2.0) - MySQL database driver
- `commons-validator` (v1.9.0) - Additional validation utilities
- `lombok` - Code generation for boilerplate reduction

### Security Dependencies
- `spring-boot-starter-security` - Spring Security framework
- `jjwt-api` (v0.12.3) - JWT API
- `jjwt-impl` (v0.12.3) - JWT Implementation
- `jjwt-jackson` (v0.12.3) - JWT Jackson integration

### Development Dependencies
- `spring-boot-devtools` - Development tools and auto-restart
- `spring-boot-starter-test` - Testing framework
- `spring-security-test` - Security testing support

## Database Configuration

**Database**: MySQL  
**URL**: `jdbc:mysql://localhost:3306/adamtechdb?createDatabaseIfNotExist=true`  
**Port**: 3306  
**Username**: root  
**Password**: password (configurable)  

### JPA Configuration
- **DDL Mode**: `update` (creates/updates tables automatically)
- **Show SQL**: Enabled with formatted output
- **SQL Logging**: DEBUG level for SQL statements

## Server Configuration

- **Context Path**: `/adamtech`
- **Port**: 8080
- **Base URL**: `http://localhost:8080/adamtech`

## Security Configuration

### JWT Configuration
- **Secret Key**: Configurable (default provided)
- **Access Token Expiration**: 24 hours (86400000 ms)
- **Refresh Token Expiration**: 7 days (604800000 ms)

### User Roles and Permissions

#### Roles
- **CUSTOMER**: Can manage own profile, orders, cart, wishlist
- **MANAGER**: Can manage products, view orders and customers
- **ADMIN**: Full access to all resources

#### Permissions
- `customer:read`, `customer:update`, `customer:create`, `customer:delete`
- `product:read`, `product:update`, `product:create`, `product:delete`
- `order:read`, `order:update`, `order:create`, `order:delete`
- `payment:read`, `payment:update`, `payment:create`, `payment:delete`

### Authentication
All endpoints (except authentication endpoints) require a valid JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Entity Model

### Core Entities

1. **Customer**
   - Primary Key: `customerId` (Long, auto-generated)
   - Relationships: OneToOne with Address, OneToOne with Cart

2. **Product**
   - Primary Key: `productId` (Long, auto-generated)
   - Embedded: `Money` object for pricing

3. **Order**
   - Primary Key: `id` (Long, auto-generated)
   - Relationships: ManyToOne with Customer, OneToMany with OrderItems
   - Status: Enum (PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, RETURNED)

4. **OrderItem**
   - Primary Key: `id` (Long, auto-generated)
   - Relationships: ManyToOne with Product, ManyToOne with Order

5. **Payment**
   - Primary Key: `id` (Long, auto-generated)
   - Relationships: ManyToOne with Order
   - Status: Enum (PENDING, PROCESSING, COMPLETED, FAILED, CANCELLED, REFUNDED)

6. **Cart**
   - Primary Key: `cartId` (Long, auto-generated)
   - Relationships: OneToOne with Customer, OneToMany with CartItems

7. **CartItem**
   - Primary Key: `cartItemId` (Long, auto-generated)
   - Relationships: ManyToOne with Product, ManyToOne with Cart

8. **Wishlist**
   - Primary Key: `wishlistId` (Long, auto-generated)
   - Relationships: ManyToOne with Customer, ManyToOne with Product

9. **Inventory**
   - Primary Key: `id` (Long, auto-generated)
   - Relationships: OneToOne with Product
   - Status: Enum (IN_STOCK, LOW_STOCK, OUT_OF_STOCK, DISCONTINUED, BACKORDERED)

10. **Address**
    - Primary Key: `addressId` (Long, auto-generated)
    - Embedded in Customer entity

11. **User**
    - Primary Key: `id` (Long, auto-generated)
    - Relationships: OneToOne with Customer
    - Implements UserDetails for Spring Security
    - Role: Enum (CUSTOMER, MANAGER, ADMIN)

### Embedded Objects

**Money**
- Fields: `amount` (int), `currency` (String)
- Used in: Product (price), OrderItem (unitPrice), Order (totalAmount), Payment (amount)

## API Endpoints

All endpoints support CORS from `http://localhost:3000` and require JWT authentication unless specified otherwise.

### Authentication Endpoints (No JWT Required)
**Base Path**: `/api/v1/auth`

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|-----------|
| POST | `/register` | Register new user | RegisterRequest JSON | AuthenticationResponse JSON |
| POST | `/authenticate` | Login user | AuthenticationRequest JSON | AuthenticationResponse JSON |

### Customer Endpoints
**Base Path**: `/customer`  
**Required Roles**: CUSTOMER, ADMIN

| Method | Endpoint | Description | Request Body | Required Role |
|--------|----------|-------------|--------------|---------------|
| POST | `/create` | Create new customer | Customer JSON | CUSTOMER, ADMIN |
| GET | `/read/{customerId}` | Get customer by ID | - | CUSTOMER, ADMIN |
| PUT | `/update` | Update customer | Customer JSON | CUSTOMER, ADMIN |
| DELETE | `/delete/{customerId}` | Delete customer | - | ADMIN |
| GET | `/getall` | Get all customers | - | CUSTOMER, ADMIN |

### Product Endpoints
**Base Path**: `/products`

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/create` | Create new product | Product JSON |
| GET | `/read/{productId}` | Get product by ID | - |
| PUT | `/update/{productId}` | Update product | Product JSON |
| DELETE | `/delete/{productId}` | Delete product | - |
| GET | `/getAll` | Get all products | - |

### Order Endpoints
**Base Path**: `/order`

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/create` | Create new order | Order JSON |
| GET | `/read/{orderId}` | Get order by ID | - |
| PUT | `/update` | Update order | Order JSON |
| DELETE | `/delete/{orderId}` | Delete order | - |
| GET | `/getAll` | Get all orders | - |

### Payment Endpoints
**Base Path**: `/payments`

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/create` | Create new payment | Payment JSON |
| GET | `/read/{paymentId}` | Get payment by ID | - |
| PUT | `/update/{paymentId}` | Update payment | Payment JSON |
| DELETE | `/delete/{paymentId}` | Delete payment | - |
| GET | `/getAll` | Get all payments | - |

### OrderItem Endpoints
**Base Path**: `/orderitem`

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/create` | Create new order item | OrderItem JSON |
| GET | `/read/{orderItemId}` | Get order item by ID | - |
| PUT | `/update` | Update order item | OrderItem JSON |
| DELETE | `/delete/{orderItemId}` | Delete order item | - |
| GET | `/getAll` | Get all order items | - |

### Cart Endpoints
**Base Path**: `/cart`

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/create` | Create new cart | Cart JSON |
| GET | `/read/{cartId}` | Get cart by ID | - |
| PUT | `/update` | Update cart | Cart JSON |
| DELETE | `/delete/{cartId}` | Delete cart | - |
| GET | `/getAll` | Get all carts | - |

### CartItem Endpoints
**Base Path**: `/cartitem`

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/create` | Create new cart item | CartItem JSON |
| GET | `/read/{cartItemId}` | Get cart item by ID | - |
| PUT | `/update` | Update cart item | CartItem JSON |
| DELETE | `/delete/{cartItemId}` | Delete cart item | - |
| GET | `/getAll` | Get all cart items | - |

### Wishlist Endpoints
**Base Path**: `/wishlist`

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/create` | Create new wishlist item | Wishlist JSON |
| GET | `/read/{wishlistId}` | Get wishlist item by ID | - |
| PUT | `/update` | Update wishlist item | Wishlist JSON |
| DELETE | `/delete/{wishlistId}` | Delete wishlist item | - |
| GET | `/getall` | Get all wishlist items | - |
| GET | `/customer/{customerId}` | Get wishlist by customer | - |
| GET | `/product/{productId}` | Get wishlist by product | - |

### Address Endpoints
**Base Path**: `/address`

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/create` | Create new address | Address JSON |
| GET | `/read/{addressId}` | Get address by ID | - |
| PUT | `/update` | Update address | Address JSON |
| DELETE | `/delete/{addressId}` | Delete address | - |
| GET | `/getAll` | Get all addresses | - |

## Request/Response Examples

### Authentication

#### Register User
```json
POST /adamtech/api/v1/auth/register
{
    "username": "john_doe",
    "email": "john.doe@example.com",
    "password": "securePassword123",
    "role": "CUSTOMER"
}
```

**Response:**
```json
{
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Login User
```json
POST /adamtech/api/v1/auth/authenticate
{
    "username": "john_doe",
    "password": "securePassword123"
}
```

**Response:**
```json
{
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Create Customer
```json
POST /adamtech/customer/create
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "address": {
        "streetNumber": 123,
        "streetName": "Main Street",
        "suburb": "Downtown",
        "city": "New York",
        "province": "NY",
        "postalCode": 10001
    }
}
```

### Create Product
```json
POST /adamtech/products/create
{
    "name": "Gaming Laptop",
    "description": "High-performance gaming laptop",
    "sku": "LAPTOP-001",
    "price": {
        "amount": 150000,
        "currency": "ZAR"
    },
    "categoryId": "electronics"
}
```

### Create Order
```json
POST /adamtech/order/create
{
    "customer": {
        "customerId": 1
    },
    "orderDate": "2025-08-27",
    "orderStatus": "PENDING",
    "totalAmount": {
        "amount": 150000,
        "currency": "ZAR"
    },
    "orderItems": []
}
```

## Error Handling

- **404 Not Found**: Resource doesn't exist
- **400 Bad Request**: Invalid input data
- **500 Internal Server Error**: Server-side errors

## Architecture

### Layers
1. **Controller Layer**: REST endpoints and request handling
2. **Service Layer**: Business logic implementation
3. **Repository Layer**: Data access using Spring Data JPA
4. **Domain Layer**: Entity models and embedded objects

### Design Patterns
- **Builder Pattern**: Used in all entity classes for object construction
- **Repository Pattern**: Data access abstraction
- **Service Pattern**: Business logic separation
- **MVC Pattern**: Model-View-Controller architecture

## Getting Started

### Prerequisites
- Java 21+
- MySQL Server
- Maven (or use IDE with Maven integration)

### Setup Steps
1. Clone the repository
2. Configure MySQL database:
   - Create database `adamtechdb` or let the application create it
   - Update credentials in `application.properties` if needed
3. Build the project: `mvn clean compile`
4. Run the application: `mvn spring-boot:run`
5. API will be available at: `http://localhost:8080/adamtech`

### Testing
- Use tools like Postman or curl to test the endpoints
- All endpoints return JSON responses
- CORS is configured for frontend integration at `localhost:3000`

## Development Notes
- All entities use Builder pattern for immutability
- JPA relationships are properly configured with cascading
- Foreign key constraints ensure data integrity
- Hibernate will automatically create/update tables based on entity definitions
- SQL queries are logged for debugging purposes
