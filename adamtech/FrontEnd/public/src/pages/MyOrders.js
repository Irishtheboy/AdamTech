// src/pages/MyOrders.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const MyOrders = ({ user }) => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [filter, setFilter] = useState('ALL');

  const fetchOrders = async () => {
    try {
      setLoading(true);
      setError('');

      const token = localStorage.getItem('token');
      if (!token) {
        setError('Please log in to view your orders.');
        setLoading(false);
        return;
      }

      // Try different possible endpoints for customer orders
      let response;

      // Option 1: Get orders by customer email
      try {
        response = await axios.get(`http://localhost:8080/adamtech/order/customer/${user.email}`, {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
      } catch (err) {
        console.log('Endpoint /order/customer/{email} failed, trying alternatives...');

        // Option 2: Get orders by customer ID
        try {
          response = await axios.get(`http://localhost:8080/adamtech/order/customer/id/${user.id}`, {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
            }
          });
        } catch (err2) {
          console.log('Endpoint /order/customer/id/{id} failed, trying /order/my-orders...');

          // Option 3: Generic "my orders" endpoint
          try {
            response = await axios.get('http://localhost:8080/adamtech/order/my-orders', {
              headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
              }
            });
          } catch (err3) {
            console.log('All customer order endpoints failed');
            throw new Error('No customer order endpoint available');
          }
        }
      }

      console.log('Orders data received:', response.data);
      setOrders(Array.isArray(response.data) ? response.data : []);

    } catch (err) {
      console.error('Error fetching orders:', err);

      if (err.response?.status === 403) {
        setError('Access denied. You do not have permission to view orders.');
      } else if (err.response?.status === 404) {
        setError('Order service not available. Please try again later.');
      } else {
        setError('Failed to load your orders. Please try again later.');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleCancelOrder = async (orderId) => {
    if (!window.confirm('Are you sure you want to cancel this order? This action cannot be undone.')) {
      return;
    }

    try {
      const token = localStorage.getItem('token');
      await axios.put(
          `http://localhost:8080/adamtech/order/${orderId}/status?status=CANCELLED`,
          {},
          {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
            }
          }
      );

      // Update local state to reflect cancellation
      setOrders(prev => prev.map(order =>
          order.orderId === orderId
              ? { ...order, orderStatus: 'CANCELLED' }
              : order
      ));

      alert('Order cancelled successfully!');
    } catch (err) {
      console.error('Error cancelling order:', err);
      alert('Failed to cancel order. Please try again.');
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

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    try {
      const date = new Date(dateString);
      return isNaN(date.getTime()) ? 'N/A' : date.toLocaleDateString();
    } catch (error) {
      return 'N/A';
    }
  };

  const getOrderTotal = (order) => {
    return order.totalAmount ||
        (order.orderItems?.reduce((sum, item) => sum + (item.price * item.quantity), 0) || 0);
  };

  const getOrderStatus = (order) => {
    return order.orderStatus || order.status || 'PENDING';
  };

  const getOrderId = (order) => {
    return order.orderId || order.id || 'N/A';
  };

  // Filter orders based on status
  const filteredOrders = filter === 'ALL'
      ? orders
      : orders.filter(order => getOrderStatus(order) === filter);

  const canCancelOrder = (order) => {
    const status = getOrderStatus(order);
    return status === 'PENDING' || status === 'CONFIRMED';
  };

  useEffect(() => {
    if (user) {
      fetchOrders();
    }
  }, [user]);

  if (loading) {
    return (
        <div style={{
          padding: '60px 20px',
          textAlign: 'center',
          backgroundColor: 'white',
          borderRadius: '12px',
          boxShadow: '0 2px 10px rgba(0,0,0,0.1)'
        }}>
          <div style={{ fontSize: '3rem', marginBottom: '20px' }}>⏳</div>
          <h2 style={{ marginBottom: '10px', color: '#333' }}>Loading Your Orders</h2>
          <p style={{ color: '#666' }}>Please wait while we fetch your order history...</p>
        </div>
    );
  }

  return (
      <div style={{
        maxWidth: '1200px',
        margin: '0 auto',
        padding: '20px',
        fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif"
      }}>
        {/* Header */}
        <div style={{
          backgroundColor: 'white',
          borderRadius: '12px',
          padding: '30px',
          boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
          marginBottom: '20px'
        }}>
          <h1 style={{
            marginBottom: '10px',
            color: '#333',
            fontSize: '2rem',
            fontWeight: '600'
          }}>
            My Orders
          </h1>
          <p style={{ color: '#666', marginBottom: '20px' }}>
            View and manage your order history
          </p>

          {/* Filter and Stats */}
          <div style={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            flexWrap: 'wrap',
            gap: '15px'
          }}>
            <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
              <select
                  value={filter}
                  onChange={(e) => setFilter(e.target.value)}
                  style={{
                    padding: '10px 15px',
                    border: '1px solid #ddd',
                    borderRadius: '6px',
                    fontSize: '14px',
                    minWidth: '150px'
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
              </select>

              <button
                  onClick={fetchOrders}
                  style={{
                    padding: '10px 20px',
                    backgroundColor: '#6c757d',
                    color: 'white',
                    border: 'none',
                    borderRadius: '6px',
                    cursor: 'pointer',
                    fontSize: '14px',
                    transition: 'background-color 0.2s'
                  }}
                  onMouseEnter={(e) => e.target.style.backgroundColor = '#5a6268'}
                  onMouseLeave={(e) => e.target.style.backgroundColor = '#6c757d'}
              >
                Refresh
              </button>
            </div>

            <div style={{
              display: 'flex',
              gap: '20px',
              fontSize: '14px',
              color: '#666'
            }}>
              <span>Total Orders: <strong>{orders.length}</strong></span>
              <span>Filtered: <strong>{filteredOrders.length}</strong></span>
            </div>
          </div>
        </div>

        {/* Error Message */}
        {error && (
            <div style={{
              padding: '15px',
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

        {/* Orders Table */}
        <div style={{
          backgroundColor: 'white',
          borderRadius: '12px',
          boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
          overflow: 'hidden'
        }}>
          {filteredOrders.length === 0 ? (
              <div style={{
                padding: '60px 20px',
                textAlign: 'center',
                color: '#666'
              }}>
                <div style={{ fontSize: '3rem', marginBottom: '20px' }}>📦</div>
                <h3 style={{ marginBottom: '10px' }}>
                  {filter === 'ALL' ? 'No Orders Yet' : 'No Orders Found'}
                </h3>
                <p>
                  {filter === 'ALL'
                      ? "You haven't placed any orders yet. Start shopping to see your orders here!"
                      : `No orders found with status: ${filter}`
                  }
                </p>
                {filter !== 'ALL' && (
                    <button
                        onClick={() => setFilter('ALL')}
                        style={{
                          marginTop: '15px',
                          padding: '10px 20px',
                          backgroundColor: '#ff6600',
                          color: 'white',
                          border: 'none',
                          borderRadius: '6px',
                          cursor: 'pointer'
                        }}
                    >
                      Show All Orders
                    </button>
                )}
              </div>
          ) : (
              <div style={{ overflowX: 'auto' }}>
                <table style={{
                  width: '100%',
                  borderCollapse: 'collapse',
                  minWidth: '800px'
                }}>
                  <thead>
                  <tr style={{ backgroundColor: '#f8f9fa' }}>
                    <th style={{
                      padding: '15px',
                      textAlign: 'left',
                      borderBottom: '2px solid #dee2e6',
                      fontWeight: '600',
                      color: '#333'
                    }}>
                      Order ID
                    </th>
                    <th style={{
                      padding: '15px',
                      textAlign: 'left',
                      borderBottom: '2px solid #dee2e6',
                      fontWeight: '600',
                      color: '#333'
                    }}>
                      Date
                    </th>
                    <th style={{
                      padding: '15px',
                      textAlign: 'left',
                      borderBottom: '2px solid #dee2e6',
                      fontWeight: '600',
                      color: '#333'
                    }}>
                      Items
                    </th>
                    <th style={{
                      padding: '15px',
                      textAlign: 'right',
                      borderBottom: '2px solid #dee2e6',
                      fontWeight: '600',
                      color: '#333'
                    }}>
                      Total
                    </th>
                    <th style={{
                      padding: '15px',
                      textAlign: 'center',
                      borderBottom: '2px solid #dee2e6',
                      fontWeight: '600',
                      color: '#333'
                    }}>
                      Status
                    </th>
                    <th style={{
                      padding: '15px',
                      textAlign: 'center',
                      borderBottom: '2px solid #dee2e6',
                      fontWeight: '600',
                      color: '#333'
                    }}>
                      Actions
                    </th>
                  </tr>
                  </thead>
                  <tbody>
                  {filteredOrders.map((order) => (
                      <tr
                          key={getOrderId(order)}
                          style={{
                            borderBottom: '1px solid #dee2e6',
                            transition: 'background-color 0.2s'
                          }}
                          onMouseEnter={(e) => e.target.parentElement.style.backgroundColor = '#f8f9fa'}
                          onMouseLeave={(e) => e.target.parentElement.style.backgroundColor = 'transparent'}
                      >
                        <td style={{
                          padding: '15px',
                          fontWeight: '600',
                          color: '#333'
                        }}>
                          #{getOrderId(order)}
                        </td>
                        <td style={{
                          padding: '15px',
                          color: '#666'
                        }}>
                          {formatDate(order.orderDate)}
                        </td>
                        <td style={{
                          padding: '15px',
                          color: '#666'
                        }}>
                          {order.orderItems?.length || 1} item(s)
                        </td>
                        <td style={{
                          padding: '15px',
                          textAlign: 'right',
                          fontWeight: '600',
                          color: '#2a9d8f'
                        }}>
                          R{getOrderTotal(order).toFixed(2)}
                        </td>
                        <td style={{
                          padding: '15px',
                          textAlign: 'center'
                        }}>
                      <span style={{
                        padding: '6px 12px',
                        borderRadius: '20px',
                        backgroundColor: getStatusColor(getOrderStatus(order)),
                        color: 'white',
                        fontSize: '0.8rem',
                        fontWeight: 'bold',
                        display: 'inline-block',
                        minWidth: '100px'
                      }}>
                        {getOrderStatus(order)}
                      </span>
                        </td>
                        <td style={{
                          padding: '15px',
                          textAlign: 'center'
                        }}>
                          {canCancelOrder(order) ? (
                              <button
                                  onClick={() => handleCancelOrder(getOrderId(order))}
                                  style={{
                                    backgroundColor: '#dc3545',
                                    color: 'white',
                                    border: 'none',
                                    padding: '8px 16px',
                                    borderRadius: '6px',
                                    cursor: 'pointer',
                                    fontSize: '0.85rem',
                                    transition: 'background-color 0.2s'
                                  }}
                                  onMouseEnter={(e) => e.target.style.backgroundColor = '#c82333'}
                                  onMouseLeave={(e) => e.target.style.backgroundColor = '#dc3545'}
                              >
                                Cancel Order
                              </button>
                          ) : (
                              <span style={{ color: '#999', fontSize: '0.85rem' }}>
                          {getOrderStatus(order) === 'CANCELLED' ? 'Cancelled' : 'Cannot cancel'}
                        </span>
                          )}
                        </td>
                      </tr>
                  ))}
                  </tbody>
                </table>
              </div>
          )}
        </div>
      </div>
  );
};

export default MyOrders;