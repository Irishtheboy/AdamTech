import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Profile = ({ user }) => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        idNumber: '',
        phoneNumber: '',
        birthday: '',
        gender: '',
    });

    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');

    // Populate form with logged-in user's details
    useEffect(() => {
        if (user) {
            setFormData({
                firstName: user.firstName || '',
                lastName: user.lastName || '',
                email: user.email || '',
                idNumber: user.idNumber || '',
                phoneNumber: user.phoneNumber || '',
                birthday: user.birthday || '',
                gender: user.gender || '',
            });
        }
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

            if (!token) {
                setError('Authentication token not found. Please log in again.');
                setLoading(false);
                return;
            }

            const response = await axios.put(
                `http://localhost:8080/adamtech/customer/update/${user.id}`,
                formData,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                }
            );

            setMessage('Profile updated successfully!');
            console.log('Updated user:', response.data);

            // Update localStorage with new user data if needed
            const updatedUser = { ...user, ...formData };
            localStorage.setItem('user', JSON.stringify(updatedUser));

        } catch (error) {
            console.error('Error updating profile:', error);

            if (error.response?.status === 401) {
                setError('Session expired. Please log in again.');
            } else if (error.response?.status === 403) {
                setError('You do not have permission to update this profile.');
            } else if (error.response?.status === 400) {
                setError(`Invalid data: ${error.response.data?.message || 'Please check your input'}`);
            } else if (error.response?.data) {
                setError(`Error: ${error.response.data.message || 'Failed to update profile'}`);
            } else {
                setError('Failed to update profile. Please try again.');
            }
        } finally {
            setLoading(false);
        }
    };

    // Format date for display (if needed)
    const formatDateForInput = (dateString) => {
        if (!dateString) return '';

        try {
            const date = new Date(dateString);
            return date.toISOString().split('T')[0];
        } catch (error) {
            return dateString;
        }
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
            maxWidth: '800px',
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
                        display: 'grid',
                        gridTemplateColumns: '1fr 1fr',
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
                                placeholder="First Name"
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
                                placeholder="Last Name"
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

                        {/* Email - Full Width */}
                        <div style={{
                            display: 'flex',
                            flexDirection: 'column',
                            gridColumn: '1 / -1'
                        }}>
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

                        {/* ID Number */}
                        <div style={{ display: 'flex', flexDirection: 'column' }}>
                            <label style={{
                                marginBottom: '8px',
                                fontWeight: '500',
                                color: '#555'
                            }}>
                                ID Number
                            </label>
                            <input
                                type="text"
                                name="idNumber"
                                value={formData.idNumber}
                                onChange={handleChange}
                                placeholder="ID Number"
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
                                placeholder="Phone Number"
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

                        {/* Birthday */}
                        <div style={{ display: 'flex', flexDirection: 'column' }}>
                            <label style={{
                                marginBottom: '8px',
                                fontWeight: '500',
                                color: '#555'
                            }}>
                                Birthday
                            </label>
                            <input
                                type="date"
                                name="birthday"
                                value={formatDateForInput(formData.birthday)}
                                onChange={handleChange}
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

                        {/* Gender */}
                        <div style={{ display: 'flex', flexDirection: 'column' }}>
                            <label style={{
                                marginBottom: '8px',
                                fontWeight: '500',
                                color: '#555'
                            }}>
                                Gender
                            </label>
                            <select
                                name="gender"
                                value={formData.gender}
                                onChange={handleChange}
                                required
                                disabled={loading}
                                style={{
                                    padding: '12px 15px',
                                    borderRadius: '8px',
                                    border: '1px solid #ddd',
                                    fontSize: '14px',
                                    transition: 'border-color 0.3s',
                                    backgroundColor: loading ? '#f8f9fa' : 'white',
                                    cursor: loading ? 'not-allowed' : 'pointer'
                                }}
                                onFocus={(e) => e.target.style.borderColor = '#ff6600'}
                                onBlur={(e) => e.target.style.borderColor = '#ddd'}
                            >
                                <option value="">Select Gender</option>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                                <option value="other">Other</option>
                                <option value="prefer_not_to_say">Prefer not to say</option>
                            </select>
                        </div>
                    </div>

                    {/* Submit Button */}
                    <div style={{ textAlign: 'center', marginTop: '20px' }}>
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
                                if (!loading) e.target.style.backgroundColor = 'orange';
                            }}
                            onMouseOut={(e) => {
                                if (!loading) e.target.style.backgroundColor = 'orange';
                            }}
                        >
                            {loading ? (
                                <>
                                    <span style={{ marginRight: '8px' }}></span>
                                    Saving Changes...
                                </>
                            ) : (
                                <>
                                    <span style={{ marginRight: '8px' }}></span>
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