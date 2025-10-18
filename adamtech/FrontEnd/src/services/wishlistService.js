// src/services/wishlistService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/adamtech';

const getAuthToken = () => {
    return localStorage.getItem('token');
};

const getAuthHeaders = () => {
    const token = getAuthToken();
    return {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };
};

// Get all wishlist items for current user
export const getWishlist = async () => {
    try {
        const response = await axios.get(`${API_URL}/wishlist/getAll`, {
            headers: getAuthHeaders()
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching wishlist:', error);
        throw error;
    }
};

// Add item to wishlist - uses your actual backend endpoint
export const addToWishlist = async (productId) => {
    try {
        // Your backend expects a Wishlist object with product and customer
        const wishlistItem = {
            product: { productId: productId },
            // You'll need to get the current customer from your user context
            customer: { email: getCurrentUserEmail() } // You'll need to implement this
        };

        const response = await axios.post(`${API_URL}/wishlist/create`,
            wishlistItem,
            { headers: getAuthHeaders() }
        );
        return response.data;
    } catch (error) {
        console.error('Error adding to wishlist:', error);
        throw error;
    }
};

// Remove item from wishlist - uses your actual backend endpoint
export const removeFromWishlist = async (wishlistId) => {
    try {
        const response = await axios.delete(`${API_URL}/wishlist/delete/${wishlistId}`, {
            headers: getAuthHeaders()
        });
        return response.data;
    } catch (error) {
        console.error('Error removing from wishlist:', error);
        throw error;
    }
};

// Check if product is in wishlist - we'll implement this by fetching all and filtering
export const checkWishlistStatus = async (productId) => {
    try {
        const wishlist = await getWishlist();
        const wishlistItem = wishlist.find(item =>
            item.product && item.product.productId === productId
        );
        return {
            isInWishlist: !!wishlistItem,
            wishlistItemId: wishlistItem ? wishlistItem.wishlistId : null
        };
    } catch (error) {
        console.error('Error checking wishlist status:', error);
        return { isInWishlist: false };
    }
};

// Helper function to get current user email (you'll need to implement this)
const getCurrentUserEmail = () => {
    // Try to get from localStorage or your user context
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    return user.email;
};

// Legacy functions for compatibility
export const getAllWishlists = getWishlist;

export const createWishlist = addToWishlist;

export const deleteWishlist = removeFromWishlist;