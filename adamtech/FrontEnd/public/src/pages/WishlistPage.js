// src/pages/WishlistPage.js
import React, { useEffect, useState } from 'react';
import WishlistForm from '../components/wishlist/WishlistForm';
import { getAllWishlists, deleteWishlist } from '../services/wishlistService';

const WishlistPage = () => {
  const [wishlists, setWishlists] = useState([]);

  const fetchWishlists = async () => {
    try {
      const data = await getAllWishlists();
      setWishlists(data);
    } catch (err) {
      console.error('Error fetching wishlists:', err);
    }
  };

  const handleDelete = async (wishlistId) => {
    try {
      await deleteWishlist(wishlistId);
      setWishlists((prev) => prev.filter((item) => item.wishlistId !== wishlistId));
    } catch (err) {
      console.error('Error deleting wishlist:', err);
    }
  };

  useEffect(() => {
    fetchWishlists();
  }, []);

  return (
      <div style={{ maxWidth: '1000px', margin: '0 auto', padding: '20px' }}>
        <h1 style={{ textAlign: 'center', marginBottom: '25px' }}>My Wishlist</h1>

        <WishlistForm onAdd={fetchWishlists} />

        <div style={{ overflowX: 'auto' }}>
          <table style={{ width: '100%', borderCollapse: 'collapse', minWidth: '600px' }}>
            <thead style={{ backgroundColor: 'orange', color: '#fff' }}>
            <tr>
              <th style={{ textAlign: 'left', padding: '12px', border: '1px solid #ddd' }}>Product</th>
              <th style={{ textAlign: 'center', padding: '12px', border: '1px solid #ddd' }}>Quantity</th>
              <th style={{ textAlign: 'right', padding: '12px', border: '1px solid #ddd' }}>Price</th>
              <th style={{ padding: '12px', border: '1px solid #ddd' }}>Actions</th>
            </tr>
            </thead>
            <tbody>
            {wishlists.length === 0 ? (
                <tr>
                  <td colSpan={4} style={{ textAlign: 'center', padding: '25px', color: '#555' }}>
                    Your wishlist is empty
                  </td>
                </tr>
            ) : (
                wishlists.map((item) => (
                    <tr key={item.wishlistId}>
                      <td style={{ padding: '12px', border: '1px solid #ddd' }}>{item.productName}</td>
                      <td style={{ textAlign: 'center', padding: '12px', border: '1px solid #ddd' }}>{item.quantity}</td>
                      <td style={{ textAlign: 'right', padding: '12px', border: '1px solid #ddd' }}>
                        ${item.price.toFixed(2)}
                      </td>
                      <td style={{ padding: '12px', border: '1px solid #ddd', textAlign: 'center' }}>
                        <button
                            onClick={() => handleDelete(item.wishlistId)}
                            style={{
                              backgroundColor: '#ff4d4d',
                              color: '#fff',
                              border: 'none',
                              padding: '8px 12px',
                              borderRadius: '8px',
                              cursor: 'pointer',
                            }}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                ))
            )}
            </tbody>
          </table>
        </div>
      </div>
  );
};

export default WishlistPage;
