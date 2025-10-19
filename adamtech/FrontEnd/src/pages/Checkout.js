import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

const Checkout = () => {
  const location = useLocation();
  const navigate = useNavigate();

  // Data passed from Cart page
  const { user, cartItems, subtotal, orderId } = location.state || {};
  const [statusMessage, setStatusMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  if (!user || !cartItems || cartItems.length === 0) {
    return (
        <div style={{ padding: "40px", textAlign: "center" }}>
          <h2>No Order Information Found</h2>
          <p style={{ marginBottom: "20px" }}>Please go back to your cart and complete the checkout process.</p>
          <button
              onClick={() => navigate("/cart")}
              style={{
                padding: "12px 25px",
                backgroundColor: "#f4a261",
                color: "#fff",
                border: "none",
                borderRadius: "8px",
                cursor: "pointer",
                fontSize: "1rem"
              }}
          >
            Back to Cart
          </button>
        </div>
    );
  }

  // Improved API call to update order status
  const handleMarkAsPaid = async () => {
    if (!orderId) {
      setStatusMessage("❌ No order ID found. Please complete the checkout process first.");
      return;
    }

    setIsLoading(true);
    setStatusMessage("");

    try {
      const token = localStorage.getItem("token");

      const response = await axios.put(
          `http://localhost:8080/adamtech/order/${orderId}/status`,
          { status: "COMPLETED" }, // Send status in request body
          {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
            }
          }
      );

      if (response.status === 200) {
        setStatusMessage("✅ Order marked as COMPLETED successfully!");

        // Optional: Clear cart after successful order
        setTimeout(() => {
          navigate("/orders", {
            state: {
              message: "Order completed successfully!",
              orderId: orderId
            }
          });
        }, 2000);
      }
    } catch (error) {
      console.error("Error updating order:", error);

      if (error.response?.status === 403) {
        setStatusMessage("❌ Access denied. Please make sure you're logged in.");
      } else if (error.response?.status === 404) {
        setStatusMessage("❌ Order not found. Please try checking out again.");
      } else {
        setStatusMessage("⚠️ Error connecting to server. Please try again.");
      }
    } finally {
      setIsLoading(false);
    }
  };

  // Calculate totals
  const calculateItemTotal = (item) => {
    return ((item.product?.price?.amount || 0) * item.quantity).toFixed(2);
  };

  return (
      <div style={{
        maxWidth: "95%",
        margin: "0 auto",
        padding: "40px 20px",
        fontFamily: "'Segoe UI', sans-serif",
        backgroundColor: "#f8f9fa",
        minHeight: "100vh"
      }}>

        {/* Header */}
        <div style={{
          backgroundColor: "white",
          padding: "30px",
          borderRadius: "12px",
          boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
          marginBottom: "30px",
          textAlign: "center"
        }}>
          <h1 style={{
            marginBottom: "10px",
            color: "orange",
            fontSize: "2.5rem",
            fontWeight: "bold"
          }}>
            Order Confirmation
          </h1>
          <p style={{ color: "#666", fontSize: "1.1rem" }}>
            Thank you for your purchase, {user.firstName}!
          </p>
          {orderId && (
              <p style={{
                marginTop: "10px",
                color: "orange",
                fontWeight: "bold",
                backgroundColor: "#e9f5f3",
                padding: "8px 15px",
                borderRadius: "20px",
                display: "inline-block"
              }}>
                Order ID: #{orderId}
              </p>
          )}
        </div>

        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "30px" }}>

          {/* Left Column - Order Details */}
          <div>
            {/* Buyer Information */}
            <section style={{
              backgroundColor: "white",
              padding: "25px",
              borderRadius: "12px",
              boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
              marginBottom: "25px"
            }}>
              <h3 style={{ color: "#333", marginBottom: "20px", borderBottom: "2px solid #f4a261", paddingBottom: "10px" }}>
                Buyer Information
              </h3>
              <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                <p><strong>Name:</strong> {user.firstName} {user.lastName}</p>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>Phone:</strong> {user.phone || "Not provided"}</p>
              </div>
            </section>

            {/* Order Summary */}
            <section style={{
              backgroundColor: "white",
              padding: "25px",
              borderRadius: "12px",
              boxShadow: "0 2px 10px rgba(0,0,0,0.1)"
            }}>
              <h3 style={{ color: "#333", marginBottom: "20px", borderBottom: "2px solid #f4a261", paddingBottom: "10px" }}>
                Order Summary
              </h3>
              <div style={{ display: "flex", flexDirection: "column", gap: "12px", marginBottom: "20px" }}>
                {cartItems.map(item => (
                    <div key={item.cartItemId} style={{
                      display: "flex",
                      justifyContent: "space-between",
                      alignItems: "center",
                      backgroundColor: "#f8f9fa",
                      padding: "15px",
                      borderRadius: "8px",
                      border: "1px solid #e9ecef"
                    }}>
                      <div>
                        <div style={{ fontWeight: "bold", color: "#333" }}>{item.product?.name}</div>
                        <div style={{ color: "#666", fontSize: "0.9rem" }}>Qty: {item.quantity}</div>
                      </div>
                      <span style={{ fontWeight: "bold", color: "orange" }}>
                    R{calculateItemTotal(item)}
                  </span>
                    </div>
                ))}
              </div>
              <div style={{
                textAlign: "right",
                paddingTop: "15px",
                borderTop: "2px solid #e9ecef",
                fontSize: "1.2rem",
                fontWeight: "bold",
                color: "#333"
              }}>
                Total: R{subtotal.toFixed(2)}

              </div>
            </section>
          </div>

          {/* Right Column - Payment Instructions */}
          <div>
            <section style={{
              backgroundColor: "white",
              padding: "25px",
              borderRadius: "12px",
              boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
              marginBottom: "25px"
            }}>
              <h3 style={{ color: "#333", marginBottom: "20px", borderBottom: "2px solid #f4a261", paddingBottom: "10px" }}>
                Payment Instructions
              </h3>

              <div style={{ backgroundColor: "#fff3cd", padding: "20px", borderRadius: "8px", marginBottom: "20px" }}>
                <h4 style={{ color: "orange", marginBottom: "15px" }}> Important Payment Details</h4>

                <div style={{ marginBottom: "15px" }}>
                  <strong style={{ color: "#333" }}>Bank:</strong> FNB (First National Bank)
                </div>

                <div style={{ marginBottom: "15px" }}>
                  <strong style={{ color: "#333" }}>Account Number:</strong>
                  <span style={{
                    backgroundColor: "#e9ecef",
                    padding: "5px 10px",
                    borderRadius: "4px",
                    marginLeft: "10px",
                    fontFamily: "monospace"
                  }}>
                  1234567890
                </span>
                </div>

                <div style={{ marginBottom: "20px" }}>
                  <strong style={{ color: "#333" }}>Account Name:</strong> AdamTech Store
                </div>

                <div style={{
                  backgroundColor: "#fff3cd",
                  padding: "15px",
                  borderRadius: "8px",
                  border: "1px solid orange"
                }}>
                  <strong> Proof of Payment:</strong>
                  <p style={{ margin: "10px 0 0 0", color: "#333" }}>
                    After payment, please email your proof of payment to:
                    <br />
                    <strong>adamtech@gmail.com</strong>
                  </p>
                </div>
              </div>

              <p style={{ fontStyle: "italic", color: "#666", textAlign: "center" }}>
                Your order will be processed once payment is verified by our admin team.
              </p>
            </section>

            {/* Action Buttons */}
            <section style={{
              backgroundColor: "white",
              padding: "25px",
              borderRadius: "12px",
              boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
              textAlign: "center"
            }}>
              <button
                  onClick={() => navigate("/")}
                  style={{
                    marginBottom: "15px",
                    padding: "15px 30px",
                    backgroundColor: "#f4a261",
                    color: "#fff",
                    border: "none",
                    borderRadius: "8px",
                    cursor: "pointer",
                    fontSize: "1rem",
                    fontWeight: "bold",
                    width: "100%",
                    transition: "background-color 0.3s"
                  }}
                  onMouseOver={(e) => e.target.style.backgroundColor = "#e76f51"}
                  onMouseOut={(e) => e.target.style.backgroundColor = "#f4a261"}
              >
                Continue Shopping
              </button>

              {/* Testing Button - For Development Only */}
              <div style={{ borderTop: "1px solid #e9ecef", paddingTop: "20px", marginTop: "20px" }}>
                <p style={{ color: "#666", fontSize: "0.9rem", marginBottom: "15px" }}>
                  <strong>Development Test:</strong> Mark order as completed
                </p>
                <button
                    onClick={handleMarkAsPaid}
                    disabled={isLoading}
                    style={{
                      padding: "12px 25px",
                      backgroundColor: isLoading ? "#6c757d" : "#2a9d8f",
                      color: "#fff",
                      border: "none",
                      borderRadius: "8px",
                      cursor: isLoading ? "not-allowed" : "pointer",
                      fontSize: "1rem",
                      width: "100%",
                      transition: "background-color 0.3s"
                    }}
                    onMouseOver={(e) => !isLoading && (e.target.style.backgroundColor = "#21867a")}
                    onMouseOut={(e) => !isLoading && (e.target.style.backgroundColor = "#2a9d8f")}
                >
                  {isLoading ? "🔄 Processing..." : "✅ Mark Order as COMPLETED (Test)"}
                </button>

                {statusMessage && (
                    <p style={{
                      marginTop: "15px",
                      color: statusMessage.includes("✅") ? "#2a9d8f" : "#e76f51",
                      fontWeight: "bold",
                      padding: "10px",
                      backgroundColor: statusMessage.includes("✅") ? "#e9f5f3" : "#ffeaea",
                      borderRadius: "8px"
                    }}>
                      {statusMessage}
                    </p>
                )}
              </div>
            </section>
          </div>
        </div>
      </div>
  );
};

export default Checkout;