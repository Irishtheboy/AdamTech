import React, { useState } from 'react';

const Settings = () => {
  const [notifications, setNotifications] = useState(true);
  const [darkMode, setDarkMode] = useState(false);

  return (
      <>
        <div className="settings-container">
          <h2>Settings</h2>

          <div className="setting-item">
            <label>
              <input
                  type="checkbox"
                  checked={notifications}
                  onChange={() => setNotifications(!notifications)}
              />
              Enable Notifications
            </label>
          </div>

          <div className="setting-item">
            <label>
              <input
                  type="checkbox"
                  checked={darkMode}
                  onChange={() => setDarkMode(!darkMode)}
              />
              Dark Mode
            </label>
          </div>

          <button className="save-btn">Save Changes</button>
        </div>

        {/* Inline CSS */}
        <style>{`
        .settings-container {
          background: #fff;
          border-radius: 8px;
          padding: 20px;
          box-shadow: 0 1px 3px rgba(0,0,0,0.08);
          max-width: 400px;
        }

        .settings-container h2 {
          font-size: 18px;
          margin-bottom: 20px;
        }

        .setting-item {
          margin-bottom: 15px;
        }

        .setting-item label {
          font-size: 14px;
          color: #333;
          cursor: pointer;
        }

        .save-btn {
          background: #ff6600;
          color: #fff;
          border: none;
          padding: 10px 15px;
          border-radius: 6px;
          cursor: pointer;
          font-size: 14px;
          transition: background 0.2s;
        }

        .save-btn:hover {
          background: #e65c00;
        }
      `}</style>
      </>
  );
};

export default Settings;

