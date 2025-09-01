# AdamTech Frontend Integration Guide

## Overview
This guide explains how to connect your React frontend to the AdamTech backend API for authentication (login/signup) functionality.

## Backend API Endpoints

### Base URL
```
http://localhost:8080/adamtech
```

### Authentication Endpoints

#### 1. User Registration (Sign Up)
**POST** `/customer/create`

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "password": "password123",
  "address": {
    "street": "123 Main St",
    "city": "Anytown",
    "province": "Province",
    "zipCode": "12345"
  }
}
```

**Success Response (200):**
```json
{
  "data": {
    "customerId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "address": {
      "id": 1,
      "street": "123 Main St",
      "city": "Anytown",
      "province": "Province",
      "zipCode": "12345"
    },
    "cart": null
  },
  "message": "Customer created successfully",
  "success": true
}
```

**Error Response (400):**
```json
{
  "error": "Email already exists",
  "success": false
}
```

#### 2. User Login
**POST** `/customer/login`

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

**Success Response (200):**
```json
{
  "user": {
    "customerId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "address": {
      "id": 1,
      "street": "123 Main St",
      "city": "Anytown",
      "province": "Province",
      "zipCode": "12345"
    },
    "cart": null
  },
  "message": "Login successful",
  "success": true
}
```

**Error Response (401):**
```json
{
  "error": "Invalid email or password",
  "success": false
}
```

**Validation Error (400):**
```json
{
  "error": "Email is required",
  "success": false
}
```

#### 3. Get Current User
**GET** `/customer/me`

**Success Response (200):**
```json
{
  "customerId": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "address": {
    "id": 1,
    "street": "123 Main St",
    "city": "Anytown",
    "province": "Province",
    "zipCode": "12345"
  },
  "cart": null
}
```

**Error Response (401):**
```json
{
  "error": "Not authenticated",
  "success": false
}
```

#### 4. User Logout
**POST** `/customer/logout`

**Success Response (200):**
```json
{
  "message": "Logged out successfully",
  "success": true
}
```

## Frontend Implementation Example

### 1. Authentication Service (authService.js)

```javascript
const API_BASE_URL = 'http://localhost:8080/adamtech';

