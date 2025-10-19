import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const AdminDashboard = () => {
  const [users, setUsers] = useState([]);
  const [stats, setStats] = useState({});
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user") || "{}");

    // Check if user is admin
    if (user.role !== "ROLE_ADMIN") {
      alert("Access denied. Admin privileges required.");
      navigate("/");
      return;
    }

    fetchAdminData(token);
  }, [navigate]);

  const fetchAdminData = async (token) => {
    try {
      // Fetch users
      const usersRes = await axios.get("http://localhost:8080/adamtech/admin/users", {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      setUsers(usersRes.data);

      // Calculate stats
      const totalUsers = usersRes.data.length;
      const adminUsers = usersRes.data.filter(user => user.role === "ROLE_ADMIN").length;
      const regularUsers = totalUsers - adminUsers;

      setStats({
        totalUsers,
        adminUsers,
        regularUsers
      });

    } catch (err) {
      console.error("Failed to fetch admin data:", err);
      if (err.response?.status === 403) {
        alert("Access denied. Admin privileges required.");
        navigate("/");
      }
    } finally {
      setLoading(false);
    }
  };

  const updateUserRole = async (email, newRole) => {
    try {
      const token = localStorage.getItem("token");
      const endpoint = newRole === "ROLE_ADMIN" ? "make-admin" : "make-user";

      await axios.post(
          `http://localhost:8080/adamtech/admin/${endpoint}/${email}`,
          {},
          {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
            }
          }
      );

      // Refresh data
      fetchAdminData(token);
      alert(`User ${email} role updated to ${newRole}`);
    } catch (err) {
      console.error("Failed to update user role:", err);
      alert("Failed to update user role");
    }
  };

  if (loading) {
    return (
        <div style={{ padding: "40px", textAlign: "center" }}>
          <h2>Loading Admin Dashboard...</h2>
        </div>
    );
  }

  return (
      <div style={{ padding: "40px 20px", maxWidth: "1200px", margin: "0 auto", fontFamily: "'Segoe UI', sans-serif" }}>
        {/* Header */}
        <div style={{ marginBottom: "40px" }}>
          <h1 style={{ color: "#333", marginBottom: "10px" }}>Admin Dashboard</h1>
          <p style={{ color: "#666" }}>Manage users and system settings</p>
        </div>

        {/* Stats Cards */}
        <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))", gap: "20px", marginBottom: "40px" }}>
          <div style={{ backgroundColor: "#fff3e0", padding: "20px", borderRadius: "8px", textAlign: "center" }}>
            <h3 style={{ color: "orange", margin: "0 0 10px 0" }}>Total Users</h3>
            <p style={{ fontSize: "2rem", fontWeight: "bold", color: "orange", margin: 0 }}>{stats.totalUsers}</p>
          </div>
          <div style={{ backgroundColor: "#eeb1b1", padding: "20px", borderRadius: "8px", textAlign: "center" }}>
            <h3 style={{ color: "#d32f2f", margin: "0 0 10px 0" }}>Admin Users</h3>
            <p style={{ fontSize: "2rem", fontWeight: "bold", color: "#d32f2f", margin: 0 }}>{stats.adminUsers}</p>
          </div>
          <div style={{ backgroundColor: "#e8f5e8", padding: "20px", borderRadius: "8px", textAlign: "center" }}>
            <h3 style={{ color: "#2e7d32", margin: "0 0 10px 0" }}>Regular Users</h3>
            <p style={{ fontSize: "2rem", fontWeight: "bold", color: "#2e7d32", margin: 0 }}>{stats.regularUsers}</p>
          </div>
        </div>

        {/* Users Table */}
        <div style={{ backgroundColor: "white", borderRadius: "8px", boxShadow: "0 2px 10px rgba(0,0,0,0.1)", overflow: "hidden" }}>
          <div style={{ padding: "20px", borderBottom: "1px solid #eee" }}>
            <h2 style={{ margin: 0, color: "#333" }}>User Management</h2>
          </div>

          <div style={{ overflowX: "auto" }}>
            <table style={{ width: "100%", borderCollapse: "collapse" }}>
              <thead>
              <tr style={{ backgroundColor: "#f5f5f5" }}>
                <th style={{ padding: "15px", textAlign: "left", borderBottom: "1px solid #ddd" }}>Email</th>
                <th style={{ padding: "15px", textAlign: "left", borderBottom: "1px solid #ddd" }}>Name</th>
                <th style={{ padding: "15px", textAlign: "left", borderBottom: "1px solid #ddd" }}>Role</th>
                <th style={{ padding: "15px", textAlign: "left", borderBottom: "1px solid #ddd" }}>Actions</th>
              </tr>
              </thead>
              <tbody>
              {users.map((user) => (
                  <tr key={user.email} style={{ borderBottom: "1px solid #eee" }}>
                    <td style={{ padding: "15px" }}>{user.email}</td>
                    <td style={{ padding: "15px" }}>{user.firstName} {user.lastName}</td>
                    <td style={{ padding: "15px" }}>
                    <span style={{
                      padding: "4px 12px",
                      borderRadius: "20px",
                      backgroundColor: user.role === "ROLE_ADMIN" ? "#d32f2f" : "#388e3c",
                      color: "white",
                      fontSize: "0.8rem",
                      fontWeight: "bold"
                    }}>
                      {user.role === "ROLE_ADMIN" ? "ADMIN" : "USER"}
                    </span>
                    </td>
                    <td style={{ padding: "15px" }}>
                      {user.role === "ROLE_USER" ? (
                          <button
                              onClick={() => updateUserRole(user.email, "ROLE_ADMIN")}
                              style={{
                                padding: "8px 16px",
                                backgroundColor: "#1976d2",
                                color: "white",
                                border: "none",
                                borderRadius: "4px",
                                cursor: "pointer",
                                marginRight: "8px",
                                fontSize: "0.9rem"
                              }}
                          >
                            Make Admin
                          </button>
                      ) : (
                          <button
                              onClick={() => updateUserRole(user.email, "ROLE_USER")}
                              style={{
                                padding: "8px 16px",
                                backgroundColor: "#757575",
                                color: "white",
                                border: "none",
                                borderRadius: "4px",
                                cursor: "pointer",
                                fontSize: "0.9rem"
                              }}
                          >
                            Make User
                          </button>
                      )}
                    </td>
                  </tr>
              ))}
              </tbody>
            </table>
          </div>
        </div>

        {/* Quick Actions */}
        <div style={{ marginTop: "30px", padding: "20px", backgroundColor: "#f8f9fa", borderRadius: "8px" }}>
          <h3 style={{ marginBottom: "15px" }}>Quick Actions</h3>
          <button
              onClick={() => window.location.reload()}
              style={{
                padding: "10px 20px",
                backgroundColor: "#6c757d",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
                marginRight: "10px"
              }}
          >
            Refresh Data
          </button>
        </div>
      </div>
  );
};

export default AdminDashboard;