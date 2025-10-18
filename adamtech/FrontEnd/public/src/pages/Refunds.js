import React, { useEffect, useState } from 'react';

const Refunds = () => {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        // For now, no backend connection
        setOrders([]); // keep empty
    }, []);

    const handleRefund = (id) => {
        // Placeholder until backend is connected
        alert(`Refund/Return requested for order ${id}`);
    };

    return (
        <div style={{ maxWidth: '800px', margin: '0 auto', padding: '20px' }}>
            <h1 style={{ textAlign: 'center', marginBottom: '25px' }}>Refunds & Returns</h1>

            <div
                style={{
                    display: 'grid',
                    gap: '15px',
                }}
            >
                {orders.length === 0 ? (
                    <p style={{ textAlign: 'center', fontSize: '14px', color: '#555' }}>
                        No refunds or returns currently in progress.
                    </p>
                ) : (
                    orders.map((order) => (
                        <div
                            key={order.id}
                            style={{
                                background: '#fff',
                                padding: '15px 20px',
                                borderRadius: '12px',
                                boxShadow: '0 1px 3px rgba(0,0,0,0.08)',
                                display: 'flex',
                                justifyContent: 'space-between',
                                alignItems: 'center',
                            }}
                        >
                            <div>
                                <p style={{ margin: 0, fontSize: '14px', fontWeight: 'bold' }}>{order.item}</p>
                                <p style={{ margin: '5px 0 0 0', fontSize: '12px', color: '#555' }}>
                                    Delivered: {order.date} | Status: {order.status}
                                </p>
                            </div>
                            <button
                                onClick={() => handleRefund(order.id)}
                                style={{
                                    backgroundColor: '#ff6600',
                                    color: '#fff',
                                    padding: '8px 16px',
                                    borderRadius: '12px',
                                    border: 'none',
                                    cursor: 'pointer',
                                    fontSize: '14px',
                                    transition: 'background 0.2s',
                                }}
                                onMouseOver={(e) => (e.target.style.backgroundColor = '#e65c00')}
                                onMouseOut={(e) => (e.target.style.backgroundColor = '#ff6600')}
                            >
                                Request Refund/Return
                            </button>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default Refunds;

