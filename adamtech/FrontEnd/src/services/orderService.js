import axios from 'axios';

const BASE_URL = 'http://localhost:8080/adamtech/order';
const BASE_URLP = 'http://localhost:8080/adamtech/product';
const BASE_URLI = 'http://localhost:8080/adamtech/orderitem';

export const getAllOrders = async () => {
  const response = await axios.get(`${BASE_URL}/getAll`);
  return response.data;
};

export const createOrder = async (order) => {
  const response = await axios.post(`${BASE_URL}/create`, order);
  return response.data;
};

export const deleteOrder = async (id) => {
  await axios.delete(`${BASE_URL}/delete/${id}`);
};
