import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Refunds = ({ user }) => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

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

            const response = await axios.get(`http://localhost:8080/adamtech/order/customer/${user.email}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });

            // Filter to only DELIVERED or RETURNED orders
            const filtered = (Array.isArray(response.data) ? response.data : []).filter(order => {
                const status = order.orderStatus || order.status || 'PENDING';
                return status === 'DELIVERED' || status === 'RETURNED';
            });

            setOrders(filtered);
        } catch (err) {
            console.error('Error fetching orders:', err);
            setError('Failed to load orders. Please try again later.');
        } finally {
            setLoading(false);
        }
    };

    const formatDate = (dateString) => {
        if (!dateString) return 'N/A';
        const date = new Date(dateString);
        return isNaN(date.getTime()) ? 'N/A' : date.toLocaleDateString();
    };

    const getStatusColor = (status) => {
        switch (status) {
            case 'DELIVERED': return '#607d8b';
            case 'RETURNED': return '#795548';
            default: return '#757575';
        }
    };

    const getOrderStatus = (order) => order.orderStatus || order.status || 'PENDING';
    const getOrderId = (order) => order.orderId || order.id || 'N/A';
    const getOrderTotal = (order) =>
        order.totalAmount || (order.orderItems?.reduce((sum, item) => sum + (item.price * item.quantity), 0) || 0);

    const handleRefundRequest = (order) => {
        const choice = window.confirm(`Do you want to request a refund/return for order #${getOrderId(order)}?`);
        if (choice) {
            // Update the status locally
            setOrders(prev => prev.map(o =>
                getOrderId(o) === getOrderId(order) ? { ...o, orderStatus: 'RETURNED' } : o
            ));
            alert(`Refund/Return requested for order #${getOrderId(order)}`);
            // TODO: call backend API to update order status
        }
    };

    useEffect(() => {
        if (user) fetchOrders();
    }, [user]);

    if (loading) {
        return (
            <div style={{ padding: '60px 20px', textAlign: 'center' }}>
                <div style={{ fontSize: '3rem', marginBottom: '20px' }}>⏳</div>
                <h2>Loading Your Refundable Orders</h2>
            </div>
        );
    }

    return (
        <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '20px', fontFamily: "'Segoe UI', sans-serif" }}>
            <h1 style={{ textAlign: 'center', marginBottom: '25px' }}>Refunds & Returns</h1>

            {error && (
                <div style={{
                    padding: '15px',
                    backgroundColor: '#f8d7da',
                    color: '#721c24',
                    borderRadius: '8px',
                    marginBottom: '20px',
                    textAlign: 'center'
                }}>
                    ❌ {error}
                </div>
            )}

            <div style={{ backgroundColor: 'white', borderRadius: '12px', boxShadow: '0 2px 10px rgba(0,0,0,0.1)', overflow: 'hidden' }}>
                {orders.length === 0 ? (
                    <div style={{ padding: '60px 20px', textAlign: 'center', color: '#666' }}>
                        <div style={{ fontSize: '3rem', marginBottom: '20px' }}>📦</div>
                        <h3>No refundable orders found</h3>
                        <p>Only delivered orders are eligible for refunds/returns.</p>
                    </div>
                ) : (
                    <div style={{ overflowX: 'auto' }}>
                        <table style={{ width: '100%', borderCollapse: 'collapse', minWidth: '800px' }}>
                            <thead>
                                <tr style={{ backgroundColor: '#f8f9fa' }}>
                                    <th style={{ padding: '15px', textAlign: 'left', borderBottom: '2px solid #dee2e6' }}>Order #</th>
                                    <th style={{ padding: '15px', textAlign: 'left', borderBottom: '2px solid #dee2e6' }}>Date</th>
                                    <th style={{ padding: '15px', textAlign: 'left', borderBottom: '2px solid #dee2e6' }}>Items</th>
                                    <th style={{ padding: '15px', textAlign: 'right', borderBottom: '2px solid #dee2e6' }}>Total</th>
                                    <th style={{ padding: '15px', textAlign: 'center', borderBottom: '2px solid #dee2e6' }}>Status</th>
                                    <th style={{ padding: '15px', textAlign: 'center', borderBottom: '2px solid #dee2e6' }}>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {orders.map((order) => (
                                    <tr key={getOrderId(order)} style={{ borderBottom: '1px solid #dee2e6' }}>
                                        <td style={{ padding: '15px', fontWeight: '600', color: '#333' }}>#{getOrderId(order)}</td>
                                        <td style={{ padding: '15px', color: '#666' }}>{formatDate(order.orderDate)}</td>
                                        <td style={{ padding: '15px', color: '#666' }}>{order.orderItems?.length || 1} item(s)</td>
                                        <td style={{ padding: '15px', textAlign: 'right', fontWeight: '600', color: 'orange' }}>R{getOrderTotal(order).toFixed(2)}</td>
                                        <td style={{ padding: '15px', textAlign: 'center' }}>
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
                                        <td style={{ padding: '15px', textAlign: 'center' }}>
                                            {getOrderStatus(order) === 'RETURNED' ? (
                                                <span style={{ color: '#999', fontSize: '0.85rem' }}>Refunded/Returned</span>
                                            ) : (
                                                <button
                                                    onClick={() => handleRefundRequest(order)}
                                                    style={{
                                                        backgroundColor: '#ff6600',
                                                        color: 'white',
                                                        border: 'none',
                                                        padding: '6px 12px',
                                                        borderRadius: '6px',
                                                        cursor: 'pointer',
                                                        fontSize: '0.85rem',
                                                        transition: 'background-color 0.2s'
                                                    }}
                                                    onMouseEnter={(e) => e.target.style.backgroundColor = '#e65c00'}
                                                    onMouseLeave={(e) => e.target.style.backgroundColor = '#ff6600'}
                                                >
                                                    Request Refund/Return
                                                </button>
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

export default Refunds;
