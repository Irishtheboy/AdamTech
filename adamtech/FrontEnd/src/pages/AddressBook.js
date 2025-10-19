import React, { useState, useEffect } from "react";
import axios from "axios";

const AddressBook = ({ user }) => {
  const [addresses, setAddresses] = useState([]);
  const [formData, setFormData] = useState({
    streetNumber: "",
    streetName: "",
    suburb: "",
    city: "",
    province: "",
    postalCode: "",
  });
  const [isEditing, setIsEditing] = useState(false);
  const [editId, setEditId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  // Load addresses for current user on mount
  useEffect(() => {
    if (user) {
      fetchUserAddresses();
    }
  }, [user]);

  // Prefill form with the first address (if exists)
useEffect(() => {
  if (addresses.length > 0 && !isEditing) {
    const firstAddress = addresses[0];
    setFormData({
      streetNumber: firstAddress.streetNumber?.toString() || "",
      streetName: firstAddress.streetName || "",
      suburb: firstAddress.suburb || "",
      city: firstAddress.city || "",
      province: firstAddress.province || "",
      postalCode: firstAddress.postalCode?.toString() || "",
    });
    setEditId(firstAddress.addressId);
    setIsEditing(true); // Optional: mark form as editing
  }
}, [addresses]);



  const fetchUserAddresses = async () => {
    try {
      setLoading(true);
      setError("");
      const token = localStorage.getItem("token");

      // Option 1: Try to get addresses by user ID
      try {
        const response = await axios.get(`http://localhost:8080/adamtech/address/user/${user.id}`, {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
        setAddresses(Array.isArray(response.data) ? response.data : []);
        return;
      } catch (err) {
        console.log('User-specific address endpoint not available, trying getAll...');
      }

      // Option 2: Get all addresses and filter by user
      const response = await axios.get("http://localhost:8080/adamtech/address/getAll", {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      // Filter addresses for current user
      const userAddresses = response.data.filter(address => {
        // Check if address belongs to current user
        return address.customer?.customerId === user.id ||
            address.customer?.email === user.email ||
            address.customerEmail === user.email;
      });

      setAddresses(userAddresses);

    } catch (err) {
      console.error("Error fetching addresses:", err);
      setError("Failed to load your addresses. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
    setError("");
    setSuccess("");
  };

  const handleAddOrUpdate = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    if (!user) {
      setError("Please log in to manage addresses.");
      return;
    }

    // Validation
    if (!formData.streetNumber || !formData.streetName || !formData.postalCode) {
      setError("Street Number, Street Name, and Postal Code are required.");
      return;
    }

    try {
      setLoading(true);
      const token = localStorage.getItem("token");

      // Prepare payload with user information
      const payload = {
        streetNumber: Math.min(Number(formData.streetNumber), 32767),
        postalCode: Math.min(Number(formData.postalCode), 32767),
        streetName: formData.streetName.trim(),
        suburb: formData.suburb.trim(),
        city: formData.city.trim(),
        province: formData.province.trim(),
        customerEmail: user.email, // Link to current user
        customerId: user.id // Link to current user
      };

      if (isEditing) {
        // Update address - make sure it belongs to current user
        const addressToUpdate = addresses.find(addr => addr.addressId === editId);
        if (!addressToUpdate) {
          setError("Address not found.");
          return;
        }

        const response = await axios.put(
            "http://localhost:8080/adamtech/address/update",
            { addressId: editId, ...payload },
            {
              headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
              }
            }
        );
        setAddresses((prev) =>
            prev.map((addr) => (addr.addressId === editId ? response.data : addr))
        );
        setSuccess("Address updated successfully!");
      } else {
        // Create new address for current user
        const response = await axios.post(
            "http://localhost:8080/adamtech/address/create",
            payload,
            {
              headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
              }
            }
        );
        setAddresses((prev) => [...prev, response.data]);
        setSuccess("Address added successfully!");
      }

      resetForm();
    } catch (err) {
      console.error("Error saving address:", err);
      const errorMessage = err.response?.data?.message || "Failed to save address. Please try again.";
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const resetForm = () => {
    setFormData({
      streetNumber: "",
      streetName: "",
      suburb: "",
      city: "",
      province: "",
      postalCode: "",
    });
    setIsEditing(false);
    setEditId(null);
  };

  const handleEdit = (address) => {
    // Verify address belongs to current user before editing
    const isUserAddress = address.customer?.customerId === user.id ||
        address.customer?.email === user.email ||
        address.customerEmail === user.email;

    if (!isUserAddress) {
      setError("You can only edit your own addresses.");
      return;
    }

    setFormData({
      streetNumber: address.streetNumber?.toString() || "",
      streetName: address.streetName || "",
      suburb: address.suburb || "",
      city: address.city || "",
      province: address.province || "",
      postalCode: address.postalCode?.toString() || "",
    });
    setIsEditing(true);
    setEditId(address.addressId);
    setError("");
    setSuccess("");

    document.getElementById("address-form")?.scrollIntoView({ behavior: "smooth" });
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this address?")) {
      return;
    }

    // Verify address belongs to current user before deleting
    const addressToDelete = addresses.find(addr => addr.addressId === id);
    if (!addressToDelete) {
      setError("Address not found.");
      return;
    }

    const isUserAddress = addressToDelete.customer?.customerId === user.id ||
        addressToDelete.customer?.email === user.email ||
        addressToDelete.customerEmail === user.email;

    if (!isUserAddress) {
      setError("You can only delete your own addresses.");
      return;
    }

    try {
      setLoading(true);
      const token = localStorage.getItem("token");

      await axios.delete(`http://localhost:8080/adamtech/address/delete/${id}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      setAddresses((prev) => prev.filter((addr) => addr.addressId !== id));
      setSuccess("Address deleted successfully!");
    } catch (err) {
      console.error("Error deleting address:", err);
      setError("Failed to delete address. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const provinces = [
    "Eastern Cape", "Free State", "Gauteng", "KwaZulu-Natal",
    "Limpopo", "Mpumalanga", "North West", "Northern Cape", "Western Cape"
  ];

  if (!user) {
    return (
        <div style={{
          padding: '60px 20px',
          textAlign: 'center',
          backgroundColor: 'white',
          borderRadius: '12px',
          boxShadow: '0 2px 10px rgba(0,0,0,0.1)'
        }}>
          <div style={{ fontSize: '3rem', marginBottom: '20px' }}>🔒</div>
          <h2 style={{ marginBottom: '10px', color: '#333' }}>Access Required</h2>
          <p style={{ color: '#666' }}>Please log in to manage your addresses.</p>
        </div>
    );
  }

  if (loading && addresses.length === 0) {
    return (
        <div style={{
          padding: '60px 20px',
          textAlign: 'center',
          backgroundColor: 'white',
          borderRadius: '12px',
          boxShadow: '0 2px 10px rgba(0,0,0,0.1)'
        }}>
          <div style={{ fontSize: '3rem', marginBottom: '20px' }}>🏠</div>
          <h2 style={{ marginBottom: '10px', color: '#333' }}>Loading Your Addresses</h2>
          <p style={{ color: '#666' }}>Please wait while we fetch your saved addresses...</p>
        </div>
    );
  }

  return (
      <div style={{
        maxWidth: "1000px",
        margin: "0 auto",
        padding: "20px",
        fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif"
      }}>
        {/* Header */}
        <div style={{
          backgroundColor: "white",
          borderRadius: "12px",
          padding: "30px",
          boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
          marginBottom: "20px"
        }}>
          <h1 style={{
            marginBottom: "10px",
            color: "#333",
            fontSize: "2rem",
            fontWeight: "600"
          }}>
            My Address Book
          </h1>
          <p style={{ color: "#666", marginBottom: "20px" }}>
            Manage your delivery addresses for faster checkout
          </p>

          {/* User Info */}
          <div style={{
            display: "flex",
            gap: "20px",
            fontSize: "14px",
            color: "#666"
          }}>
            <span>User: <strong>{user.firstName} {user.lastName}</strong></span>
            <span>Email: <strong>{user.email}</strong></span>
            <span>Your Addresses: <strong>{addresses.length}</strong></span>
          </div>
        </div>

        {/* Messages */}
        {error && (
            <div style={{
              padding: "15px",
              backgroundColor: "#f8d7da",
              color: "#721c24",
              border: "1px solid #f5c6cb",
              borderRadius: "8px",
              marginBottom: "20px",
              textAlign: "center"
            }}>
              ❌ {error}
            </div>
        )}

        {success && (
            <div style={{
              padding: "15px",
              backgroundColor: "#d4edda",
              color: "#155724",
              border: "1px solid #c3e6cb",
              borderRadius: "8px",
              marginBottom: "20px",
              textAlign: "center"
            }}>
              ✅ {success}
            </div>
        )}

        {/* Address Form */}
        <div id="address-form" style={{
          backgroundColor: "white",
          borderRadius: "12px",
          padding: "30px",
          boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
          marginBottom: "30px"
        }}>
          <h2 style={{
            marginBottom: "20px",
            color: "#333",
            fontSize: "1.5rem"
          }}>
            {isEditing ? "Edit Your Address" : "Add New Address"}
          </h2>

          <form onSubmit={handleAddOrUpdate}>
            <div style={{
              display: "grid",
              gridTemplateColumns: "1fr 1fr",
              gap: "20px",
              marginBottom: "20px"
            }}>
              {/* Street Number */}
              <div style={{ display: "flex", flexDirection: "column" }}>
                <label style={{
                  marginBottom: "8px",
                  fontWeight: "500",
                  color: "#555"
                }}>
                  Street Number *
                </label>
                <input
                    type="number"
                    name="streetNumber"
                    value={formData.streetNumber}
                    onChange={handleChange}
                    placeholder="e.g., 123"
                    required
                    min="1"
                    max="32767"
                    disabled={loading}
                    style={{
                      padding: "12px 15px",
                      borderRadius: "8px",
                      border: "1px solid #ddd",
                      fontSize: "14px",
                      transition: "border-color 0.3s",
                      backgroundColor: loading ? "#f8f9fa" : "white"
                    }}
                />
              </div>

              {/* Street Name */}
              <div style={{ display: "flex", flexDirection: "column" }}>
                <label style={{
                  marginBottom: "8px",
                  fontWeight: "500",
                  color: "#555"
                }}>
                  Street Name *
                </label>
                <input
                    type="text"
                    name="streetName"
                    value={formData.streetName}
                    onChange={handleChange}
                    placeholder="e.g., Main Street"
                    required
                    disabled={loading}
                    style={{
                      padding: "12px 15px",
                      borderRadius: "8px",
                      border: "1px solid #ddd",
                      fontSize: "14px",
                      transition: "border-color 0.3s",
                      backgroundColor: loading ? "#f8f9fa" : "white"
                    }}
                />
              </div>

              {/* Suburb */}
              <div style={{ display: "flex", flexDirection: "column" }}>
                <label style={{
                  marginBottom: "8px",
                  fontWeight: "500",
                  color: "#555"
                }}>
                  Suburb
                </label>
                <input
                    type="text"
                    name="suburb"
                    value={formData.suburb}
                    onChange={handleChange}
                    placeholder="e.g., Sandton"
                    disabled={loading}
                    style={{
                      padding: "12px 15px",
                      borderRadius: "8px",
                      border: "1px solid #ddd",
                      fontSize: "14px",
                      transition: "border-color 0.3s",
                      backgroundColor: loading ? "#f8f9fa" : "white"
                    }}
                />
              </div>

              {/* City */}
              <div style={{ display: "flex", flexDirection: "column" }}>
                <label style={{
                  marginBottom: "8px",
                  fontWeight: "500",
                  color: "#555"
                }}>
                  City *
                </label>
                <input
                    type="text"
                    name="city"
                    value={formData.city}
                    onChange={handleChange}
                    placeholder="e.g., Johannesburg"
                    required
                    disabled={loading}
                    style={{
                      padding: "12px 15px",
                      borderRadius: "8px",
                      border: "1px solid #ddd",
                      fontSize: "14px",
                      transition: "border-color 0.3s",
                      backgroundColor: loading ? "#f8f9fa" : "white"
                    }}
                />
              </div>

              {/* Province */}
              <div style={{ display: "flex", flexDirection: "column" }}>
                <label style={{
                  marginBottom: "8px",
                  fontWeight: "500",
                  color: "#555"
                }}>
                  Province *
                </label>
                <select
                    name="province"
                    value={formData.province}
                    onChange={handleChange}
                    required
                    disabled={loading}
                    style={{
                      padding: "12px 15px",
                      borderRadius: "8px",
                      border: "1px solid #ddd",
                      fontSize: "14px",
                      backgroundColor: loading ? "#f8f9fa" : "white",
                      cursor: loading ? "not-allowed" : "pointer"
                    }}
                >
                  <option value="">Select Province</option>
                  {provinces.map(province => (
                      <option key={province} value={province}>{province}</option>
                  ))}
                </select>
              </div>

              {/* Postal Code */}
              <div style={{ display: "flex", flexDirection: "column" }}>
                <label style={{
                  marginBottom: "8px",
                  fontWeight: "500",
                  color: "#555"
                }}>
                  Postal Code *
                </label>
                <input
                    type="number"
                    name="postalCode"
                    value={formData.postalCode}
                    onChange={handleChange}
                    placeholder="e.g., 2000"
                    required
                    min="1"
                    max="32767"
                    disabled={loading}
                    style={{
                      padding: "12px 15px",
                      borderRadius: "8px",
                      border: "1px solid #ddd",
                      fontSize: "14px",
                      transition: "border-color 0.3s",
                      backgroundColor: loading ? "#f8f9fa" : "white"
                    }}
                />
              </div>
            </div>

            {/* Form Actions */}
            <div style={{ display: "flex", gap: "15px", alignItems: "center" }}>
              <button
                  type="submit"
                  disabled={loading}
                  style={{
                    backgroundColor: loading ? "#ccc" : (isEditing ? "orange" : "orange"),
                    color: "white",
                    padding: "14px 30px",
                    borderRadius: "8px",
                    border: "none",
                    fontSize: "16px",
                    fontWeight: "600",
                    cursor: loading ? "not-allowed" : "pointer",
                    transition: "all 0.3s",
                    minWidth: "160px"
                  }}
              >
                {loading ? (
                    <>
                      <span style={{ marginRight: "8px" }}>⏳</span>
                      {isEditing ? "Updating..." : "Saving..."}
                    </>
                ) : (
                    <>
                      <span style={{ marginRight: "8px" }}></span>
                      {isEditing ? "Update Address" : "Save Address"}
                    </>
                )}
              </button>

              {isEditing && (
                  <button
                      type="button"
                      onClick={resetForm}
                      disabled={loading}
                      style={{
                        backgroundColor: "#6c757d",
                        color: "white",
                        padding: "14px 20px",
                        borderRadius: "8px",
                        border: "none",
                        fontSize: "16px",
                        cursor: loading ? "not-allowed" : "pointer",
                        transition: "background-color 0.3s"
                      }}
                  >
                    Cancel Edit
                  </button>
              )}
            </div>
          </form>
        </div>

        {/* User's Address List */}
        <div style={{
          backgroundColor: "white",
          borderRadius: "12px",
          padding: "30px",
          boxShadow: "0 2px 10px rgba(0,0,0,0.1)"
        }}>
          <h2 style={{
            marginBottom: "20px",
            color: "#333",
            fontSize: "1.5rem"
          }}>
            Your Saved Addresses ({addresses.length})
          </h2>

          {addresses.length === 0 ? (
              <div style={{
                padding: "40px 20px",
                textAlign: "center",
                color: "#666"
              }}>
                <div style={{ fontSize: "3rem", marginBottom: "20px" }}>🏠</div>
                <h3 style={{ marginBottom: "10px" }}>No Addresses Saved</h3>
                <p>Add your first address above to get started with faster checkouts.</p>
              </div>
          ) : (
              <div style={{
                display: "grid",
                gridTemplateColumns: "repeat(auto-fit, minmax(350px, 1fr))",
                gap: "20px",
              }}>
                {addresses.map((address) => (
                    <div
                        key={address.addressId}
                        style={{
                          background: "#fff",
                          padding: "20px",
                          borderRadius: "12px",
                          border: "2px solid #e9ecef",
                          display: "flex",
                          flexDirection: "column",
                          justifyContent: "space-between",
                          minHeight: "140px",
                          transition: "all 0.3s",
                        }}
                    >
                      <div>
                        <p style={{
                          margin: "0 0 10px 0",
                          fontSize: "15px",
                          lineHeight: "1.4",
                          color: "#333"
                        }}>
                          <strong>{address.streetNumber} {address.streetName}</strong>
                          {address.suburb && <>, {address.suburb}</>}
                          <br />
                          {address.city}, {address.province}
                          <br />
                          {address.postalCode}
                        </p>
                      </div>

                      <div style={{ display: "flex", gap: "10px", marginTop: "15px" }}>
                        <button
                            onClick={() => handleEdit(address)}
                            disabled={loading}
                            style={{
                              backgroundColor: "orange",
                              color: "#fff",
                              padding: "8px 16px",
                              borderRadius: "6px",
                              border: "none",
                              cursor: loading ? "not-allowed" : "pointer",
                              fontSize: "14px",
                              transition: "background-color 0.2s",
                              flex: 1
                            }}
                        >
                           Edit
                        </button>
                        <button
                            onClick={() => handleDelete(address.addressId)}
                            disabled={loading}
                            style={{
                              backgroundColor: "#dc3545",
                              color: "#fff",
                              padding: "8px 16px",
                              borderRadius: "6px",
                              border: "none",
                              cursor: loading ? "not-allowed" : "pointer",
                              fontSize: "14px",
                              transition: "background-color 0.2s",
                              flex: 1
                            }}
                        >
                           Delete
                        </button>
                      </div>
                    </div>
                ))}
              </div>
          )}
        </div>
      </div>
  );
};

export default AddressBook;