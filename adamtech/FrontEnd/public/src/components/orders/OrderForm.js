// src/components/orders/OrderForm.js
import React, { useState } from 'react';
import { createOrder } from '../../services/orderService';

const OrderForm = ({ onAdd }) => {
  const [productName, setProductName] = useState('');
  const [quantity, setQuantity] = useState(1);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!productName) return;

    const newOrder = {
      orderDate: new Date().toISOString().split('T')[0],
      status: "PENDING",
      totalAmount: quantity * 100, 
      items: [
        { productName, quantity, unitPrice: 100 } 
      ]
    };

    try {
      const savedOrder = await createOrder(newOrder);
      onAdd(savedOrder); 
      setProductName('');
      setQuantity(1);
    } catch (err) {
      console.error('Error creating order:', err);
    }
  };

  return (
    <div className="account-card full-width">
      <input
        type="text"
        placeholder="Product Name"
        value={productName}
        onChange={(e) => setProductName(e.target.value)}
        style={{ padding: '10px', width: '40%', marginRight: '10px' }}
      />
      <input
        type="number"
        min="1"
        value={quantity}
        onChange={(e) => setQuantity(Number(e.target.value))}
        style={{ padding: '10px', width: '20%', marginRight: '10px' }}
      />
      <button className="orange-btn" onClick={handleSubmit}>
        Add Order
      </button>
    </div>
  );
};

export default OrderForm;
