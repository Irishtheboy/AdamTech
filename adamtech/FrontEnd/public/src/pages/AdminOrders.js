import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const AdminOrders = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState("ALL");
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

    fetchOrders(token);
  }, [navigate, filter]);

  const fetchOrders = async (token) => {
    try {
      setLoading(true);
      const response = await axios.get(
          "http://localhost:8080/adamtech/order/getAll",
          {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
            }
          }
      );
      console.log("Orders data:", response.data);
      setOrders(response.data);
    } catch (err) {
      console.error("Failed to fetch orders:", err);
      if (err.response?.status === 403) {
        alert("Access denied. Admin privileges required.");
        navigate("/");
      } else {
        alert("Failed to load orders. Please try again.");
      }
    } finally {
      setLoading(false);
    }
  };

  const updateOrderStatus = async (orderId, newStatus) => {
    try {
      const token = localStorage.getItem("token");
      const user = JSON.parse(localStorage.getItem("user") || "{}");

      console.log("🔄 Updating order status...", { orderId, newStatus });

      // ✅ FIX: Send status as URL parameter instead of request body
      const response = await axios.put(
          `http://localhost:8080/adamtech/order/${orderId}/status?status=${encodeURIComponent(newStatus)}`,
          {}, // Empty body since status is in URL
          {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
            }
          }
      );

      // Refresh orders
      fetchOrders(token);
      alert(`✅ Order #${orderId} status updated to ${newStatus}`);

    } catch (err) {
      console.error("❌ Failed to update order status:", err);
      console.error("🔍 Error details:", err.response?.data);

      if (err.response?.status === 403) {
        alert(`🔒 Access Denied: You don't have permission to update order status.`);
      } else if (err.response?.status === 400) {
        alert(`❌ Bad Request: ${err.response?.data?.message || 'Invalid status value'}`);
      } else {
        alert("❌ Failed to update order status. Please try again.");
      }
    }
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'PENDING': return '#ff9800';
      case 'CONFIRMED': return '#2196f3';
      case 'PAID': return '#009688';
      case 'SHIPPED': return '#9c27b0';
      case 'DELIVERED': return '#607d8b';
      case 'COMPLETED': return '#4caf50';
      case 'CANCELLED': return '#f44336';
      case 'RETURNED': return '#795548';
      default: return '#757575';
    }
  };

  // Format date properly
  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';

    try {
      if (typeof dateString === 'string' && dateString.includes('/')) {
        return dateString;
      }

      const date = new Date(dateString);
      return isNaN(date.getTime()) ? 'N/A' : date.toLocaleDateString();
    } catch (error) {
      return 'N/A';
    }
  };

  // Get display name for customer
  const getCustomerDisplayName = (order) => {
    if (order.customerName) return order.customerName;
    if (order.customerFirstName && order.customerLastName) {
      return `${order.customerFirstName} ${order.customerLastName}`;
    }
    if (order.customerFirstName) return order.customerFirstName;
    if (order.customerEmail) return order.customerEmail;

    return 'Unknown Customer';
  };

  // Get customer email
  const getCustomerEmail = (order) => {
    return order.customerEmail || 'No email available';
  };

  // Get order ID
  const getOrderId = (order) => {
    return order.orderId || order.id || 'N/A';
  };

  // Get order status
  const getOrderStatus = (order) => {
    return order.orderStatus || order.status || 'PENDING';
  };

  // Get total amount
  const getTotalAmount = (order) => {
    return order.totalAmount || 0;
  };

  // Filter orders based on selected filter
  const filteredOrders = filter === "ALL"
      ? orders
      : orders.filter(order => getOrderStatus(order) === filter);

  if (loading) {
    return (
        <div style={{ padding: "40px", textAlign: "center" }}>
          <h2>Loading Orders...</h2>
        </div>
    );
  }

  return (
      <div style={{ padding: "40px 20px", maxWidth: "1400px", margin: "0 auto", fontFamily: "'Segoe UI', sans-serif" }}>
        {/* Header */}
        <div style={{ marginBottom: "40px", display: "flex", justifyContent: "space-between", alignItems: "center" }}>
          <div>
            <h1 style={{ color: "#333", marginBottom: "10px" }}>Order Management</h1>
            <p style={{ color: "#666" }}>Manage and track all customer orders</p>
          </div>

          {/* Filter and Actions */}
          <div style={{ display: "flex", gap: "15px", alignItems: "center" }}>
            <select
                value={filter}
                onChange={(e) => setFilter(e.target.value)}
                style={{
                  padding: "10px 15px",
                  border: "1px solid #ddd",
                  borderRadius: "6px",
                  fontSize: "1rem"
                }}
            >
              <option value="ALL">All Orders</option>
              <option value="PENDING">Pending</option>
              <option value="CONFIRMED">Confirmed</option>
              <option value="PAID">Paid</option>
              <option value="SHIPPED">Shipped</option>
              <option value="DELIVERED">Delivered</option>
              <option value="COMPLETED">Completed</option>
              <option value="CANCELLED">Cancelled</option>
              <option value="RETURNED">Returned</option>
            </select>

            <button
                onClick={() => fetchOrders(localStorage.getItem("token"))}
                style={{
                  padding: "10px 20px",
                  backgroundColor: "#6c757d",
                  color: "white",
                  border: "none",
                  borderRadius: "6px",
                  cursor: "pointer"
                }}
            >
              Refresh
            </button>
          </div>
        </div>

        {/* Orders Statistics */}
        <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))", gap: "20px", marginBottom: "30px" }}>
          <div style={{ backgroundColor: "#e3f2fd", padding: "20px", borderRadius: "8px", textAlign: "center" }}>
            <h3 style={{ color: "#1976d2", margin: "0 0 10px 0" }}>Total Orders</h3>
            <p style={{ fontSize: "2rem", fontWeight: "bold", color: "#1976d2", margin: 0 }}>{orders.length}</p>
          </div>
          <div style={{ backgroundColor: "#fff3e0", padding: "20px", borderRadius: "8px", textAlign: "center" }}>
            <h3 style={{ color: "#ef6c00", margin: "0 0 10px 0" }}>Pending</h3>
            <p style={{ fontSize: "2rem", fontWeight: "bold", color: "#ef6c00", margin: 0 }}>
              {orders.filter(o => getOrderStatus(o) === 'PENDING').length}
            </p>
          </div>
          <div style={{ backgroundColor: "#e8f5e8", padding: "20px", borderRadius: "8px", textAlign: "center" }}>
            <h3 style={{ color: "#2e7d32", margin: "0 0 10px 0" }}>Completed</h3>
            <p style={{ fontSize: "2rem", fontWeight: "bold", color: "#2e7d32", margin: 0 }}>
              {orders.filter(o => getOrderStatus(o) === 'COMPLETED').length}
            </p>
          </div>
        </div>

        {/* Orders Table */}
        <div style={{ backgroundColor: "white", borderRadius: "12px", boxShadow: "0 4px 20px rgba(0,0,0,0.1)", overflow: "hidden" }}>
          <div style={{ padding: "25px", borderBottom: "1px solid #eee", backgroundColor: "#f8f9fa" }}>
            <h2 style={{ margin: 0, color: "#333", display: "flex", alignItems: "center", gap: "10px" }}>
              📦 Orders ({filteredOrders.length})
              {filter !== "ALL" && <span style={{ fontSize: "1rem", color: "#666" }}>• Filtered by: {filter}</span>}
            </h2>
          </div>

          <div style={{ overflowX: "auto" }}>
            <table style={{ width: "100%", borderCollapse: "collapse" }}>
              <thead>
              <tr style={{ backgroundColor: "#f5f5f5" }}>
                <th style={{ padding: "15px", textAlign: "left", borderBottom: "2px solid #ddd", fontWeight: "600" }}>Order ID</th>
                <th style={{ padding: "15px", textAlign: "left", borderBottom: "2px solid #ddd", fontWeight: "600" }}>Customer</th>
                <th style={{ padding: "15px", textAlign: "left", borderBottom: "2px solid #ddd", fontWeight: "600" }}>Date</th>
                <th style={{ padding: "15px", textAlign: "left", borderBottom: "2px solid #ddd", fontWeight: "600" }}>Total</th>
                <th style={{ padding: "15px", textAlign: "left", borderBottom: "2px solid #ddd", fontWeight: "600" }}>Status</th>
                <th style={{ padding: "15px", textAlign: "left", borderBottom: "2px solid #ddd", fontWeight: "600" }}>Actions</th>
              </tr>
              </thead>
              <tbody>
              {filteredOrders.length > 0 ? (
                  filteredOrders.map((order) => (
                      <tr key={getOrderId(order)} style={{ borderBottom: "1px solid #eee", transition: "background 0.2s" }}>
                        <td style={{ padding: "15px", fontWeight: "bold", color: "#333" }}>
                          #{getOrderId(order)}
                        </td>
                        <td style={{ padding: "15px" }}>
                          <div>
                            <div style={{ fontWeight: "500" }}>
                              {getCustomerDisplayName(order)}
                            </div>
                            <div style={{ color: "#666", fontSize: "0.9rem" }}>
                              {getCustomerEmail(order)}
                            </div>
                          </div>
                        </td>
                        <td style={{ padding: "15px", color: "#666" }}>
                          {formatDate(order.orderDate)}
                        </td>
                        <td style={{ padding: "15px", fontWeight: "bold", color: "#2a9d8f" }}>
                          R{getTotalAmount(order).toFixed(2)}
                        </td>
                        <td style={{ padding: "15px" }}>
                      <span style={{
                        padding: "6px 12px",
                        borderRadius: "20px",
                        backgroundColor: getStatusColor(getOrderStatus(order)),
                        color: "white",
                        fontSize: "0.8rem",
                        fontWeight: "bold",
                        display: "inline-block"
                      }}>
                        {getOrderStatus(order)}
                      </span>
                        </td>
                        <td style={{ padding: "15px" }}>
                          <select
                              value={getOrderStatus(order)}
                              onChange={(e) => updateOrderStatus(getOrderId(order), e.target.value)}
                              style={{
                                padding: "8px 12px",
                                border: "1px solid #ddd",
                                borderRadius: "4px",
                                fontSize: "0.9rem",
                                minWidth: "140px"
                              }}
                          >
                            <option value="PENDING">PENDING</option>
                            <option value="CONFIRMED">CONFIRMED</option>
                            <option value="PAID">PAID</option>
                            <option value="SHIPPED">SHIPPED</option>
                            <option value="DELIVERED">DELIVERED</option>
                            <option value="COMPLETED">COMPLETED</option>
                            <option value="CANCELLED">CANCELLED</option>
                            <option value="RETURNED">RETURNED</option>
                          </select>
                        </td>
                      </tr>
                  ))
              ) : (
                  <tr>
                    <td colSpan="6" style={{ padding: "60px", textAlign: "center", color: "#666" }}>
                      <div style={{ fontSize: "1.2rem", marginBottom: "10px" }}>📭</div>
                      <div>No orders found</div>
                      {filter !== "ALL" && (
                          <div style={{ marginTop: "10px", fontSize: "0.9rem" }}>
                            Try changing the filter or check back later.
                          </div>
                      )}
                    </td>
                  </tr>
              )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
  );
};

export default AdminOrders;