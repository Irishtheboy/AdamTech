import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import axios from "axios";

import Home from "./pages/Home";
import SignUp from "./pages/SignUp";
import Login from "./pages/Login";
import Shop from "./pages/Shop";
import ProfileDash from "./pages/ProfileDash";
import Cart from "./pages/Cart";
import MyOrders from "./pages/MyOrders";
import WishlistPage from "./pages/WishlistPage";
import EditProfile from "./pages/EditProfile";
import About from "./pages/About";
import Contact from "./pages/Contact";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import ProductDetails from "./pages/ProductDetails";
import Checkout from "./pages/Checkout";
import AdminOrders from "./pages/AdminOrders";
import AdminDashboard from "./pages/AdminDashboard";

function App() {
  const [user, setUser] = useState(null);
  const [cartItems, setCartItems] = useState([]);
  const [orders, setOrders] = useState([]);

  // ✅ FIXED: Check if a user session exists using the correct endpoint
  useEffect(() => {
    const token = localStorage.getItem("token");
    
    if (token) {
      axios
        .get("http://localhost:8080/adamtech/customer/me", {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        })
        .then((res) => {
          console.log("User data loaded:", res.data);
          setUser(res.data);
        })
        .catch((error) => {
          console.error("Error loading user:", error);
          setUser(null);
          // Clear invalid token
          localStorage.removeItem("token");
          localStorage.removeItem("user");
        });
    } else {
      setUser(null);
    }
  }, []);

  // ✅ Add item to cart (frontend + backend, linked to user cart)
  const addToCart = async (product) => {
    if (!user) {
      alert("Please log in first.");
      return;
    }

    try {
      const token = localStorage.getItem("token");
      
      // 1️⃣ Get or create the user's cart
      const cartRes = await axios.get(
        `http://localhost:8080/adamtech/cart/customer/${user.email}`,
        { 
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        }
      );
      const cart = cartRes.data;

      // 2️⃣ Prepare CartItem payload linked to that cart
      const cartItem = {
        product: { productId: product.productId },
        quantity: 1,
        cart: { cartId: cart.cartId },
      };

      // 3️⃣ Save cart item in backend
      const res = await axios.post(
        "http://localhost:8080/adamtech/cart-items/create",
        cartItem,
        { 
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        }
      );

      const savedItem = res.data;

      // 4️⃣ Update frontend state for instant UI
      const existingItem = cartItems.find(
        (item) => item.productId === savedItem.productId
      );
      if (existingItem) {
        setCartItems(
          cartItems.map((item) =>
            item.productId === savedItem.productId
              ? { ...item, quantity: item.quantity + 1 }
              : item
          )
        );
      } else {
        setCartItems([...cartItems, savedItem]);
      }

      console.log("✅ Cart updated:", savedItem);
    } catch (error) {
      console.error("❌ Error adding to cart:", error);
      alert("Could not add item to cart.");
    }
  };

  // Remove item from cart
  const removeItem = (name) => {
    setCartItems(cartItems.filter((item) => item.name !== name));
  };

  // Update quantity
  const updateQuantity = (name, amount) => {
    setCartItems(
      cartItems.map((item) =>
        item.name === name
          ? { ...item, quantity: Math.max(item.quantity + amount, 1) }
          : item
      )
    );
  };

  // Checkout
  const checkout = () => {
    if (!cartItems.length) return;

    const total = cartItems.reduce(
      (sum, item) => sum + item.price.amount * item.quantity,
      0
    );
    const newOrder = {
      id: orders.length + 1,
      date: new Date().toLocaleDateString(),
      status: "Processing",
      total,
      items: cartItems,
    };

    setOrders([...orders, newOrder]);
    setCartItems([]);
  };

  return (
    <Router>
     
<Header user={user} setUser={setUser} />
      <main>
        <Routes>
          <Route path="/" element={<Home addToCart={addToCart} user={user} />} />
          <Route path="/signUp" element={<SignUp setUser={setUser} />} />
          <Route path="/login" element={<Login setUser={setUser} />} />
          <Route path="/shop" element={<Shop addToCart={addToCart} user={user} />} />
          <Route
            path="/product/:id"
            element={<ProductDetails addToCart={addToCart} user={user} />}
          />

          <Route
            path="/cart"
            element={
              <Cart
                cartItems={cartItems}
                removeItem={removeItem}
                updateQuantity={updateQuantity}
                checkout={checkout}
              />
            }
          />
          <Route path="/orders" element={<MyOrders orders={orders} />} />
          <Route path="/wishlist" element={<WishlistPage />} />
          <Route path="/account/edit" element={<EditProfile />} />
          <Route path="/profile" element={<ProfileDash user={user} />} />
          <Route path="/about" element={<About />} />
          <Route path="/contact" element={<Contact />} />
          <Route path="/checkout" element={<Checkout />} />
          <Route path="/admin/orders" element={<AdminOrders />} />
          <Route path="/admin/dashboard" element={<AdminDashboard />} />
        </Routes>
      </main>
      <Footer />
    </Router>
  );
}

export default App;