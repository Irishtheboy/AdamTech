import React, { useState, useEffect } from 'react';

const Settings = () => {
  const [notifications, setNotifications] = useState(true);
  const [darkMode, setDarkMode] = useState(false);

  // Apply dark mode when the state changes
  useEffect(() => {
    if (darkMode) {
      document.documentElement.classList.add('dark-mode');
    } else {
      document.documentElement.classList.remove('dark-mode');
    }

    // Optional: Save to localStorage
    localStorage.setItem('darkMode', darkMode);
  }, [darkMode]);

  // Optional: Load saved preference on component mount
  useEffect(() => {
    const savedDarkMode = localStorage.getItem('darkMode') === 'true';
    setDarkMode(savedDarkMode);
  }, []);

  const handleSave = () => {
    // Your save logic here
    alert('Settings saved!');
  };

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

          <button className="save-btn" onClick={handleSave}>
            Save Changes
          </button>
        </div>

        {/* Inline CSS */}
        <style>{`
        .settings-container {
          background: #fff;
          border-radius: 8px;
          padding: 20px;
          box-shadow: 0 1px 3px rgba(0,0,0,0.08);
          max-width: 400px;
          transition: all 0.3s ease;
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

        /* Dark Mode Styles */
        .dark-mode {
          background-color: #1a1a1a;
          color: #ffffff;
        }

        .dark-mode .settings-container {
          background: #2d2d2d;
          color: #ffffff;
        }

        .dark-mode .setting-item label {
          color: #ffffff;
        }

        .dark-mode .save-btn {
          background: #ff8c42;
        }

        .dark-mode .save-btn:hover {
          background: #ff7b29;
        }

        /* Base body styles */
        body {
          margin: 0;
          padding: 20px;
          font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
          background-color: #f5f5f5;
          transition: background-color 0.3s ease, color 0.3s ease;
        }
      `}</style>
      </>
  );
};

export default Settings;