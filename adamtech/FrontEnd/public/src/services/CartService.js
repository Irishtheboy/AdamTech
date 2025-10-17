// src/services/CartService.js
import axios from "axios";

const API_URL = "http://localhost:8080/adamtech";

export const getCartByUserEmail = async (email) => {
  try {
    const res = await axios.get(`${API_URL}/cart/customer/${email}`, { withCredentials: true });
    return res.data.cartItems || [];
  } catch (err) {
    console.error("Failed to fetch cart:", err);
    return [];
  }
};

export const addCartItem = async (cartItem) => {
  try {
    const res = await axios.post(`${API_URL}/cart-items/create`, cartItem, { withCredentials: true });
    return res.data;
  } catch (err) {
    console.error("Failed to add item to cart:", err);
    return null;
  }
};

export const updateCartItem = async (cartItem) => {
  try {
    const res = await axios.put(`${API_URL}/cart-items/update`, cartItem, { withCredentials: true });
    return res.data;
  } catch (err) {
    console.error("Failed to update cart item:", err);
    return null;
  }
};

export const deleteCartItem = async (cartItemId) => {
  try {
    await axios.delete(`${API_URL}/cart-items/delete/${cartItemId}`, { withCredentials: true });
    return true;
  } catch (err) {
    console.error("Failed to remove cart item:", err);
    return false;
  }
};
