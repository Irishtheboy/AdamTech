import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/EditProfile.css";

const EditProfile = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "John Doe",
    email: "johndoe@example.com",
    phone: "+27 123 456 789",
    password: "",
    confirmPassword: "",
    address: "123 Main Street, Cape Town, 8000",
    profilePic: "https://via.placeholder.com/120",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert("Profile updated successfully!");
    navigate("/account");
  };

  return (
      <div className="edit-profile-container">
        <h1 className="edit-profile-title">Edit Profile</h1>

        <form className="edit-profile-form" onSubmit={handleSubmit}>
          {/* Profile Picture */}
          <div className="profile-pic-section">
            <img
                src={formData.profilePic}
                alt="Profile"
                className="profile-pic-preview"
            />
            <button type="button" className="orange-btn small-btn">
              Change Picture
            </button>
          </div>

          {/* Full Name */}
          <label className="form-label">Full Name</label>
          <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              className="form-input"
              required
          />

          {/* Email */}
          <label className="form-label">Email Address</label>
          <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="form-input"
              required
          />

          {/* Phone */}
          <label className="form-label">Phone Number</label>
          <input
              type="tel"
              name="phone"
              value={formData.phone}
              onChange={handleChange}
              className="form-input"
          />

          {/* Address */}
          <label className="form-label">Shipping Address</label>
          <textarea
              name="address"
              value={formData.address}
              onChange={handleChange}
              className="form-input textarea"
          />

          {/* Password */}
          <label className="form-label">New Password</label>
          <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className="form-input"
              placeholder="Leave blank to keep current password"
          />

          {/* Confirm Password */}
          <label className="form-label">Confirm New Password</label>
          <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              className="form-input"
          />

          {/* Buttons */}
          <div className="form-buttons">
            <button
                type="button"
                className="gray-btn"
                onClick={() => navigate("/account")}
            >
              Cancel
            </button>
            <button type="submit" className="orange-btn">
              Save Changes
            </button>
          </div>
        </form>
      </div>
  );
};

export default EditProfile;
