import React, { useState, useEffect } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { getProductById, getProductImage } from "../services/ProductService";
import Loader from "../components/Loader";

const ProductDetails = ({ addToCart, user }) => {
    const location = useLocation();
    const navigate = useNavigate();
    const { id } = useParams();
    const [product, setProduct] = useState(location.state?.product || null);
    const [loading, setLoading] = useState(!product);
    const [error, setError] = useState(null);
    const [quantity, setQuantity] = useState(1);
    const [isWishlisted, setIsWishlisted] = useState(false);
    const [activeTab, setActiveTab] = useState("description"); // ✅ Added state for tabs

    useEffect(() => {
        if (product) return;

        const fetchProduct = async () => {
            try {
                setLoading(true);
                const data = await getProductById(id);
                setProduct(data);
            } catch {
                setError("Product not found.");
            } finally {
                setLoading(false);
            }
        };

        fetchProduct();
    }, [id, product]);

    const handleAddToCart = () => {
        if (!user) return alert("Please log in first.");
        addToCart({ ...product, quantity });
        alert(`${product.name} added to cart`);
    };

    const handleWishlistToggle = () => {
        if (!user) return alert("Please log in first.");
        setIsWishlisted((prev) => !prev);
        alert(
            !isWishlisted
                ? `${product.name} added to wishlist ❤️`
                : `${product.name} removed from wishlist 💔`
        );
    };

    if (loading) return <Loader />;

    if (error || !product)
        return (
            <div style={{ textAlign: "center", marginTop: "50px" }}>
                <p>{error || "Product not found."}</p>
                <button
                    onClick={() => navigate("/shop")}
                    style={{
                        backgroundColor: "#f4a261",
                        color: "#fff",
                        padding: "10px 20px",
                        border: "none",
                        borderRadius: "8px",
                        cursor: "pointer",
                    }}
                >
                    Go Back to Shop
                </button>
            </div>
        );

    return (
        <div
            style={{
                padding: "40px",
                fontFamily: "'Segoe UI', sans-serif",
                maxWidth: "1200px",
                margin: "0 auto",
            }}
        >
            <button
                onClick={() => navigate("/shop")}
                style={{
                    backgroundColor: "#f4a261",
                    color: "#fff",
                    padding: "8px 15px",
                    border: "none",
                    borderRadius: "8px",
                    cursor: "pointer",
                    marginBottom: "20px",
                }}
            >
                ← Back to Shop
            </button>

            <div style={{ display: "flex", gap: "50px", flexWrap: "wrap", alignItems: "flex-start" }}>
                {/* Product Image */}
                <div style={{ flex: "1 1 300px", textAlign: "center" }}>
                    <img
                        src={getProductImage(product.productId)}
                        alt={product.name}
                        style={{
                            width: "100%",
                            maxWidth: "400px",
                            objectFit: "contain",
                            borderRadius: "12px",
                        }}
                    />
                </div>

                {/* Product Info */}
                <div style={{ flex: "1 1 300px", minWidth: "300px" }}>
                    <div
                        style={{
                            backgroundColor: "#f5f5f5",
                            padding: "20px",
                            borderRadius: "8px",
                            marginBottom: "20px",
                        }}
                    >
                        {/* Product Name */}
                        <h1 style={{ fontSize: "2rem", marginBottom: "15px", color: "#333" }}>
                            {product.name}
                        </h1>

                        {/* Product Price */}
                        <p
                            style={{
                                fontSize: "1.5rem",
                                fontWeight: "bold",
                                color: "#f4a261",
                                marginBottom: "15px",
                            }}
                        >
                            R{product.price?.amount?.toLocaleString() || 0}
                        </p>

                        {/* Quantity Selector */}
                        <div style={{ marginBottom: "20px" }}>
                            <p style={{ color: "#555", fontSize: "1rem", marginBottom: "8px" }}>
                                Quantity:
                            </p>
                            <div
                                style={{
                                    display: "flex",
                                    alignItems: "center",
                                    gap: "10px",
                                    marginTop: "5px",
                                }}
                            >
                                <button
                                    onClick={() => setQuantity((q) => Math.max(1, q - 1))}
                                    style={{
                                        backgroundColor: "#fff",
                                        color: "#333",
                                        padding: "5px 10px",
                                        border: "1px solid #ccc",
                                        borderRadius: "4px",
                                        cursor: "pointer",
                                    }}
                                >
                                    -
                                </button>
                                <span
                                    style={{
                                        padding: "5px 10px",
                                        border: "1px solid #ccc",
                                        borderRadius: "4px",
                                    }}
                                >
                  {quantity}
                </span>
                                <button
                                    onClick={() => setQuantity((q) => q + 1)}
                                    style={{
                                        backgroundColor: "#fff",
                                        color: "#333",
                                        padding: "5px 10px",
                                        border: "1px solid #ccc",
                                        borderRadius: "4px",
                                        cursor: "pointer",
                                    }}
                                >
                                    +
                                </button>
                            </div>
                        </div>

                        {/* Add to Cart + Wishlist */}
                        <div style={{ display: "flex", gap: "10px" }}>
                            <button
                                onClick={handleAddToCart}
                                style={{
                                    backgroundColor: "#f4a261",
                                    color: "#fff",
                                    padding: "12px 25px",
                                    border: "none",
                                    borderRadius: "8px",
                                    cursor: "pointer",
                                    fontWeight: "bold",
                                    flex: "1",
                                }}
                                onMouseOver={(e) =>
                                    (e.currentTarget.style.backgroundColor = "#e39352")
                                }
                                onMouseOut={(e) =>
                                    (e.currentTarget.style.backgroundColor = "#f4a261")
                                }
                            >
                                Add to Cart
                            </button>

                            {/* Wishlist Button */}
                            <button
                                onClick={handleWishlistToggle}
                                style={{
                                    backgroundColor: isWishlisted ? "orange" : "#fff",
                                    color: isWishlisted ? "#fff" : "orange",
                                    border: "1px solid orange",
                                    borderRadius: "8px",
                                    fontSize: "1.5rem",
                                    cursor: "pointer",
                                    padding: "0 15px",
                                }}
                            >
                                ♡
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            {/* ✅ Description & Delivery Tabs with Animated Underline */}
            <div style={{ marginTop: "30px" }}>
                {/* Tab Header */}
                <div
                    style={{
                        display: "flex",
                        position: "relative",
                        borderBottom: "2px solid #ddd",
                        marginBottom: "15px",
                    }}
                >
                    {["description", "delivery"].map((tab) => (
                        <button
                            key={tab}
                            onClick={() => setActiveTab(tab)}
                            style={{
                                flex: 1,
                                padding: "12px",
                                border: "none",
                                background: "transparent",
                                fontWeight: "600",
                                fontSize: "1rem",
                                color: activeTab === tab ? "#f4a261" : "#333",
                                cursor: "pointer",
                                transition: "color 0.3s ease",
                            }}
                        >
                            {tab === "description" ? "Description" : "Delivery & Returns"}
                        </button>
                    ))}

                    {/* Animated Underline */}
                    <div
                        style={{
                            position: "absolute",
                            bottom: "0",
                            left: activeTab === "description" ? "0%" : "50%",
                            width: "50%",
                            height: "3px",
                            backgroundColor: "#f4a261",
                            borderRadius: "4px 4px 0 0",
                            transition: "left 0.3s ease",
                        }}
                    />
                </div>

                {/* Tab Content */}
                <div
                    style={{
                        backgroundColor: "#f5f5f5",
                        padding: "20px",
                        borderRadius: "8px",
                    }}
                >
                    {activeTab === "description" ? (
                        <>
                            <h3 style={{ color: "#333", marginBottom: "15px", fontSize: "1.2rem" }}>
                                Description
                            </h3>
                            <p style={{ color: "#555", fontSize: "0.9rem" }}>
                                {product.description || "No description available for this product."}
                            </p>
                        </>
                    ) : (
                        <>
                            <h3 style={{ color: "#333", marginBottom: "15px", fontSize: "1.2rem" }}>
                                Delivery & Returns
                            </h3>
                            <p style={{ color: "#555", fontSize: "0.9rem" }}>
                                Order today & get it delivered fast. Free returns within 30 days.
                            </p>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ProductDetails;




