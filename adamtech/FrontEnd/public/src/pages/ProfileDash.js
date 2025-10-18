import React, { useState } from 'react';
import MyOrders from './MyOrders';
import WishlistPage from './WishlistPage';
import Settings from './Settings';
import Profile from './Profile';
import AddressBook from './AddressBook';
import Refunds from './Refunds';

const ProfileDash = ({ user, setUser }) => {
  const [activeTab, setActiveTab] = useState('details');

  if (!user) {
    return (
        <div style={{ textAlign: 'center', padding: '50px' }}>
          <h2>Please log in to view your profile.</h2>
        </div>
    );
  }

  return (
      <>
        <div className="profile-container">
          {/* Sidebar */}
          <aside className="sidebar">
            <h3 className="sidebar-title">Welcome, {user.firstName}</h3>
            <nav>
              <ul>
                <li
                    className={activeTab === 'details' ? 'active' : ''}
                    onClick={() => setActiveTab('details')}
                >
                  My Profile
                </li>
                <li
                    className={activeTab === 'orders' ? 'active' : ''}
                    onClick={() => setActiveTab('orders')}
                >
                  My Orders
                </li>
                <li
                    className={activeTab === 'saved' ? 'active' : ''}
                    onClick={() => setActiveTab('saved')}
                >
                  My Wish List
                </li>
                <li
                    className={activeTab === 'address' ? 'active' : ''}
                    onClick={() => setActiveTab('address')}
                >
                  Address Book
                </li>
                <li
                    className={activeTab === 'refunds' ? 'active' : ''}
                    onClick={() => setActiveTab('refunds')}
                >
                  Refunds/Returns
                </li>
                <li
                    className={activeTab === 'settings' ? 'active' : ''}
                    onClick={() => setActiveTab('settings')}
                >
                  Settings
                </li>
              </ul>
            </nav>
            <div className="help-center">Help center</div>
          </aside>

          {/* Main Content */}
          <main className="main-content">
            {activeTab === 'details' && <Profile user={user} setUser={setUser} />}
            {activeTab === 'saved' && <WishlistPage user={user} />}
            {activeTab === 'orders' && <MyOrders user={user} />}
            {activeTab === 'address' && <AddressBook user={user} />}
            {activeTab === 'refunds' && <Refunds user={user} />}
            {activeTab === 'settings' && <Settings user={user} setUser={setUser} />}
          </main>
        </div>

        {/* Inline CSS */}
        <style>{`
        .profile-container {
          display: flex;
          min-height: 100vh;
          font-family: Arial, sans-serif;
          background: #fafafa;
          color: #333;
        }

        .sidebar {
          width: 220px;
          background: #fff;
          border-right: 1px solid #eee;
          padding: 20px;
        }

        .sidebar-title {
          font-size: 16px;
          font-weight: bold;
          margin-bottom: 20px;
        }

        .sidebar ul {
          list-style: none;
          padding: 0;
        }

        .sidebar ul li {
          padding: 12px 10px;
          cursor: pointer;
          border-radius: 6px;
          margin-bottom: 5px;
          transition: background 0.2s;
        }

        .sidebar ul li:hover {
          background: #f5f5f5;
        }

        .sidebar ul li.active {
          background: #f0f0f0;
          font-weight: bold;
        }

        .help-center {
          margin-top: 30px;
          font-size: 14px;
          color: #777;
        }

        .main-content {
          flex: 1;
          padding: 20px;
        }

        .card {
          background: #fff;
          border-radius: 8px;
          padding: 20px;
          box-shadow: 0 1px 3px rgba(0,0,0,0.08);
        }

        .card h2 {
          font-size: 18px;
          margin-bottom: 20px;
        }
      `}</style>
      </>
  );
};

export default ProfileDash;