class AuthService {
  async login(email, password) {
    try {
      const response = await fetch(`${API_BASE_URL}/customer/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include', // Important for session cookies
        body: JSON.stringify({ email, password })
      });

      const data = await response.json();
      
      if (response.ok) {
        return { success: true, user: data.user };
      } else {
        return { success: false, error: data.error || 'Login failed' };
      }
    } catch (error) {
      console.error('Login error:', error);
      return { success: false, error: 'Network error' };
    }
  }

  async register(userData) {
    try {
      const response = await fetch(`${API_BASE_URL}/customer/create`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify(userData)
      });

      const data = await response.json();
      
      if (response.ok) {
        return { success: true, data: data.data };
      } else {
        return { success: false, error: data.error || 'Registration failed' };
      }
    } catch (error) {
      console.error('Registration error:', error);
      return { success: false, error: 'Network error' };
    }
  }

  async getCurrentUser() {
    try {
      const response = await fetch(`${API_BASE_URL}/customer/me`, {
        method: 'GET',
        credentials: 'include'
      });

      if (response.ok) {
        const user = await response.json();
        return { success: true, user };
      } else {
        return { success: false, error: 'Not authenticated' };
      }
    } catch (error) {
      console.error('Get current user error:', error);
      return { success: false, error: 'Network error' };
    }
  }

  async logout() {
    try {
      const response = await fetch(`${API_BASE_URL}/customer/logout`, {
        method: 'POST',
        credentials: 'include'
      });

      if (response.ok) {
        return { success: true };
      } else {
        return { success: false, error: 'Logout failed' };
      }
    } catch (error) {
      console.error('Logout error:', error);
      return { success: false, error: 'Network error' };
    }
  }
}

export const authService = new AuthService();
```

### 2. Login Component

```javascript
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    // Basic validation
    if (!email || !password) {
      setError('Please fill in all fields');
      setLoading(false);
      return;
    }

    if (!email.includes('@')) {
      setError('Please enter a valid email address');
      setLoading(false);
      return;
    }

    try {
      const result = await authService.login(email, password);

      if (result.success) {
        // Redirect to home page or dashboard
        navigate('/');
      } else {
        setError(result.error);
      }
    } catch (err) {
      console.error('Login error:', err);
      setError('An unexpected error occurred. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="Email"
        required
      />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Password"
        required
      />
      <button type="submit" disabled={loading}>
        {loading ? 'Logging in...' : 'Login'}
      </button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </form>
  );
};

export default Login;
```

### 3. SignUp Component

```javascript
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';

const SignUp = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    password: '',
    address: {
      street: '',
      city: '',
      province: '',
      zipCode: ''
    }
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name.startsWith('address.')) {
      const addressField = name.split('.')[1];
      setFormData(prev => ({
        ...prev,
        address: {
          ...prev.address,
          [addressField]: value
        }
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: value
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    // Basic validation
    const requiredFields = ['firstName', 'lastName', 'email', 'phoneNumber', 'password'];
    const requiredAddressFields = ['street', 'city', 'zipCode'];

    for (const field of requiredFields) {
      if (!formData[field].trim()) {
        setError(`${field.replace(/([A-Z])/g, ' $1').toLowerCase()} is required`);
        setLoading(false);
        return;
      }
    }

    for (const field of requiredAddressFields) {
      if (!formData.address[field].trim()) {
        setError(`Address ${field} is required`);
        setLoading(false);
        return;
      }
    }

    if (!formData.email.includes('@')) {
      setError('Please enter a valid email address');
      setLoading(false);
      return;
    }

    if (formData.password.length < 6) {
      setError('Password must be at least 6 characters long');
      setLoading(false);
      return;
    }

    try {
      const result = await authService.register(formData);

      if (result.success) {
        alert('Account created successfully! Please login with your credentials.');
        navigate('/login');
      } else {
        setError(result.error);
      }
    } catch (err) {
      console.error('Registration error:', err);
      setError('An unexpected error occurred. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        name="firstName"
        value={formData.firstName}
        onChange={handleChange}
        placeholder="First Name"
        required
      />
      <input
        type="text"
        name="lastName"
        value={formData.lastName}
        onChange={handleChange}
        placeholder="Last Name"
        required
      />
      <input
        type="email"
        name="email"
        value={formData.email}
        onChange={handleChange}
        placeholder="Email"
        required
      />
      <input
        type="tel"
        name="phoneNumber"
        value={formData.phoneNumber}
        onChange={handleChange}
        placeholder="Phone Number"
        required
      />
      <input
        type="password"
        name="password"
        value={formData.password}
        onChange={handleChange}
        placeholder="Password"
        required
      />
      <input
        type="text"
        name="address.street"
        value={formData.address.street}
        onChange={handleChange}
        placeholder="Street Address"
        required
      />
      <input
        type="text"
        name="address.city"
        value={formData.address.city}
        onChange={handleChange}
        placeholder="City"
        required
      />
      <input
        type="text"
        name="address.province"
        value={formData.address.province}
        onChange={handleChange}
        placeholder="Province"
      />
      <input
        type="text"
        name="address.zipCode"
        value={formData.address.zipCode}
        onChange={handleChange}
        placeholder="Zip Code"
        required
      />
      <button type="submit" disabled={loading}>
        {loading ? 'Creating Account...' : 'Sign Up'}
      </button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </form>
  );
};

export default SignUp;
```

## Important Notes

### 1. Session Management
- The backend uses **session-based authentication** with HTTP cookies
- Always include `credentials: 'include'` in fetch requests
- Sessions persist until logout or server restart

### 2. CORS Configuration
- Backend is configured to accept requests from `http://localhost:3000`
- If your frontend runs on a different port, update the CORS configuration in `CorsConfig.java`

### 3. Password Security
- **Important**: The current implementation does not validate passwords
- Passwords are accepted in the DTO but not checked during login
- You should implement proper password hashing and validation

### 4. Error Handling
- All endpoints return structured error responses with `success` flag
- Check the `success` field to determine if the request was successful
- Display `error` field content to users for feedback

### 5. User State Management
- Use React Context or state management library to store authenticated user data
- Call `/customer/me` on app initialization to restore user session
- Clear user state on logout

## Testing the Integration

1. Start the backend server: `mvn spring-boot:run`
2. Start your React frontend: `npm start`
3. Test registration with valid data
4. Test login with registered user credentials
5. Verify user session persistence by refreshing the page
6. Test logout functionality

## Common Issues and Solutions

### 1. CORS Errors
- Ensure backend CORS configuration matches frontend URL
- Include `credentials: 'include'` in all API calls

### 2. Session Not Persisting
- Make sure `credentials: 'include'` is set in fetch requests
- Check that cookies are being set and sent

### 3. 401 Unauthorized
- User session may have expired
- Redirect to login page and clear user state

### 4. Email Already Exists Error
- Implement proper validation on frontend
- Show meaningful error messages to users
