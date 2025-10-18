import React, { useState } from 'react';
import '../../styles/Account.css';

const WishlistForm = ({ onAdd }) => {
  const [productId, setProductId] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!productId) return;
    onAdd(productId);
    setProductId('');
  };

  return (
    <div className="account-card full-width">
      <input
        type="number"
        placeholder="Product ID to add"
        value={productId}
        onChange={(e) => setProductId(e.target.value)}
        style={{ padding: '10px', width: '70%', marginRight: '10px' }}
      />
      <button className="orange-btn" onClick={handleSubmit}>
        Add
      </button>
    </div>
  );
};

export default WishlistForm;