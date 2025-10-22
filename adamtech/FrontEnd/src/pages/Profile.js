import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Profile = ({ user }) => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
    });

    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const [profileData, setProfileData] = useState(null);

    // Fetch complete user profile data using email
    useEffect(() => {
        const fetchUserProfile = async () => {
            if (!user || !user.email) return;

            try {
                const token = localStorage.getItem('token');
                const userEmail = user.email;

                if (!token || !userEmail) {
                    console.log('No token or email found');
                    return;
                }

                console.log('Fetching profile for email:', userEmail);

                // Fetch complete user profile from backend using EMAIL
                const response = await axios.get(
                    `http://localhost:8080/adamtech/customer/read/${userEmail}`,
                    {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json'
                        }
                    }
                );

                console.log('Full profile data received:', response.data);
                setProfileData(response.data);

                // Populate form with complete user data
                const userProfile = response.data;
                setFormData({
                    firstName: userProfile.firstName || userProfile.firstname || '',
                    lastName: userProfile.lastName || userProfile.lastname || '',
                    email: userProfile.email || user.email || '',
                    phoneNumber: userProfile.phoneNumber || userProfile.phone || userProfile.phonenumber || '',
                });

            } catch (error) {
                console.error('Error fetching user profile:', error);
                console.log('Will use basic user data instead');

                // Fallback to basic user data
                setFormData({
                    firstName: user.firstName || user.firstname || '',
                    lastName: user.lastName || user.lastname || '',
                    email: user.email || '',
                    phoneNumber: user.phoneNumber || user.phone || user.phonenumber || '',
                });
            }
        };

        fetchUserProfile();
    }, [user]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
        // Clear messages when user starts typing
        setMessage('');
        setError('');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!user) {
            setError('Please log in to update your profile.');
            return;
        }

        setLoading(true);
        setMessage('');
        setError('');

        try {
            const token = localStorage.getItem('token');
            const userEmail = user.email;

            if (!token || !userEmail) {
                setError('Authentication token or email not found. Please log in again.');
                setLoading(false);
                return;
            }

            console.log('Updating profile with data:', formData);

            // Use the correct update endpoint - your backend expects the full Customer object
            const updatePayload = {
                firstName: formData.firstName,
                lastName: formData.lastName,
                email: formData.email,
                phoneNumber: formData.phoneNumber,
                // Include other required fields if needed
                role: user.role || 'ROLE_USER'
            };

            const response = await axios.put(
                `http://localhost:8080/adamtech/customer/update`,
                updatePayload,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                }
            );

            setMessage('Profile updated successfully!');
            console.log('Updated user:', response.data);

            // Update localStorage with new user data
            const updatedUser = {
                ...user,
                ...formData,
                // Ensure backward compatibility
                firstname: formData.firstName,
                lastname: formData.lastName,
                phonenumber: formData.phoneNumber
            };
            localStorage.setItem('user', JSON.stringify(updatedUser));

        } catch (error) {
            console.error('Error updating profile:', error);
            console.log('Error response:', error.response);

            if (error.response?.status === 401) {
                setError('Session expired. Please log in again.');
            } else if (error.response?.status === 403) {
                setError('You do not have permission to update this profile.');
            } else if (error.response?.status === 400) {
                setError(`Invalid data: ${error.response.data?.message || 'Please check your input'}`);
            } else if (error.response?.data) {
                setError(`Error: ${error.response.data.message || 'Failed to update profile'}`);
            } else if (error.request) {
                setError('Network error. Please check your connection.');
            } else {
                setError('Failed to update profile. Please try again.');
            }
        } finally {
            setLoading(false);
        }
    };

    // Debug component to see what data is available
    const DebugInfo = () => {
        if (!user) return null;

        return (
            <div style={{
                backgroundColor: '#f8f9fa',
                padding: '10px',
                borderRadius: '8px',
                marginBottom: '20px',
                fontSize: '12px',
                color: '#666'
            }}>
                <strong>Debug Info:</strong><br />
                User object keys: {Object.keys(user).join(', ')}<br />
                User email: {user.email}<br />
                Profile data: {profileData ? Object.keys(profileData).join(', ') : 'Loading...'}
            </div>
        );
    };

    if (!user) {
        return (
            <div style={{
                textAlign: 'center',
                padding: '50px',
                backgroundColor: '#f8f9fa',
                borderRadius: '12px',
                margin: '20px'
            }}>
                <h2 style={{ color: '#6c757d', marginBottom: '20px' }}>Please log in to view your profile.</h2>
                <button
                    onClick={() => window.location.href = '/login'}
                    style={{
                        backgroundColor: '#ff6600',
                        color: 'white',
                        padding: '12px 24px',
                        border: 'none',
                        borderRadius: '8px',
                        cursor: 'pointer',
                        fontSize: '16px'
                    }}
                >
                    Go to Login
                </button>
            </div>
        );
    }

    return (
        <div style={{
            maxWidth: '600px',
            margin: '0 auto',
            padding: '20px',
            fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif"
        }}>
            <div style={{
                backgroundColor: 'white',
                borderRadius: '16px',
                padding: '30px',
                boxShadow: '0 4px 20px rgba(0,0,0,0.1)'
            }}>
                <h1 style={{
                    textAlign: 'center',
                    marginBottom: '30px',
                    color: 'orange',
                    fontSize: '2rem',
                    fontWeight: '600'
                }}>
                    My Profile
                </h1>

                {/* Debug Info */}
                <DebugInfo />

                {/* Success Message */}
                {message && (
                    <div style={{
                        padding: '12px 16px',
                        backgroundColor: '#d4edda',
                        color: '#155724',
                        border: '1px solid #c3e6cb',
                        borderRadius: '8px',
                        marginBottom: '20px',
                        textAlign: 'center'
                    }}>
                        ✅ {message}
                    </div>
                )}

                {/* Error Message */}
                {error && (
                    <div style={{
                        padding: '12px 16px',
                        backgroundColor: '#f8d7da',
                        color: '#721c24',
                        border: '1px solid #f5c6cb',
                        borderRadius: '8px',
                        marginBottom: '20px',
                        textAlign: 'center'
                    }}>
                        ❌ {error}
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    <div style={{
                        display: 'flex',
                        flexDirection: 'column',
                        gap: '20px',
                        marginBottom: '20px'
                    }}>
                        {/* First Name */}
                        <div style={{ display: 'flex', flexDirection: 'column' }}>
                            <label style={{
                                marginBottom: '8px',
                                fontWeight: '500',
                                color: '#555'
                            }}>
                                First Name
                            </label>
                            <input
                                type="text"
                                name="firstName"
                                value={formData.firstName}
                                onChange={handleChange}
                                placeholder="Enter your first name"
                                required
                                disabled={loading}
                                style={{
                                    padding: '12px 15px',
                                    borderRadius: '8px',
                                    border: formData.firstName ? '1px solid #ddd' : '1px solid #ff6b6b',
                                    fontSize: '14px',
                                    transition: 'border-color 0.3s',
                                    backgroundColor: loading ? '#f8f9fa' : 'white'
                                }}
                                onFocus={(e) => e.target.style.borderColor = '#ff6600'}
                                onBlur={(e) => e.target.style.borderColor = formData.firstName ? '#ddd' : '#ff6b6b'}
                            />
                            {!formData.firstName && (
                                <small style={{ color: '#ff6b6b', marginTop: '5px' }}>
                                    First name is required
                                </small>
                            )}
                        </div>

                        {/* Last Name */}
                        <div style={{ display: 'flex', flexDirection: 'column' }}>
                            <label style={{
                                marginBottom: '8px',
                                fontWeight: '500',
                                color: '#555'
                            }}>
                                Last Name
                            </label>
                            <input
                                type="text"
                                name="lastName"
                                value={formData.lastName}
                                onChange={handleChange}
                                placeholder="Enter your last name"
                                required
                                disabled={loading}
                                style={{
                                    padding: '12px 15px',
                                    borderRadius: '8px',
                                    border: formData.lastName ? '1px solid #ddd' : '1px solid #ff6b6b',
                                    fontSize: '14px',
                                    transition: 'border-color 0.3s',
                                    backgroundColor: loading ? '#f8f9fa' : 'white'
                                }}
                                onFocus={(e) => e.target.style.borderColor = '#ff6600'}
                                onBlur={(e) => e.target.style.borderColor = formData.lastName ? '#ddd' : '#ff6b6b'}
                            />
                            {!formData.lastName && (
                                <small style={{ color: '#ff6b6b', marginTop: '5px' }}>
                                    Last name is required
                                </small>
                            )}
                        </div>

                        {/* Email */}
                        <div style={{ display: 'flex', flexDirection: 'column' }}>
                            <label style={{
                                marginBottom: '8px',
                                fontWeight: '500',
                                color: '#555'
                            }}>
                                Email Address
                            </label>
                            <input
                                type="email"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
                                placeholder="Email Address"
                                required
                                disabled={loading}
                                style={{
                                    padding: '12px 15px',
                                    borderRadius: '8px',
                                    border: '1px solid #ddd',
                                    fontSize: '14px',
                                    transition: 'border-color 0.3s',
                                    backgroundColor: loading ? '#f8f9fa' : 'white'
                                }}
                                onFocus={(e) => e.target.style.borderColor = '#ff6600'}
                                onBlur={(e) => e.target.style.borderColor = '#ddd'}
                            />
                        </div>

                        {/* Phone Number */}
                        <div style={{ display: 'flex', flexDirection: 'column' }}>
                            <label style={{
                                marginBottom: '8px',
                                fontWeight: '500',
                                color: '#555'
                            }}>
                                Phone Number
                            </label>
                            <input
                                type="tel"
                                name="phoneNumber"
                                value={formData.phoneNumber}
                                onChange={handleChange}
                                placeholder="Enter your phone number"
                                required
                                disabled={loading}
                                style={{
                                    padding: '12px 15px',
                                    borderRadius: '8px',
                                    border: formData.phoneNumber ? '1px solid #ddd' : '1px solid #ff6b6b',
                                    fontSize: '14px',
                                    transition: 'border-color 0.3s',
                                    backgroundColor: loading ? '#f8f9fa' : 'white'
                                }}
                                onFocus={(e) => e.target.style.borderColor = '#ff6600'}
                                onBlur={(e) => e.target.style.borderColor = formData.phoneNumber ? '#ddd' : '#ff6b6b'}
                            />
                            {!formData.phoneNumber && (
                                <small style={{ color: '#ff6b6b', marginTop: '5px' }}>
                                    Phone number is required
                                </small>
                            )}
                        </div>
                    </div>

                    {/* Submit Button */}
                    <div style={{ textAlign: 'center', marginTop: '30px' }}>
                        <button
                            type="submit"
                            disabled={loading}
                            style={{
                                backgroundColor: loading ? '#ccc' : '#ff6600',
                                color: '#fff',
                                padding: '14px 40px',
                                borderRadius: '8px',
                                border: 'none',
                                fontSize: '16px',
                                fontWeight: '600',
                                cursor: loading ? 'not-allowed' : 'pointer',
                                transition: 'all 0.3s',
                                minWidth: '200px'
                            }}
                            onMouseOver={(e) => {
                                if (!loading) e.target.style.backgroundColor = '#e55b00';
                            }}
                            onMouseOut={(e) => {
                                if (!loading) e.target.style.backgroundColor = '#ff6600';
                            }}
                        >
                            {loading ? (
                                <>
                                    <span style={{ marginRight: '8px' }}>⏳</span>
                                    Saving Changes...
                                </>
                            ) : (
                                <>
                                    <span style={{ marginRight: '8px' }}>💾</span>
                                    Save Profile
                                </>
                            )}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Profile;