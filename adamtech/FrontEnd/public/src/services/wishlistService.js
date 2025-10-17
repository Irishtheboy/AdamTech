// src/services/wishlistService.js
import axios from 'axios';

const BASE_URL = 'http://localhost:8080/adamtech/wishlist';

export const getAllWishlists = async () => {
    const response = await axios.get(`${BASE_URL}/getAll`);
    return response.data;
};

export const createWishlist = async (wishlist) => {
    const response = await axios.post(`${BASE_URL}/create`, wishlist);
    return response.data;
};

export const deleteWishlist = async (id) => {
    await axios.delete(`${BASE_URL}/delete/${id}`);
};
