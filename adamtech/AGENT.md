# AdamTech E-commerce Backend - Agent Documentation

## Project Overview
AdamTech is a Spring Boot e-commerce backend application with comprehensive functionality for product management, user authentication, shopping cart operations, and order processing.

## 🚀 Build & Run Commands

### Build
```bash
mvn clean compile
```

### Test
```bash
mvn test
mvn test -Dtest="*FactoryTest"          # Run all factory tests (160 tests)
mvn test -Dtest="FileUploadServiceTest"  # Run file upload tests
```

### Run Application
```bash
mvn spring-boot:run
# Application runs on http://localhost:8080/adamtech
```

## 📁 Project Structure

### Main Packages
- **`za.co.admatech.domain`** - JPA entities and enums
- **`za.co.admatech.controller`** - REST API controllers
- **`za.co.admatech.service`** - Business logic services
- **`za.co.admatech.repository`** - Data access repositories
- **`za.co.admatech.factory`** - Factory classes for object creation
- **`za.co.admatech.config`** - Configuration classes (Security, CORS, WebSocket)
- **`za.co.admatech.dto`** - Data transfer objects

### Key Features
- **User Authentication** - JWT-based with BCrypt password encoding
- **Product Management** - CRUD operations with categories and inventory
- **Shopping Cart** - Multi-item cart functionality
- **Order Processing** - Complete order lifecycle management
- **File Upload** - Image upload system for product management
- **WebSocket Support** - Real-time communication capabilities

## 🗄️ Database Configuration

### MySQL Connection
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/adamtechdb_2?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=admin
```

### Database Scripts
- **`schema.sql`** - Complete database schema with indexes
- **`data.sql`** - Default users and basic data
- **`data-init.sql`** - Extended sample data

### Default Users
- **Admin**: username: `admin`, password: `admin123`
- **User**: username: `user`, password: `user123`

## 🔧 Configuration

### Security
- JWT token expiration: 24 hours
- CORS enabled for localhost:3000, localhost:3001
- Role-based access control (USER, ADMIN)

### File Upload
- Max file size: 10MB
- Allowed types: JPEG, JPG, PNG, GIF, WEBP
- Upload directory: `uploads/images/`

## 🧪 Testing

### Test Coverage
- **Factory Tests**: 160 unit tests covering all factory classes
- **Service Tests**: Comprehensive service layer testing with mocks
- **Controller Tests**: REST API endpoint testing
- **Total Tests**: 364+ tests

### Test Patterns
- JUnit 5 with Mockito
- `@ExtendWith(MockitoExtension.class)`
- Arrange-Act-Assert pattern
- Comprehensive edge case coverage

## 📡 API Endpoints

### Public Endpoints
- `POST /api/auth/login` - User authentication
- `POST /api/auth/register` - User registration
- `GET /api/products/**` - Product catalog
- `GET /api/categories/**` - Category listings

### Admin Endpoints
- `POST /api/files/upload/image` - Image upload
- `DELETE /api/files/images/{fileName}` - Image deletion
- Product management (CRUD)

### User Endpoints
- `GET/POST /api/cart/**` - Shopping cart operations
- `GET/POST /api/orders/**` - Order management

## 🔍 Common Issues & Solutions

### Compilation Issues
1. **CORS Configuration Conflict**: Fixed - uses single CorsConfig
2. **Enum Value Mismatch**: Use `IN_STOCK`, `OUT_OF_STOCK`, `LOW_STOCK`
3. **Method Name Issues**: Domain classes use standard getter/setter names

### Circular Dependencies
- Enabled with `spring.main.allow-circular-references=true`
- SecurityConfig → UserService → PasswordEncoder → SecurityConfig

### Testing Issues
- Spring Boot integration tests may fail due to context loading
- Unit tests (Factory, Service with @Mock) work reliably
- Use `@WebMvcTest` for controller tests

## 🏗️ Architecture Patterns

### Domain-Driven Design
- Clear separation between domain, service, and presentation layers
- Factory pattern for object creation
- Repository pattern for data access

### Security
- JWT authentication filter chain
- Method-level security with `@PreAuthorize`
- CORS configuration for frontend integration

## 📈 Performance Considerations
- Database indexes on frequently queried columns
- Connection pooling with HikariCP
- Lazy loading for JPA relationships
- File upload size limits

## 🔄 Recent Additions
- **FileUploadService** - Secure image upload with validation
- **Enhanced Testing** - 160+ factory tests and service tests
- **Database Scripts** - Complete schema and sample data
- **CORS Fix** - Resolved configuration conflicts
- **Security Updates** - Fixed circular dependencies
