import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Cart() {
  const [user, setUser] = useState(null);
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const DELIVERY_FEE = 150; // R150 delivery fee

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) {
          setUser(null);
          return;
        }

        const res = await axios.get("http://localhost:8080/adamtech/customer/me", {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
        if (res.data && res.data.email) setUser(res.data);
      } catch (err) {
        console.error("No logged-in user found:", err);
        setUser(null);
        localStorage.removeItem("token");
        localStorage.removeItem("user");
      }
    };
    fetchUser();
  }, []);

  useEffect(() => {
    if (!user?.email) return;

    const fetchCart = async () => {
      try {
        setLoading(true);
        const token = localStorage.getItem("token");
        const res = await axios.get(
            `http://localhost:8080/adamtech/cart/customer/${user.email}`,
            {
              headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
              }
            }
        );
        const items = res.data.cartItems || [];
        const fullItems = await Promise.all(
            items.map(async (item) => {
              if (!item.product || !item.product.name) {
                const prodRes = await axios.get(
                    `http://localhost:8080/adamtech/products/read/${item.product?.productId}`
                );
                item.product = prodRes.data;
              }
              return item;
            })
        );
        setCartItems(fullItems);
      } catch (err) {
        console.error("Failed to fetch cart:", err);
        if (err.response?.status === 401 || err.response?.status === 403) {
          localStorage.removeItem("token");
          localStorage.removeItem("user");
          setUser(null);
        }
      } finally {
        setLoading(false);
      }
    };
    fetchCart();
  }, [user]);

  const removeItem = async (cartItemId) => {
    try {
      const token = localStorage.getItem("token");
      await axios.delete(
          `http://localhost:8080/adamtech/cart-items/delete/${cartItemId}`,
          {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
            }
          }
      );
      setCartItems(cartItems.filter((item) => item.cartItemId !== cartItemId));
    } catch (err) {
      console.error("Failed to remove item:", err);
      if (err.response?.status === 401 || err.response?.status === 403) {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        setUser(null);
      }
    }
  };

  const updateQuantity = async (cartItemId, amount) => {
    const item = cartItems.find((i) => i.cartItemId === cartItemId);
    if (!item) return;
    const newQuantity = Math.max(item.quantity + amount, 1);

    try {
      const token = localStorage.getItem("token");
      await axios.put(
          "http://localhost:8080/adamtech/cart-items/update",
          { ...item, quantity: newQuantity },
          {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
            }
          }
      );
      setCartItems(
          cartItems.map((i) =>
              i.cartItemId === cartItemId ? { ...i, quantity: newQuantity } : i
          )
      );
    } catch (err) {
      console.error("Failed to update quantity:", err);
      if (err.response?.status === 401 || err.response?.status === 403) {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        setUser(null);
      }
    }
  };

  const checkout = async () => {
    if (!cartItems.length) return;

    const subtotal = cartItems.reduce(
        (sum, item) => sum + (item.product?.price?.amount || 0) * item.quantity,
        0
    );

    const total = subtotal + DELIVERY_FEE;

    // FIXED: Send Money object structure that matches your backend
    const orderPayload = {
      customer: { email: user.email },
      orderStatus: "PENDING",
      // Send as Money object with amount and currency - THIS IS THE FIX
      totalAmount: {
        amount: Math.round(total), // Convert to int as your Money class expects
        currency: "ZAR"
      },
      orderItems: cartItems.map(item => ({
        product: { productId: item.product.productId },
        quantity: item.quantity,
        // Also send unitPrice as Money object if your OrderItem expects it
        unitPrice: {
          amount: Math.round(item.product?.price?.amount || 0),
          currency: item.product?.price?.currency || "ZAR"
        }
      }))
    };

    console.log("Order payload:", JSON.stringify(orderPayload, null, 2));
    console.log("Total amount type:", typeof orderPayload.totalAmount);
    console.log("Total amount value:", orderPayload.totalAmount);

    try {
      const token = localStorage.getItem("token");
      const res = await axios.post(
          "http://localhost:8080/adamtech/order/create",
          orderPayload,
          {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
            }
          }
      );

      console.log("Order creation response:", res.data);

      if (res.data?.orderId || res.data?.id) {
        const orderId = res.data.orderId || res.data.id;
        navigate("/checkout", {
          state: {
            user,
            cartItems,
            subtotal: subtotal,
            deliveryFee: DELIVERY_FEE,
            total: total,
            orderId: orderId
          }
        });

        // Clear cart after successful order creation
        setCartItems([]);
      } else {
        alert("Order created but no order ID returned. Please contact support.");
      }
    } catch (err) {
      console.error("Failed to create order:", err);
      console.error("Error response:", err.response?.data);
      console.error("Error status:", err.response?.status);

      if (err.response?.status === 401 || err.response?.status === 403) {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        setUser(null);
        alert("Please login again to continue.");
      } else {
        alert(`Failed to create order: ${err.response?.data?.message || err.message}`);
      }
    }
  };

  const subtotal = cartItems.reduce(
      (sum, item) => sum + (item.product?.price?.amount || 0) * item.quantity,
      0
  );

  const total = subtotal + DELIVERY_FEE;

  if (!user) return (
      <div style={{ padding: "40px", textAlign: "center" }}>
        <h3>Please log in to view your cart</h3>
        <button
            onClick={() => navigate("/login")}
            style={{
              marginTop: "20px",
              padding: "10px 20px",
              backgroundColor: "#f4a261",
              color: "white",
              border: "none",
              borderRadius: "5px",
              cursor: "pointer"
            }}
        >
          Go to Login
        </button>
      </div>
  );

  if (loading) {
    return (
        <div style={{ padding: "40px", textAlign: "center" }}>
          <h3>Loading your cart...</h3>
        </div>
    );
  }

  return (
      <div style={{ padding: "40px 20px", maxWidth: "1200px", margin: "0 auto", fontFamily: "'Segoe UI', sans-serif" }}>
        <h2 style={{ marginBottom: "30px", color: "#333", textAlign: "center", fontSize: "2rem" }}>Your Cart</h2>

        {cartItems.length === 0 ? (
            <div style={{ textAlign: "center", padding: "60px" }}>
              <h3 style={{ color: "#666", marginBottom: "20px" }}>Your cart is empty</h3>
              <button
                  onClick={() => navigate("/shop")}
                  style={{
                    padding: "12px 24px",
                    backgroundColor: "#f4a261",
                    color: "white",
                    border: "none",
                    borderRadius: "8px",
                    cursor: "pointer",
                    fontSize: "1rem",
                    fontWeight: "bold"
                  }}
              >
                Continue Shopping
              </button>
            </div>
        ) : (
            <>
              <div style={{ overflowX: "auto" }}>
                <table style={{ width: "100%", borderCollapse: "collapse", minWidth: "700px" }}>
                  <thead style={{ backgroundColor: "orange", color: "#fff" }}>
                  <tr>
                    <th style={{ textAlign: "left", padding: "15px", border: "1px solid #ddd", fontSize: "1.1rem" }}>Product</th>
                    <th style={{ textAlign: "center", padding: "15px", border: "1px solid #ddd", fontSize: "1.1rem" }}>Price</th>
                    <th style={{ textAlign: "center", padding: "15px", border: "1px solid #ddd", fontSize: "1.1rem" }}>Quantity</th>
                    <th style={{ textAlign: "right", padding: "15px", border: "1px solid #ddd", fontSize: "1.1rem" }}>Subtotal</th>
                    <th style={{ padding: "15px", border: "1px solid #ddd", fontSize: "1.1rem" }}></th>
                  </tr>
                  </thead>
                  <tbody>
                  {cartItems.map((item) => (
                      <tr key={item.cartItemId} style={{ borderBottom: "1px solid #eee", transition: "background 0.2s" }}>
                        <td style={{ padding: "15px", border: "1px solid #ddd" }}>
                          <div style={{ display: "flex", alignItems: "center", gap: "15px" }}>
                            <img
                                src={`http://localhost:8080/adamtech/products/${item.product.productId}/image`}
                                alt={item.product.name}
                                style={{
                                  width: "80px",
                                  height: "80px",
                                  objectFit: "cover",
                                  borderRadius: "8px",
                                  border: "1px solid #ddd"
                                }}
                                onError={(e) => {
                                  // Fallback if image fails to load
                                  e.target.style.display = 'none';
                                  e.target.nextSibling.style.display = 'block';
                                }}
                            />
                            <div
                                style={{
                                  display: 'none',
                                  width: "80px",
                                  height: "80px",
                                  backgroundColor: "#f5f5f5",
                                  borderRadius: "8px",
                                  display: "flex",
                                  alignItems: "center",
                                  justifyContent: "center",
                                  color: "#999",
                                  fontSize: "0.8rem",
                                  textAlign: "center"
                                }}
                            >
                              No Image
                            </div>
                            <div>
                              <div style={{ fontWeight: "600", color: "#333", marginBottom: "5px", fontSize: "1.1rem" }}>
                                {item.product?.name || "Unnamed Product"}
                              </div>
                              {item.product?.description && (
                                  <div style={{ color: "#666", fontSize: "0.9rem", maxWidth: "300px" }}>
                                    {item.product.description.length > 80
                                        ? `${item.product.description.substring(0, 80)}...`
                                        : item.product.description
                                    }
                                  </div>
                              )}
                            </div>
                          </div>
                        </td>
                        <td style={{ textAlign: "center", padding: "15px", border: "1px solid #ddd", fontWeight: "bold", color: "#f4a261", fontSize: "1.1rem" }}>
                          R{item.product?.price?.amount?.toFixed(2) || "0.00"}
                        </td>
                        <td style={{ textAlign: "center", padding: "15px", border: "1px solid #ddd" }}>
                          <div style={{ display: "flex", alignItems: "center", justifyContent: "center", gap: "10px" }}>
                            <button
                                onClick={() => updateQuantity(item.cartItemId, -1)}
                                style={{
                                  padding: "8px 12px",
                                  borderRadius: "5px",
                                  border: "1px solid #ccc",
                                  cursor: "pointer",
                                  backgroundColor: "#fff",
                                  fontSize: "1rem",
                                  minWidth: "40px"
                                }}
                            >-</button>
                            <span style={{
                              padding: "8px 16px",
                              border: "1px solid #ddd",
                              borderRadius: "5px",
                              minWidth: "50px",
                              display: "inline-block",
                              fontWeight: "bold"
                            }}>
                            {item.quantity}
                          </span>
                            <button
                                onClick={() => updateQuantity(item.cartItemId, 1)}
                                style={{
                                  padding: "8px 12px",
                                  borderRadius: "5px",
                                  border: "1px solid #ccc",
                                  cursor: "pointer",
                                  backgroundColor: "#fff",
                                  fontSize: "1rem",
                                  minWidth: "40px"
                                }}
                            >+</button>
                          </div>
                        </td>
                        <td style={{ textAlign: "right", padding: "15px", border: "1px solid #ddd", fontWeight: "bold", color: "#f4a261", fontSize: "1.1rem" }}>
                          R{((item.product?.price?.amount || 0) * item.quantity).toFixed(2)}
                        </td>
                        <td style={{ textAlign: "center", padding: "15px", border: "1px solid #ddd" }}>
                          <button
                              onClick={() => removeItem(item.cartItemId)}
                              style={{
                                backgroundColor: "#e76f51",
                                color: "#fff",
                                border: "none",
                                padding: "8px 12px",
                                borderRadius: "6px",
                                cursor: "pointer",
                                fontSize: "0.9rem",
                                fontWeight: "600"
                              }}
                              onMouseOver={(e) => e.currentTarget.style.backgroundColor = "#d65c40"}
                              onMouseOut={(e) => e.currentTarget.style.backgroundColor = "#e76f51"}
                          >
                            Remove
                          </button>
                        </td>
                      </tr>
                  ))}
                  </tbody>
                </table>
              </div>

              <div
                  style={{
                    marginTop: "30px",
                    maxWidth: "400px",
                    marginLeft: "auto",
                    backgroundColor: "#f8f8f8",
                    padding: "25px",
                    borderRadius: "12px",
                    boxShadow: "0 4px 12px rgba(0,0,0,0.1)"
                  }}
              >
                <h3 style={{ marginBottom: "20px", fontSize: "1.5rem", color: "#333" }}>Cart Totals</h3>

                {/* Subtotal */}
                <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "12px", fontSize: "1.1rem" }}>
                  <span>Subtotal</span>
                  <span style={{ color: "#333" }}>R{subtotal.toFixed(2)}</span>
                </div>

                {/* Delivery Fee */}
                <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "12px", fontSize: "1.1rem" }}>
                  <span>Delivery Fee</span>
                  <span style={{ color: "#333" }}>R{DELIVERY_FEE.toFixed(2)}</span>
                </div>

                {/* Separator */}
                <hr style={{ border: "none", borderTop: "1px solid #ddd", margin: "15px 0" }} />

                {/* Total */}
                <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "25px", fontWeight: "bold", fontSize: "1.3rem" }}>
                  <span>Total</span>
                  <span style={{ color: "#f4a261" }}>R{total.toFixed(2)}</span>
                </div>

                {/* Delivery Info Note */}
                <div style={{
                  backgroundColor: "#e8f5e8",
                  padding: "10px 15px",
                  borderRadius: "6px",
                  marginBottom: "20px",
                  fontSize: "0.9rem",
                  color: "#2d5a2d",
                  border: "1px solid #c8e6c9"
                }}>
                  <strong>🚚 Free delivery</strong> on orders over R1000
                </div>

                <button
                    onClick={checkout}
                    style={{
                      width: "100%",
                      padding: "14px",
                      backgroundColor: "#2a9d8f",
                      color: "#fff",
                      border: "none",
                      borderRadius: "10px",
                      fontWeight: "bold",
                      fontSize: "1.1rem",
                      cursor: "pointer",
                      transition: "background 0.2s"
                    }}
                    onMouseOver={(e) => e.currentTarget.style.backgroundColor = "#21867a"}
                    onMouseOut={(e) => e.currentTarget.style.backgroundColor = "#2a9d8f"}
                >
                  PROCEED TO CHECKOUT
                </button>
              </div>
            </>
        )}
      </div>
  );
}

export default Cart;