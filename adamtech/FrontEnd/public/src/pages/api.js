// src/api.js
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/adamtech",
  withCredentials: true, // keep cookies if you need them
});

// Attach JWT automatically to all requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("accessToken");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
