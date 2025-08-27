# AdamTech Testing Documentation

## Test Coverage Overview

This document outlines the comprehensive testing strategy implemented for the AdamTech e-commerce application.

## Test Types Implemented

### 1. Unit Tests

#### Service Layer Tests
- **CustomerServiceTest**: Tests all CRUD operations for customer service
- **ProductServiceTest**: Tests all CRUD operations for product service  
- **WishlistServiceTest**: Tests wishlist functionality including customer and product relationships
- **PaymentServiceTest**: Tests payment processing and order relationships
- **OrderServiceTest**: Tests order management and item relationships

**Features Tested:**
- Create operations with valid data
- Read operations for existing and non-existing entities
- Update operations with modified data
- Delete operations for existing and non-existing entities
- GetAll operations for entity collections
- Null value handling
- Repository interaction verification

#### Factory Tests
- **CustomerFactoryTest**: Tests customer object creation and builder pattern
- **ProductFactoryTest**: Tests product object creation with embedded Money objects
- **MoneyFactoryTest**: Tests money object creation with different currencies and amounts
- **AddressFactoryTest**: Tests address object creation
- **OrderFactoryTest**: Tests order object creation with relationships

**Features Tested:**
- Valid object creation with all parameters
- Null value handling for optional parameters
- Unique instance creation
- Relationship preservation
- Boundary value testing (min/max values)
- Builder pattern functionality

### 2. Integration Tests

#### Controller Tests
- **CustomerControllerTest**: Tests REST endpoints with security annotations
- **ProductControllerTest**: Tests product API with role-based access
- **AuthenticationControllerTest**: Tests user registration and login
- **WishlistControllerTest**: Tests wishlist management endpoints

**Features Tested:**
- HTTP method handling (GET, POST, PUT, DELETE)
- Request/response JSON serialization
- Security role enforcement
- CORS configuration
- Error handling and status codes
- Authentication requirements
- Authorization based on user roles

### 3. Security Tests

#### Authentication Tests
- User registration with different roles
- User login with valid/invalid credentials
- JWT token generation and validation
- Role-based access control
- Unauthorized access attempts

#### Authorization Tests
- CUSTOMER role access to customer endpoints
- ADMIN role access to admin-only endpoints
- MANAGER role access to product management
- Cross-role access validation

## Test Framework and Tools

### Dependencies Used
- **JUnit 5**: Primary testing framework
- **Mockito**: Mocking framework for unit tests
- **Spring Boot Test**: Integration testing support
- **Spring Security Test**: Security testing utilities
- **MockMvc**: Web layer testing
- **TestContainers**: Database integration testing (if needed)

### Testing Annotations
- `@ExtendWith(MockitoExtension.class)`: For Mockito integration
- `@WebMvcTest`: For controller layer testing
- `@MockBean`: For mocking Spring beans
- `@WithMockUser`: For security context testing
- `@PreAuthorize`: For method-level security testing

## Test Structure and Conventions

### Naming Conventions
- Test class: `{ClassUnderTest}Test`
- Test method: `{methodName}_Should{ExpectedBehavior}_When{Condition}`

### Test Organization
```
src/test/java/za/co/admatech/
├── controller/          # Controller integration tests
├── service/            # Service unit tests  
├── factory/            # Factory unit tests
├── security/           # Security-specific tests
└── integration/        # Full integration tests
```

### AAA Pattern
All tests follow the Arrange-Act-Assert pattern:
```java
@Test
void methodName_ShouldReturnExpected_WhenValidInput() {
    // Given (Arrange)
    // Setup test data and mocks
    
    // When (Act)
    // Execute the method under test
    
    // Then (Assert)
    // Verify the results
}
```

## Security Testing Strategy

### JWT Testing
- Token generation with valid user details
- Token validation with expired tokens
- Token extraction and user detail retrieval
- Refresh token functionality

### Role-Based Access Testing
- CUSTOMER role can access:
  - Own customer data
  - Product catalog (read-only)
  - Own orders and cart
  - Wishlist management

- MANAGER role can access:
  - Product management (create, update)
  - Customer data (read)
  - Order management
  
- ADMIN role can access:
  - All customer operations (including delete)
  - All product operations
  - All order operations
  - Payment management

### Endpoint Security Testing
Each secured endpoint is tested for:
- Authenticated access with proper role
- Unauthorized access (no token)
- Forbidden access (wrong role)
- Token expiration handling

## Test Data Management

### Test Data Builders
Factory classes are used to create consistent test data:
```java
Customer testCustomer = CustomerFactory.buildCustomer(
    1L, "John", "Doe", "john@example.com", address, cart, phone
);
```

### Mock Data Strategy
- Repository layers are mocked in service tests
- Service layers are mocked in controller tests
- External dependencies are mocked where applicable

## Test Execution

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CustomerServiceTest

# Run tests with coverage
mvn test jacoco:report

# Run integration tests only
mvn test -Dtest=**/*IntegrationTest
```

### Test Profiles
Different test profiles can be configured:
- `test`: Default test profile with H2 in-memory database
- `integration-test`: Profile for integration testing with TestContainers
- `security-test`: Profile focused on security testing

## Coverage Goals

### Target Coverage
- **Line Coverage**: 80% minimum
- **Branch Coverage**: 70% minimum
- **Method Coverage**: 90% minimum

### Coverage Areas
- Service layer: 90%+ coverage
- Controller layer: 85%+ coverage
- Factory classes: 100% coverage
- Security components: 80%+ coverage

## Continuous Integration

### Test Automation
Tests are automatically executed on:
- Code commits
- Pull requests
- Scheduled nightly builds
- Release deployments

### Quality Gates
- All tests must pass before merge
- Coverage thresholds must be met
- Security tests must pass
- Performance tests within acceptable limits

## Best Practices Followed

### Test Independence
- Each test can run in isolation
- No dependencies between test methods
- Clean setup and teardown for each test

### Test Readability
- Clear test method names describing scenarios
- Descriptive assertions with custom messages
- Well-organized test data and setup

### Test Maintainability
- Factory pattern for test data creation
- Shared test utilities for common operations
- Parameterized tests for multiple scenarios
- Regular refactoring to reduce duplication

### Error Testing
- Exception handling verification
- Boundary condition testing
- Invalid input validation
- Error message verification

## Future Testing Enhancements

### Planned Additions
1. **Performance Tests**: Load testing for high-traffic scenarios
2. **End-to-End Tests**: Full user journey testing with Selenium
3. **Database Integration Tests**: Real database testing with TestContainers
4. **API Contract Tests**: Consumer-driven contract testing
5. **Chaos Engineering**: Resilience testing under failure conditions

### Test Automation Improvements
1. **Parallel Test Execution**: Faster feedback cycles
2. **Test Result Reporting**: Enhanced reporting with trends
3. **Mutation Testing**: Code quality assessment
4. **Property-Based Testing**: Automated edge case discovery

This comprehensive testing strategy ensures the AdamTech application is robust, secure, and maintainable while providing quick feedback to developers during the development process.
