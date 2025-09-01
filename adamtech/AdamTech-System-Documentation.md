# AdamTech E-Commerce Backend System Documentation

## Table of Contents
1. [System Overview](#system-overview)
2. [Architecture](#architecture)
3. [Domain Model](#domain-model)
4. [Business Rules](#business-rules)
5. [Functional Requirements](#functional-requirements)
6. [Non-Functional Requirements](#non-functional-requirements)
7. [API Endpoints](#api-endpoints)
8. [Technology Stack](#technology-stack)
9. [Database Configuration](#database-configuration)

---

## System Overview

AdamTech is a comprehensive e-commerce backend system built with Spring Boot that provides complete functionality for managing an online store. The system handles customer management, product catalog, shopping cart operations, order processing, payment handling, inventory tracking, and wishlist management.

### Core Business Purpose
- **B2C E-commerce Platform**: Enable customers to browse products, manage shopping carts, place orders, and process payments
- **Inventory Management**: Track product availability and stock levels
- **Customer Relationship Management**: Handle customer registration, authentication, and profile management
- **Order Fulfillment**: Complete order lifecycle from cart to payment processing

---

## Architecture

The system follows a **layered architecture** pattern with clear separation of concerns:

### Layer Structure
```
├── Presentation Layer (Controllers)
├── Business Logic Layer (Services)
├── Data Access Layer (Repositories)
├── Domain Layer (Entities & Value Objects)
└── Configuration Layer (Security & CORS)
```

### Design Patterns Implemented
- **Repository Pattern**: Data access abstraction
- **Service Layer Pattern**: Business logic encapsulation
- **Builder Pattern**: Object construction (all domain entities)
- **Factory Pattern**: Object creation utilities
- **DTO Pattern**: Data transfer between layers

### Package Structure
- `za.co.admatech.domain` - Core business entities
- `za.co.admatech.service` - Business logic implementation
- `za.co.admatech.repository` - Data access layer
- `za.co.admatech.controller` - REST API endpoints
- `za.co.admatech.config` - System configuration
- `za.co.admatech.factory` - Object creation utilities
- `za.co.admatech.DTO` - Data transfer objects

---

## Domain Model

### Core Entities

#### Customer Entity
- **Primary Key**: `customerId` (Long, auto-generated)
- **Attributes**: firstName, lastName, email, phoneNumber
- **Relationships**:
  - One-to-One with Address (cascading)
  - One-to-One with Cart (bidirectional)
- **Business Rules**: Email serves as unique identifier for login
- **Builder Pattern**: Immutable object construction

#### Product Entity  
- **Primary Key**: `productId` (Long, auto-generated)
- **Attributes**: name, description, sku, categoryId
- **Embedded Value Object**: Money (price)
- **Special Features**: 
  - Image support via `byte[] imageData` (LONGBLOB)
  - SKU for inventory tracking
- **Business Rules**: Each product has unique SKU

#### Order Entity
- **Primary Key**: `id` (Long, auto-generated)
- **Attributes**: orderDate, orderStatus (enum), totalAmount (Money)
- **Relationships**:
  - Many-to-One with Customer
  - One-to-Many with OrderItem (cascading)
- **Status Workflow**: PENDING → CONFIRMED → SHIPPED → DELIVERED → COMPLETED
- **JSON Handling**: Identity management to prevent circular references

#### Cart Entity
- **Primary Key**: `cartId` (Long, auto-generated)
- **Relationships**:
  - One-to-One with Customer
  - One-to-Many with CartItem (cascading)
- **Business Rules**: Each customer has exactly one cart
- **Builder Features**: Direct product addition capability

#### Payment Entity
- **Primary Key**: `id` (Long, auto-generated)
- **Attributes**: paymentDate, amount (Money), paymentStatus (enum)
- **Relationships**: Many-to-One with Order
- **Status Types**: PENDING, COMPLETED, FAILED, REFUNDED

### Value Objects

#### Money (Embeddable)
- **Attributes**: amount (int), currency (String)
- **Purpose**: Ensures consistent monetary value handling across entities
- **Design**: Immutable value object with builder pattern

### Enumerations

#### OrderStatus
- PENDING, CONFIRMED, SHIPPED, DELIVERED, COMPLETED, CANCELLED, RETURNED
- **Business Logic**: Defines order lifecycle states

#### PaymentStatus  
- PENDING, COMPLETED, FAILED, REFUNDED
- **Business Logic**: Tracks payment processing states

#### InventoryStatus
- IN_STOCK, LOW_STOCK, OUT_OF_STOCK
- **Business Logic**: Manages product availability

---

## Business Rules

### Customer Management
1. **Email Uniqueness**: Each customer must have a unique email address
2. **Cart Association**: Every customer automatically gets one cart upon creation
3. **Address Requirement**: Customers must have associated address for orders
4. **Authentication**: Login uses email-based identification

### Product Management
1. **SKU Uniqueness**: Each product must have a unique Stock Keeping Unit
2. **Price Validation**: All products must have valid Money value objects
3. **Image Storage**: Product images stored as binary data in database
4. **Category Classification**: Products organized by categoryId

### Order Processing
1. **Order Lifecycle**: Orders follow strict status progression
2. **Total Calculation**: Order total calculated from constituent OrderItems
3. **Customer Association**: All orders must be linked to valid customer
4. **Date Tracking**: Order date automatically set on creation

### Cart Operations
1. **Single Cart Rule**: One cart per customer maximum
2. **Item Quantity**: CartItems track quantity of each product
3. **Product Reference**: Cart items reference but don't own products
4. **Persistence**: Cart state maintained across sessions

### Payment Processing
1. **Order Link**: Payments must reference valid orders
2. **Amount Matching**: Payment amount should match order total
3. **Status Tracking**: Payment status progression monitored
4. **Date Recording**: Payment date logged for audit trails

### Inventory Management
1. **Stock Levels**: Inventory tracks available product quantities
2. **Status Automation**: Inventory status calculated from quantity levels
3. **Product Binding**: Each inventory record links to specific product

---

## Functional Requirements

### Customer Management (FR001-FR007)
- **FR001**: Customer registration with personal details and address
- **FR002**: Customer authentication via email-based login
- **FR003**: Customer profile viewing and updating
- **FR004**: Customer listing for administrative purposes
- **FR005**: Customer account deletion
- **FR006**: Session management for authenticated users
- **FR007**: Customer lookup by email address

### Product Catalog (FR008-FR014)
- **FR008**: Product creation with details, pricing, and images
- **FR009**: Product information retrieval by ID
- **FR010**: Product catalog browsing (all products)
- **FR011**: Product information updates
- **FR012**: Product removal from catalog
- **FR013**: Product image upload and storage
- **FR014**: Product categorization and SKU management

### Shopping Cart (FR015-FR021)
- **FR015**: Add products to customer cart
- **FR016**: Remove items from cart
- **FR017**: Update item quantities in cart
- **FR018**: View cart contents and totals
- **FR019**: Clear entire cart contents
- **FR020**: Cart persistence across sessions
- **FR021**: Cart item count and value calculations

### Order Management (FR022-FR029)
- **FR022**: Convert cart to order
- **FR023**: Order status tracking and updates
- **FR024**: Order history retrieval
- **FR025**: Order cancellation (when applicable)
- **FR026**: Order item management
- **FR027**: Order total calculations
- **FR028**: Order date and timeline tracking
- **FR029**: Order-to-customer relationship management

### Payment Processing (FR030-FR036)
- **FR030**: Payment creation for orders
- **FR031**: Payment status monitoring
- **FR032**: Payment amount validation
- **FR033**: Payment date recording
- **FR034**: Payment method handling
- **FR035**: Payment failure management
- **FR036**: Payment refund processing

### Inventory Management (FR037-FR042)
- **FR037**: Stock level tracking per product
- **FR038**: Inventory status updates
- **FR039**: Low stock notifications
- **FR040**: Out-of-stock prevention
- **FR041**: Inventory adjustment capabilities
- **FR042**: Inventory reporting and analytics

### Wishlist Management (FR043-FR047)
- **FR043**: Add products to customer wishlist
- **FR044**: Remove items from wishlist
- **FR045**: View wishlist contents
- **FR046**: Move items from wishlist to cart
- **FR047**: Wishlist sharing capabilities

---

## Non-Functional Requirements

### Performance Requirements (NFR001-NFR005)
- **NFR001**: API response time < 500ms for standard operations
- **NFR002**: Support concurrent user sessions (100+ simultaneous users)
- **NFR003**: Database query optimization for large product catalogs
- **NFR004**: Efficient image storage and retrieval
- **NFR005**: Cart operations real-time responsiveness

### Security Requirements (NFR006-NFR010)
- **NFR006**: HTTPS enforcement for all communications
- **NFR007**: Session-based authentication management
- **NFR008**: CORS configuration for cross-origin requests
- **NFR009**: SQL injection prevention via JPA/Hibernate
- **NFR010**: Input validation on all API endpoints

### Scalability Requirements (NFR011-NFR015)
- **NFR011**: Horizontal scaling capability
- **NFR012**: Database connection pooling
- **NFR013**: Stateless service design
- **NFR014**: Microservice-ready architecture
- **NFR015**: Load balancer compatibility

### Availability Requirements (NFR016-NFR020)
- **NFR016**: 99.9% system uptime target
- **NFR017**: Graceful error handling and recovery
- **NFR018**: Database transaction consistency
- **NFR019**: Service health monitoring
- **NFR020**: Automatic database schema updates

### Maintainability Requirements (NFR021-NFR025)
- **NFR021**: Clean architecture with separation of concerns
- **NFR022**: Comprehensive unit and integration testing
- **NFR023**: Detailed API documentation
- **NFR024**: Logging and debugging capabilities
- **NFR025**: Configuration externalization

### Data Requirements (NFR026-NFR030)
- **NFR026**: MySQL database persistence
- **NFR027**: ACID transaction compliance
- **NFR028**: Data integrity via foreign key constraints
- **NFR029**: Automatic timestamp tracking
- **NFR030**: Image data binary storage optimization

---

## API Endpoints

### Customer Management Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/customer/create` | Create new customer |
| GET | `/customer/read/{id}` | Get customer by ID |
| PUT | `/customer/update` | Update customer details |
| DELETE | `/customer/delete/{id}` | Delete customer |
| GET | `/customer/getAll` | List all customers |
| GET | `/customer/me` | Get logged-in customer |
| POST | `/customer/login` | Customer authentication |

### Product Management Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/product/create` | Add new product |
| GET | `/product/read/{id}` | Get product details |
| PUT | `/product/update` | Update product |
| DELETE | `/product/delete/{id}` | Remove product |
| GET | `/product/getAll` | List all products |

### Cart Management Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/cart/create` | Create customer cart |
| GET | `/cart/read/{id}` | Get cart contents |
| PUT | `/cart/update` | Update cart |
| DELETE | `/cart/delete/{id}` | Clear cart |

### Order Management Endpoints  
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/order/create` | Create new order |
| GET | `/order/read/{id}` | Get order details |
| PUT | `/order/update` | Update order status |
| DELETE | `/order/delete/{id}` | Cancel order |
| GET | `/order/getAll` | List all orders (DTO) |

### Payment Management Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/payment/create` | Process payment |
| GET | `/payment/read/{id}` | Get payment details |
| PUT | `/payment/update` | Update payment status |
| DELETE | `/payment/delete/{id}` | Delete payment record |
| GET | `/payment/getAll` | List all payments |

---

## Technology Stack

### Core Framework
- **Spring Boot 3.2.5**: Main application framework
- **Java 21**: Programming language
- **Maven**: Dependency management and build tool

### Data Layer
- **Spring Data JPA**: Data persistence abstraction
- **Hibernate**: ORM implementation
- **MySQL Connector/J 9.2.0**: Database driver
- **MySQL Database**: Primary data storage

### Web Layer
- **Spring Web MVC**: REST API framework  
- **Spring Security**: Authentication and authorization
- **CORS Configuration**: Cross-origin request handling
- **Jackson**: JSON serialization/deserialization

### Utilities & Tools
- **Lombok**: Boilerplate code reduction
- **Apache Commons Validator 1.9.0**: Input validation
- **OkHttp 4.11.0**: HTTP client for external services
- **Gson 2.10.1**: Additional JSON processing
- **Spring DevTools**: Development-time enhancements

### Testing Framework
- **Spring Boot Test**: Comprehensive testing support
- **JUnit**: Unit testing framework
- **Integration Testing**: Full application context testing

---

## Database Configuration

### Connection Details
```properties
Database URL: jdbc:mysql://localhost:3306/adamtechdb
Auto-create Database: true
Username: root
Password: password (configurable)
Driver: com.mysql.cj.jdbc.Driver
```

### JPA/Hibernate Settings
```properties
DDL Strategy: update (auto-schema management)
SQL Logging: enabled (development)
SQL Formatting: enabled
Connection Pool: HikariCP (default)
Show SQL: true (debug mode)
```

### Server Configuration
```properties
Context Path: /adamtech
Server Port: 8080
Bean Override: allowed
```

### Database Schema Features
- **Auto-generation**: Primary keys with IDENTITY strategy
- **Cascade Operations**: Automatic relationship management
- **Foreign Key Constraints**: Data integrity enforcement
- **LONGBLOB Support**: Large image data storage
- **Enum Persistence**: String-based enum storage
- **Embedded Objects**: Money value object persistence

---

## Security Architecture

### Authentication Strategy
- **Session-based**: HttpSession management
- **Email-based Login**: Customer identification via email
- **Stateful Sessions**: Session attribute storage
- **Simple Authentication**: No password validation (prototype)

### Authorization Configuration
- **Permissive Access**: All endpoints publicly accessible
- **CSRF Disabled**: For REST API compatibility  
- **Form Login Disabled**: API-only authentication
- **HTTP Basic Disabled**: Session-based approach

### CORS Configuration
- **Cross-Origin Support**: Frontend integration enabled
- **Flexible Origins**: Configurable allowed origins
- **Method Support**: All HTTP methods allowed
- **Header Support**: Custom headers permitted

---

## Development & Testing

### Code Quality Standards
- **Builder Pattern Usage**: Consistent object construction
- **Interface Segregation**: Service interfaces for all implementations
- **Factory Pattern**: Centralized object creation
- **Exception Handling**: Graceful error management
- **JSON Circular Reference Prevention**: @JsonIdentityInfo usage

### Testing Strategy
- **Unit Testing**: Service layer testing
- **Integration Testing**: Full application context
- **Repository Testing**: Data access validation
- **Controller Testing**: API endpoint validation
- **Mock Testing**: Isolated component testing

### Development Features
- **Hot Reload**: DevTools integration
- **SQL Debugging**: Query logging and formatting
- **Profile Support**: Environment-specific configuration
- **Bean Override**: Development flexibility

---

This documentation provides a comprehensive overview of the AdamTech e-commerce backend system, covering all aspects from architecture to implementation details. The system is designed for scalability, maintainability, and extensibility while providing robust e-commerce functionality.
