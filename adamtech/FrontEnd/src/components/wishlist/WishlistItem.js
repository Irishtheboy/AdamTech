import React from 'react';

const WishlistItem = ({ wishlist, onDelete }) => {
    return (
        <div className="account-card">
            <h3 className="section-title">{wishlist.product?.name || 'Unnamed Product'}</h3>
            <p>Added by: {wishlist.customer?.email || 'Unknown'}</p>
            <button className="orange-btn" onClick={() => onDelete(wishlist.wishlistId)}>
                Remove
            </button>
        </div>
    );
};

export default WishlistItem;
