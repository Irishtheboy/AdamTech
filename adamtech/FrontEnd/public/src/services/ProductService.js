import axios from "axios";

const BASE_URL = "http://localhost:8080/adamtech/products";

export const getProductById = async (id) => {
  try {
    const res = await axios.get(`${BASE_URL}/read/${id}`);
    return res.data;
  } catch (err) {
    console.error("Failed to fetch product:", err);
    throw err;
  }
};

export const getProductImage = (id) => {
  return `${BASE_URL}/${id}/image`; // returns URL for <img src={}>
};
