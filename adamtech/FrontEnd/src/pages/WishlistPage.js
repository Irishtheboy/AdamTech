// src/pages/WishlistPage.js
import React, { useEffect, useState } from 'react';
import { getWishlist, removeFromWishlist } from '../services/wishlistService';

const WishlistPage = ({ user, addToCart }) => {
  const [wishlistItems, setWishlistItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [addingToCart, setAddingToCart] = useState({});

  const fetchWishlist = async () => {
    try {
      setLoading(true);
      const data = await getWishlist();
      setWishlistItems(data);
    } catch (err) {
      console.error('Error fetching wishlist:', err);
      alert('Failed to load wishlist. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (wishlistId) => {
    try {
      await removeFromWishlist(wishlistId);
      setWishlistItems((prev) => prev.filter((item) => item.wishlistId !== wishlistId));
      alert('Item removed from wishlist');
    } catch (err) {
      console.error('Error deleting from wishlist:', err);
      alert('Failed to remove item from wishlist. Please try again.');
    }
  };

  const handleAddToCart = async (item) => {
    if (!user) {
      alert("Please log in first.");
      return;
    }

    setAddingToCart(prev => ({ ...prev, [item.wishlistId]: true }));

    try {
      // Use the addToCart function from props with the correct structure
      await addToCart({
        productId: item.product.productId,
        name: item.product.name,
        price: item.product.price,
        quantity: 1
      });

      alert(`${item.product.name} added to cart!`);
    } catch (err) {
      console.error('Error adding to cart:', err);
      alert('Failed to add item to cart. Please try again.');
    } finally {
      setAddingToCart(prev => ({ ...prev, [item.wishlistId]: false }));
    }
  };

  const handleAddToCartAndRemove = async (item) => {
    if (!user) {
      alert("Please log in first.");
      return;
    }

    setAddingToCart(prev => ({ ...prev, [item.wishlistId]: true }));

    try {
      // Use the addToCart function from props with the correct structure
      await addToCart({
        productId: item.product.productId,
        name: item.product.name,
        price: item.product.price,
        quantity: 1
      });

      // Remove from wishlist
      await removeFromWishlist(item.wishlistId);
      setWishlistItems((prev) => prev.filter((wishlistItem) => wishlistItem.wishlistId !== item.wishlistId));

      alert(`${item.product.name} added to cart and removed from wishlist!`);
    } catch (err) {
      console.error('Error adding to cart:', err);
      alert('Failed to add item to cart. Please try again.');
    } finally {
      setAddingToCart(prev => ({ ...prev, [item.wishlistId]: false }));
    }
  };

  useEffect(() => {
    fetchWishlist();
  }, []);

  if (loading) {
    return <div style={{ textAlign: 'center', padding: '50px' }}>Loading wishlist...</div>;
  }

  return (
      <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '20px' }}>
        <h1 style={{ textAlign: 'center', marginBottom: '25px', color: '#333' }}>My Wishlist</h1>

        <div style={{ overflowX: 'auto' }}>
          <table style={{ width: '100%', borderCollapse: 'collapse', minWidth: '800px', backgroundColor: '#fff', borderRadius: '10px', overflow: 'hidden', boxShadow: '0 4px 12px rgba(0,0,0,0.1)' }}>
            <thead style={{ backgroundColor: 'orange', color: '#fff' }}>
            <tr>
              <th style={{ textAlign: 'left', padding: '15px', border: '1px solid #ddd', fontSize: '1.1rem' }}>Product</th>
              <th style={{ textAlign: 'center', padding: '15px', border: '1px solid #ddd', fontSize: '1.1rem' }}>Price</th>
              <th style={{ textAlign: 'center', padding: '15px', border: '1px solid #ddd', fontSize: '1.1rem' }}>Date Added</th>
              <th style={{ padding: '15px', border: '1px solid #ddd', fontSize: '1.1rem', textAlign: 'center' }}>Actions</th>
            </tr>
            </thead>
            <tbody>
            {wishlistItems.length === 0 ? (
                <tr>
                  <td colSpan={4} style={{ textAlign: 'center', padding: '40px', color: '#555', fontSize: '1.1rem' }}>
                    Your wishlist is empty
                  </td>
                </tr>
            ) : (
                wishlistItems.map((item) => (
                    <tr key={item.wishlistId} style={{ borderBottom: '1px solid #eee', transition: 'background 0.2s' }}>
                      <td style={{ padding: '15px', border: '1px solid #ddd' }}>
                        <div style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
                          {item.product && (
                              <>
                                <img
                                    src={`http://localhost:8080/adamtech/products/${item.product.productId}/image`}
                                    alt={item.product.name}
                                    style={{
                                      width: '60px',
                                      height: '60px',
                                      objectFit: 'cover',
                                      borderRadius: '8px',
                                      border: '1px solid #ddd'
                                    }}
                                    onError={(e) => {
                                      e.target.style.display = 'none';
                                    }}
                                />
                                <div>
                                  <div style={{ fontWeight: '600', color: '#333', marginBottom: '5px' }}>
                                    {item.product.name}
                                  </div>
                                  {item.product.description && (
                                      <div style={{ fontSize: '0.9rem', color: '#666', maxWidth: '300px' }}>
                                        {item.product.description.length > 100
                                            ? `${item.product.description.substring(0, 100)}...`
                                            : item.product.description
                                        }
                                      </div>
                                  )}
                                </div>
                              </>
                          )}
                        </div>
                      </td>
                      <td style={{ textAlign: 'center', padding: '15px', border: '1px solid #ddd', fontWeight: 'bold', color: '#f4a261', fontSize: '1.1rem' }}>
                        R{item.product?.price?.amount?.toFixed(2) || '0.00'}
                      </td>
                      <td style={{ textAlign: 'center', padding: '15px', border: '1px solid #ddd', color: '#555' }}>
                        {item.dateAdded ? new Date(item.dateAdded).toLocaleDateString() : 'Unknown'}
                      </td>
                      <td style={{ padding: '15px', border: '1px solid #ddd', textAlign: 'center' }}>
                        <div style={{ display: 'flex', flexDirection: 'column', gap: '8px', alignItems: 'center' }}>
                          {/* Add to Cart Button */}
                          <button
                              onClick={() => handleAddToCart(item)}
                              disabled={addingToCart[item.wishlistId]}
                              style={{
                                backgroundColor: addingToCart[item.wishlistId] ? '#ccc' : '#2a9d8f',
                                color: '#fff',
                                border: 'none',
                                padding: '8px 16px',
                                borderRadius: '6px',
                                cursor: addingToCart[item.wishlistId] ? 'not-allowed' : 'pointer',
                                fontWeight: '600',
                                fontSize: '0.9rem',
                                width: '140px',
                                transition: 'all 0.2s ease',
                                opacity: addingToCart[item.wishlistId] ? 0.7 : 1
                              }}
                              onMouseOver={(e) => {
                                if (!addingToCart[item.wishlistId]) {
                                  e.target.style.backgroundColor = '#21867a';
                                }
                              }}
                              onMouseOut={(e) => {
                                if (!addingToCart[item.wishlistId]) {
                                  e.target.style.backgroundColor = '#2a9d8f';
                                }
                              }}
                          >
                            {addingToCart[item.wishlistId] ? 'Adding...' : 'Add to Cart'}
                          </button>

                          {/* Add to Cart & Remove Button */}
                          <button
                              onClick={() => handleAddToCartAndRemove(item)}
                              disabled={addingToCart[item.wishlistId]}
                              style={{
                                backgroundColor: addingToCart[item.wishlistId] ? '#ccc' : '#f4a261',
                                color: '#fff',
                                border: 'none',
                                padding: '8px 16px',
                                borderRadius: '6px',
                                cursor: addingToCart[item.wishlistId] ? 'not-allowed' : 'pointer',
                                fontWeight: '600',
                                fontSize: '0.9rem',
                                width: '140px',
                                transition: 'all 0.2s ease',
                                opacity: addingToCart[item.wishlistId] ? 0.7 : 1
                              }}
                              onMouseOver={(e) => {
                                if (!addingToCart[item.wishlistId]) {
                                  e.target.style.backgroundColor = '#e39352';
                                }
                              }}
                              onMouseOut={(e) => {
                                if (!addingToCart[item.wishlistId]) {
                                  e.target.style.backgroundColor = '#f4a261';
                                }
                              }}
                          >
                            {addingToCart[item.wishlistId] ? 'Adding...' : 'Add & Remove'}
                          </button>

                          {/* Remove Button */}
                          <button
                              onClick={() => handleDelete(item.wishlistId)}
                              disabled={addingToCart[item.wishlistId]}
                              style={{
                                backgroundColor: addingToCart[item.wishlistId] ? '#ccc' : '#e76f51',
                                color: '#fff',
                                border: 'none',
                                padding: '8px 16px',
                                borderRadius: '6px',
                                cursor: addingToCart[item.wishlistId] ? 'not-allowed' : 'pointer',
                                fontWeight: '600',
                                fontSize: '0.9rem',
                                width: '140px',
                                transition: 'all 0.2s ease',
                                opacity: addingToCart[item.wishlistId] ? 0.7 : 1
                              }}
                              onMouseOver={(e) => {
                                if (!addingToCart[item.wishlistId]) {
                                  e.target.style.backgroundColor = '#d65c40';
                                }
                              }}
                              onMouseOut={(e) => {
                                if (!addingToCart[item.wishlistId]) {
                                  e.target.style.backgroundColor = '#e76f51';
                                }
                              }}
                          >
                            Remove
                          </button>
                        </div>
                      </td>
                    </tr>
                ))
            )}
            </tbody>
          </table>
        </div>

        {/* Quick Stats */}
        {wishlistItems.length > 0 && (
            <div style={{
              marginTop: '20px',
              padding: '15px',
              backgroundColor: '#f8f9fa',
              borderRadius: '8px',
              border: '1px solid #dee2e6'
            }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <span style={{ fontWeight: '600', color: '#333' }}>
              Total Items: {wishlistItems.length}
            </span>
                <span style={{ fontWeight: '600', color: '#f4a261' }}>
              Total Value: R{wishlistItems.reduce((sum, item) => sum + (item.product?.price?.amount || 0), 0).toFixed(2)}
            </span>
              </div>
            </div>
        )}
      </div>
  );
};

export default WishlistPage;