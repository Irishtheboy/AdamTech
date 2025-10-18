// src/components/orders/OrderItem.js
import React from 'react';

const OrderItem = ({ order, onDelete }) => {
  return (
    <div className="account-card">
      <h3 className="section-title">Order #{order.id}</h3>
      <p>Date: {order.orderDate}</p>
      <p>Status: {order.status}</p>
      <p>Total: R{order.totalAmount.toLocaleString()}</p>

      <h4>Items:</h4>
      <ul>
        {order.items?.map((item, index) => (
          <li key={index}>
            {item.productName} (x{item.quantity}) - R{item.unitPrice.toLocaleString()}
          </li>
        ))}
      </ul>

      <button className="orange-btn" onClick={() => onDelete(order.id)}>
        Remove
      </button>
    </div>
  );
};

export default OrderItem;
