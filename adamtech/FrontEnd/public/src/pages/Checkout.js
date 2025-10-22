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
                { status: "COMPLETED" },
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                }
            );

            if (response.status === 200) {
                setStatusMessage("✅ Order marked as COMPLETED successfully!");

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

    // Calculate shipping and tax (you can adjust these)
    const shipping = 50.00;
    const tax = (subtotal * 0.15).toFixed(2); // 15% tax
    const total = (parseFloat(subtotal) + parseFloat(shipping) + parseFloat(tax)).toFixed(2);

    return (
        <div style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            minHeight: "100vh",
            backgroundColor: "#f8f9fa",
            padding: "20px",
            fontFamily: "'Segoe UI', sans-serif"
        }}>

            <div className="container" style={{
                display: "grid",
                gridTemplateColumns: "auto",
                gap: "0px",
                maxWidth: "400px",
                width: "100%"
            }}>

                {/* Checkout Card */}
                <div className="card cart" style={{
                    width: "100%",
                    background: "rgb(255, 250, 235)",
                    boxShadow: "0px 187px 75px rgba(0, 0, 0, 0.01), 0px 105px 63px rgba(0, 0, 0, 0.05), 0px 47px 47px rgba(0, 0, 0, 0.09), 0px 12px 26px rgba(0, 0, 0, 0.1), 0px 0px 0px rgba(0, 0, 0, 0.1)",
                    borderRadius: "19px 19px 0px 0px"
                }}>
                    <label className="title" style={{
                        width: "100%",
                        height: "40px",
                        position: "relative",
                        display: "flex",
                        alignItems: "center",
                        paddingLeft: "20px",
                        borderBottom: "1px solid rgba(16, 86, 82, .75)",
                        fontWeight: "700",
                        fontSize: "11px",
                        color: "#000000"
                    }}>
                        CHECKOUT
                    </label>

                    <div className="steps" style={{
                        display: "flex",
                        flexDirection: "column",
                        padding: "20px"
                    }}>
                        <div className="step" style={{
                            display: "grid",
                            gap: "10px"
                        }}>

                            {/* Shipping Information */}
                            <div>
                <span style={{
                    fontSize: "13px",
                    fontWeight: "600",
                    color: "#000000",
                    marginBottom: "8px",
                    display: "block"
                }}>
                  SHIPPING
                </span>
                                <p style={{
                                    fontSize: "11px",
                                    fontWeight: "600",
                                    color: "#000000",
                                    margin: "4px 0"
                                }}>
                                    {user.firstName} {user.lastName}
                                </p>
                                <p style={{
                                    fontSize: "11px",
                                    fontWeight: "600",
                                    color: "#000000",
                                    margin: "4px 0"
                                }}>
                                    {user.email}
                                </p>
                                <p style={{
                                    fontSize: "11px",
                                    fontWeight: "600",
                                    color: "#000000",
                                    margin: "4px 0"
                                }}>
                                    {user.phoneNumber || "Phone not provided"}
                                </p>
                            </div>

                            <hr style={{
                                height: "1px",
                                backgroundColor: "rgba(16, 86, 82, .75)",
                                border: "none"
                            }} />

                            {/* Payment Method */}
                            <div>
                <span style={{
                    fontSize: "13px",
                    fontWeight: "600",
                    color: "#000000",
                    marginBottom: "8px",
                    display: "block"
                }}>
                  PAYMENT METHOD
                </span>
                                <p style={{
                                    fontSize: "11px",
                                    fontWeight: "600",
                                    color: "#000000",
                                    margin: "4px 0"
                                }}>
                                    Bank Transfer
                                </p>
                                <p style={{
                                    fontSize: "11px",
                                    fontWeight: "600",
                                    color: "#000000",
                                    margin: "4px 0"
                                }}>
                                    FNB: 1234567890
                                </p>
                            </div>

                            <hr style={{
                                height: "1px",
                                backgroundColor: "rgba(16, 86, 82, .75)",
                                border: "none"
                            }} />

                            {/* Order Items */}
                            <div>
                <span style={{
                    fontSize: "13px",
                    fontWeight: "600",
                    color: "#000000",
                    marginBottom: "8px",
                    display: "block"
                }}>
                  ORDER ITEMS
                </span>
                                {cartItems.map(item => (
                                    <p key={item.cartItemId} style={{
                                        fontSize: "11px",
                                        fontWeight: "600",
                                        color: "#000000",
                                        margin: "4px 0",
                                        display: "flex",
                                        justifyContent: "space-between"
                                    }}>
                                        <span>{item.product?.name} (x{item.quantity})</span>
                                        <span>R{((item.product?.price?.amount || 0) * item.quantity).toFixed(2)}</span>
                                    </p>
                                ))}
                            </div>

                            <hr style={{
                                height: "1px",
                                backgroundColor: "rgba(16, 86, 82, .75)",
                                border: "none"
                            }} />

                            {/* Payment Details */}
                            <div className="payments">
                <span style={{
                    fontSize: "13px",
                    fontWeight: "600",
                    color: "#000000",
                    marginBottom: "8px",
                    display: "block"
                }}>
                  PAYMENT DETAILS
                </span>
                                <div className="details" style={{
                                    display: "grid",
                                    gridTemplateColumns: "10fr 1fr",
                                    padding: "0px",
                                    gap: "5px"
                                }}>
                                    <span>Subtotal:</span>
                                    <span>R{subtotal.toFixed(2)}</span>
                                    <span>Shipping:</span>
                                    <span>R{shipping.toFixed(2)}</span>
                                    <span>Tax:</span>
                                    <span>R{tax}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                {/* Total and Checkout Button */}
                <div className="card checkout" style={{
                    width: "100%",
                    background: "rgb(255, 250, 235)",
                    boxShadow: "0px 187px 75px rgba(0, 0, 0, 0.01), 0px 105px 63px rgba(0, 0, 0, 0.05), 0px 47px 47px rgba(0, 0, 0, 0.09), 0px 12px 26px rgba(0, 0, 0, 0.1), 0px 0px 0px rgba(0, 0, 0, 0.1)"
                }}>
                    <div className="footer" style={{
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "space-between",
                        padding: "10px 10px 10px 20px",
                        background: "rgba(16, 86, 82, .5)",
                        borderRadius: "0px 0px 19px 19px"
                    }}>
                        <label className="price" style={{
                            position: "relative",
                            fontSize: "22px",
                            color: "#2B2B2F",
                            fontWeight: "900"
                        }}>
                            R{total}
                        </label>
                        <button
                            onClick={handleMarkAsPaid}
                            disabled={isLoading}
                            className="checkout-btn"
                            style={{
                                display: "flex",
                                flexDirection: "row",
                                justifyContent: "center",
                                alignItems: "center",
                                width: "150px",
                                height: "36px",
                                background: isLoading ? "rgba(16, 86, 82, .3)" : "rgba(16, 86, 82, .55)",
                                boxShadow: "0px 0.5px 0.5px rgba(16, 86, 82, .75), 0px 1px 0.5px rgba(16, 86, 82, .75)",
                                borderRadius: "7px",
                                border: `1px solid rgb(16, 86, 82)`,
                                color: "#000000",
                                fontSize: "13px",
                                fontWeight: "600",
                                transition: "all 0.3s cubic-bezier(0.15, 0.83, 0.66, 1)",
                                cursor: isLoading ? "not-allowed" : "pointer"
                            }}
                        >
                            {isLoading ? "Processing..." : "Confirm Order"}
                        </button>
                    </div>
                </div>

                {/* Status Message */}
                {statusMessage && (
                    <div style={{
                        marginTop: "20px",
                        padding: "15px",
                        backgroundColor: statusMessage.includes("✅") ? "#e9f5f3" : "#ffeaea",
                        border: `1px solid ${statusMessage.includes("✅") ? "#2a9d8f" : "#e76f51"}`,
                        borderRadius: "8px",
                        textAlign: "center"
                    }}>
                        <p style={{
                            color: statusMessage.includes("✅") ? "#2a9d8f" : "#e76f51",
                            fontWeight: "bold",
                            margin: 0,
                            fontSize: "14px"
                        }}>
                            {statusMessage}
                        </p>
                    </div>
                )}

                {/* Continue Shopping Button */}
                <button
                    onClick={() => navigate("/")}
                    style={{
                        marginTop: "20px",
                        padding: "12px 25px",
                        backgroundColor: "transparent",
                        color: "rgba(16, 86, 82, .75)",
                        border: "1px solid rgba(16, 86, 82, .75)",
                        borderRadius: "7px",
                        cursor: "pointer",
                        fontSize: "13px",
                        fontWeight: "600",
                        width: "100%",
                        transition: "all 0.3s ease"
                    }}
                    onMouseOver={(e) => {
                        e.target.style.backgroundColor = "rgba(16, 86, 82, .1)";
                    }}
                    onMouseOut={(e) => {
                        e.target.style.backgroundColor = "transparent";
                    }}
                >
                    Continue Shopping
                </button>

            </div>
        </div>
    );
};

export default Checkout;