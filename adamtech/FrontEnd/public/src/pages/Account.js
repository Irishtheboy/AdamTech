import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import '../styles/Account.css';

const Account = () => {
  const [user, setUser] = useState(null);

  useEffect(() => {

    fetch("http://localhost:8080/customer/me", {
      credentials: 'include'
    })
        .then(res => res.json())
        .then(data => setUser(data))
        .catch(err => console.error("Error fetching user:", err));
  }, []);

  if (!user) return <p>Loading account...</p>;

  return (
      <div className="account-container">
        <h1 className="account-title">My Account</h1>

        <div className="account-grid">
          {/* Personal Info */}
          <div className="account-card profile-section">
            <div className="profile-header">
              <img src={user.profilePic || "https://via.placeholder.com/120"} alt="Profile" className="profile-pic" />
              <div>
                <h2 className="section-title">{user.firstName} {user.lastName}</h2>
                <p>Email: {user.email}</p>
                <p>Phone: {user.phoneNumber}</p>
                <Link to="/account/edit" className="orange-btn">Edit Profile</Link>
              </div>
            </div>
          </div>

          {/* Optional: show order history if available */}
          {user.orders && user.orders.length > 0 && (
              <div className="account-card">
                <h2 className="section-title">Order History</h2>
                <ul className="order-list">
                  {user.orders.map(order => (
                      <li key={order.id} className="order-item">
                        <div>
                          <strong>Order #{order.id}</strong> — {order.date}
                          <p>Total: {order.total}</p>
                          <p>Status: {order.status}</p>
                        </div>
                        <button className="orange-btn">Reorder</button>
                      </li>
                  ))}
                </ul>
              </div>
          )}

          {/* Optional: show addresses */}
          {user.addresses && user.addresses.length > 0 && (
              <div className="account-card">
                <h2 className="section-title">Shipping Addresses</h2>
                <div className="address-list">
                  {user.addresses.map((addr, i) => (
                      <div key={i} className="address-item">
                        {addr.street}, {addr.city}, {addr.zip}
                      </div>
                  ))}
                </div>
                <button className="orange-btn">Manage Addresses</button>
              </div>
          )}
        </div>
      </div>
  );
};

export default Account;
