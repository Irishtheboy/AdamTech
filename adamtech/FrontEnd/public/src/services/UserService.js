// src/services/UserService.js
import axios from "axios";

const API_URL = "http://localhost:8080/adamtech";

export const getCurrentUser = async () => {
  try {
    const res = await axios.get(`${API_URL}/customer/current`, { withCredentials: true });
    return res.data;
  } catch (err) {
    console.error("No logged-in user found:", err);
    return null;
  }
};
