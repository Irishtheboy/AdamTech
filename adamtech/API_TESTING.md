# AdamTech API Testing Guide

## Prerequisites
1. Start the backend server: `mvn spring-boot:run`
2. Server runs on: `http://localhost:8080/adamtech`

## Test Endpoints with cURL

### 1. Test Customer Registration

```bash
curl -X POST http://localhost:8080/adamtech/customer/create \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "password": "password123",
    "address": {
      "street": "123 Main St",
      "city": "Cape Town",
      "province": "Western Cape",
      "zipCode": "8001"
    }
  }'
```

**Expected Response:**
```json
{
  "data": {
    "customerId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "address": {
      "addressId": 1,
      "streetNumber": 123,
      "streetName": "Main St",
      "suburb": "",
      "city": "Cape Town",
      "province": "Western Cape",
      "postalCode": 8001
    },
    "cart": null
  },
  "message": "Customer created successfully",
  "success": true
}
```

### 2. Test Customer Login

```bash
curl -X POST http://localhost:8080/adamtech/customer/login \
  -H "Content-Type: application/json" \
  -c cookies.txt \
  -d '{
    "email": "john.doe@example.com",
    "password": "anypassword"
  }'
```

**Expected Response:**
```json
{
  "user": {
    "customerId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "address": {
      "addressId": 1,
      "streetNumber": 123,
      "streetName": "Main St",
      "suburb": "",
      "city": "Cape Town",
      "province": "Western Cape",
      "postalCode": 8001
    },
    "cart": null
  },
  "message": "Login successful",
  "success": true
}
```

### 3. Test Get Current User (requires login session)

```bash
curl -X GET http://localhost:8080/adamtech/customer/me \
  -b cookies.txt
```

**Expected Response:**
```json
{
  "customerId": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "address": {
    "addressId": 1,
    "streetNumber": 123,
    "streetName": "Main St",
    "suburb": "",
    "city": "Cape Town",
    "province": "Western Cape",
    "postalCode": 8001
  },
  "cart": null
}
```

### 4. Test Logout

```bash
curl -X POST http://localhost:8080/adamtech/customer/logout \
  -b cookies.txt
```

**Expected Response:**
```json
{
  "message": "Logged out successfully",
  "success": true
}
```

### 5. Test Access After Logout

```bash
curl -X GET http://localhost:8080/adamtech/customer/me \
  -b cookies.txt
```

**Expected Response:**
```json
{
  "error": "Not authenticated",
  "success": false
}
```

## Frontend Testing with Browser

1. Open Developer Tools (F12)
2. Go to Console tab
3. Test registration:

```javascript
fetch('http://localhost:8080/adamtech/customer/create', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  credentials: 'include',
  body: JSON.stringify({
    firstName: "Jane",
    lastName: "Smith",
    email: "jane.smith@example.com",
    phoneNumber: "+9876543210",
    password: "password456",
    address: {
      street: "456 Oak Ave",
      city: "Johannesburg",
      province: "Gauteng",
      zipCode: "2000"
    }
  })
})
.then(response => response.json())
.then(data => console.log('Registration:', data));
```

4. Test login:

```javascript
fetch('http://localhost:8080/adamtech/customer/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  credentials: 'include',
  body: JSON.stringify({
    email: "jane.smith@example.com",
    password: "anypassword"
  })
})
.then(response => response.json())
.then(data => console.log('Login:', data));
```

5. Test get current user:

```javascript
fetch('http://localhost:8080/adamtech/customer/me', {
  method: 'GET',
  credentials: 'include'
})
.then(response => response.json())
.then(data => console.log('Current User:', data));
```

## Common Test Scenarios

### Valid Registration
- All required fields provided
- Valid email format
- Valid phone number
- Complete address information

### Invalid Registration Tests
1. **Missing email:**
```json
{
  "firstName": "Test",
  "lastName": "User"
}
```
Expected: `{"error": "Email is required", "success": false}`

2. **Duplicate email:**
Register same email twice
Expected: `{"error": "Email already exists", "success": false}`

### Valid Login
- Registered email address
- Any password (password validation not implemented yet)

### Invalid Login Tests
1. **Missing email:**
```json
{"password": "test"}
```
Expected: `{"error": "Email is required", "success": false}`

2. **Non-existent email:**
```json
{
  "email": "nonexistent@example.com",
  "password": "test"
}
```
Expected: `{"error": "Invalid email or password", "success": false}`

## Troubleshooting

### CORS Issues
If you get CORS errors:
1. Ensure frontend is running on http://localhost:3000
2. If using different port, update `CorsConfig.java`
3. Make sure `credentials: 'include'` is set in frontend

### Session Issues
If sessions aren't persisting:
1. Check that cookies are being set (Developer Tools > Network > Response Headers)
2. Ensure `credentials: 'include'` in all requests
3. Verify JSESSIONID cookie is being sent in subsequent requests

### Database Issues
If getting database errors:
1. Ensure MySQL is running
2. Check database connection in `application.properties`
3. Verify database `adamtechdb` exists

### Validation Errors
Common validation failures:
1. Missing required fields
2. Invalid email format
3. Numeric fields (street number, postal code) with non-numeric values
