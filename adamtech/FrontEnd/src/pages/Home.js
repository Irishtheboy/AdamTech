import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

// Images
import macbook2 from "../assets/images/macbook 2.png";
import case4 from "../assets/images/case4.png";
import periph9 from "../assets/images/periph9.png";
import laptop5 from "../assets/images/laptop5.png";
import Macbook from "../assets/images/Macbook.png";
import AsusLaptop from "../assets/images/AsusLaptop.png";
import DellLogo from "../assets/images/DellLogo.png";
import HPLogo from "../assets/images/HPLogo.png";
import LenovoLogo from "../assets/images/LenovoLogo.png";
import AppleLogo from "../assets/images/AppleLogo.png";
import SamsungLogo from "../assets/images/SamsungLogo.png";
import AsusLogo from "../assets/images/AsusLogo.png";

// Banner Images for Carousel
import CosairBanner from "../assets/images/CosairBanner.jpg";
import HPBanner from "../assets/images/HPBanner.jpg";
import HPBanner2 from "../assets/images/HPBanner2.jpg";
import MSIBanner from "../assets/images/MSIBanner.jpg";

// Laptop Deals Banner
import LaptopDealsBanner from "../assets/images/laptopdealsbanner.jpg";
import GamingKeyboardBanner from "../assets/images/gamingkeyboardbanner.jpg";
import GamingMouseBanner from "../assets/images/gamingmousebanner.jpg";

const Home = ({ addToCart, user }) => {
    const navigate = useNavigate();
    const [products, setProducts] = useState([]);

    const handleAddToCart = (product) => {
        addToCart(product);
        alert(`${product.name} was added to your cart!`);
    };

    const goToSignUpIfNotLoggedIn = () => {
        if (!user) {
            navigate("/signUp");
        }
    };

    const goToCategory = (categoryName) => {
        navigate(`/shop?category=${encodeURIComponent(categoryName.trim())}`);
    };

    // Fetch products (limit 6 for Laptop Deals)
    const fetchProducts = async () => {
        try {
            // ✅ GET THE TOKEN FROM LOCALSTORAGE
            const token = localStorage.getItem("token");
            console.log("Token for products request:", token); // Debug log

            const res = await axios.get(
                "http://localhost:8080/adamtech/products/getAll",
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                }
            );

            const productsArray = Array.isArray(res.data) ? res.data : res.data.content || [];
            const productsWithImages = productsArray.map((product) => ({
                ...product,
                imageUrl: `http://localhost:8080/adamtech/products/${product.productId}/image`,
            }));
            setProducts(productsWithImages.slice(0, 6));
        } catch (err) {
            console.error("❌ Error fetching products:", err);

            // Handle authentication errors
            if (err.response?.status === 401 || err.response?.status === 403) {
                console.error("Authentication error - token may be invalid or expired");
                // Optional: Redirect to login or show message
                // navigate("/login");
            }
        }
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    // Carousel State
    const banners = [{ img: CosairBanner }, { img: HPBanner }, { img: MSIBanner }];
    const [current, setCurrent] = useState(0);

    // Auto-slide every 6 seconds
    useEffect(() => {
        const timer = setInterval(() => {
            setCurrent((prev) => (prev + 1) % banners.length);
        }, 6000);
        return () => clearInterval(timer);
    }, [banners.length]);

    const prevSlide = () => {
        setCurrent((prev) => (prev - 1 + banners.length) % banners.length);
    };

    const nextSlide = () => {
        setCurrent((prev) => (prev + 1) % banners.length);
    };

    return (
        <div style={{ fontFamily: "Arial, sans-serif", margin: 0, padding: 0, backgroundColor: "#fff", color: "#333" }}>

            {/* Hero Section (Carousel) */}
            <section
                style={{
                    position: "relative",
                    textAlign: "center",
                    padding: "40px 20px",
                    backgroundColor: "white",
                    borderRadius: "10px",
                    margin: "20px",
                    overflow: "hidden",
                }}
            >
                <img
                    src={banners[current].img}
                    alt="banner"
                    style={{
                        width: "100%",
                        maxHeight: "400px",
                        objectFit: "cover",
                        borderRadius: "10px",
                        transition: "opacity 1s ease-in-out",
                    }}
                />

                {/* Left/Right Arrows */}
                <button
                    onClick={prevSlide}
                    style={{
                        position: "absolute",
                        top: "50%",
                        left: "20px",
                        transform: "translateY(-50%)",
                        background: "rgba(0,0,0,0.5)",
                        border: "none",
                        borderRadius: "50%",
                        color: "#fff",
                        fontSize: "1.5rem",
                        padding: "8px 12px",
                        cursor: "pointer",
                    }}
                >
                    ❮
                </button>
                <button
                    onClick={nextSlide}
                    style={{
                        position: "absolute",
                        top: "50%",
                        right: "20px",
                        transform: "translateY(-50%)",
                        background: "rgba(0,0,0,0.5)",
                        border: "none",
                        borderRadius: "50%",
                        color: "#fff",
                        fontSize: "1.5rem",
                        padding: "8px 12px",
                        cursor: "pointer",
                    }}
                >
                    ❯
                </button>

                {/* Dots Navigation */}
                <div
                    style={{
                        position: "absolute",
                        bottom: "15px",
                        left: "50%",
                        transform: "translateX(-50%)",
                        display: "flex",
                        gap: "8px",
                    }}
                >
                    {banners.map((_, idx) => (
                        <span
                            key={idx}
                            onClick={() => setCurrent(idx)}
                            style={{
                                width: "10px",
                                height: "10px",
                                borderRadius: "50%",
                                backgroundColor: idx === current ? "#f4a261" : "#ccc",
                                cursor: "pointer",
                                transition: "background-color 0.3s",
                            }}
                        />
                    ))}
                </div>
            </section>

            {/* Categories Section */}
            <section style={{ padding: "40px 20px", textAlign: "center" }}>
                <h2 style={{ fontSize: "2rem", marginBottom: "20px", color: "#333" }}>Shop by Category</h2>
                <div style={{ display: "flex", justifyContent: "center", gap: "30px", flexWrap: "wrap" }}>
                    {[{ name: "Laptops", img: Macbook },
                        { name: "Cases", img: case4 },
                        { name: "Gaming Laptops", img: laptop5 },
                        { name: "Peripherals", img: periph9 }].map((cat, idx) => (
                        <div
                            key={idx}
                            onClick={() => goToCategory(cat.name)}
                            style={{
                                width: "240px",
                                height: "240px",
                                backgroundColor: "#f8f8f8",
                                borderRadius: "50%",
                                boxShadow: "0 0 12px rgba(0,0,0,0.15)",
                                cursor: "pointer",
                                display: "flex",
                                flexDirection: "column",
                                alignItems: "center",
                                justifyContent: "center",
                                overflow: "hidden",
                                transition: "transform 0.3s",
                            }}
                            onMouseEnter={(e) => (e.currentTarget.style.transform = "scale(1.05)")}
                            onMouseLeave={(e) => (e.currentTarget.style.transform = "scale(1)")}
                        >
                            <img
                                src={cat.img}
                                alt={cat.name}
                                style={{
                                    width: "100%",
                                    height: "100%",
                                    objectFit: "cover",
                                }}
                            />
                        </div>
                    ))}
                </div>
                <div style={{ display: "flex", justifyContent: "center", gap: "30px", flexWrap: "wrap", marginTop: "15px" }}>
                    {[{ name: "Laptops" }, { name: "Cases" }, { name: "Gaming Laptops" }, { name: "Peripherals" }].map((cat, idx) => (
                        <h3 key={idx} style={{ width: "240px", textAlign: "center", color: "#333" }}>{cat.name}</h3>
                    ))}
                </div>
            </section>

            <section style={{ backgroundColor: "white", padding: "40px", margin: "20px", borderRadius: "10px" }}>
                <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "25px" }}>
                    <h1 style={{ fontSize: "2rem", fontWeight: "700", color: "orange" }}>Laptop Deals</h1>

                </div>

                <div style={{ display: "grid", gridTemplateColumns: "1fr 2fr", gap: "20px" }}>
                    {/* Banner */}
                    <div>
                        <img
                            src={LaptopDealsBanner}
                            alt="Laptop Deals Banner"
                            style={{ width: "100%", borderRadius: "16px", objectFit: "cover" }}
                        />
                    </div>

                    {/* Products */}
                    <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))", gap: "20px" }}>
                        {products.map((product) => (
                            <div
                                key={product.productId}
                                style={{
                                    backgroundColor: "white",
                                    borderRadius: "12px",
                                    padding: "15px",
                                    textAlign: "center",
                                    color: "#fff",
                                    cursor: "pointer",
                                    boxShadow: "0 4px 10px rgba(0,0,0,0.4)",
                                    transition: "transform 0.3s ease, box-shadow 0.3s ease",
                                }}
                                onClick={() => navigate(`/product/${product.productId}`)}
                                onMouseEnter={(e) => {
                                    e.currentTarget.style.transform = "translateY(-6px)";
                                    e.currentTarget.style.boxShadow = "0 8px 18px rgba(0,0,0,0.6)";
                                }}
                                onMouseLeave={(e) => {
                                    e.currentTarget.style.transform = "translateY(0)";
                                    e.currentTarget.style.boxShadow = "0 4px 10px rgba(0,0,0,0.4)";
                                }}
                            >
                                <img
                                    src={product.imageUrl}
                                    alt={product.name}
                                    style={{
                                        width: "100%",
                                        height: "160px",
                                        objectFit: "contain",
                                        borderRadius: "10px",
                                        marginBottom: "10px",
                                        backgroundColor: "#fff",
                                    }}
                                />
                                <h3 style={{ fontSize: "1rem", fontWeight: "600", marginBottom: "6px" }}>{product.name}</h3>
                                <p style={{ fontSize: "1.1rem", fontWeight: "700", color: "orange" }}>
                                    R{product.price?.amount?.toLocaleString() || "0.00"}
                                </p>
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            {/* Extra Banners Section */}
            <section
                style={{
                    display: "flex",
                    gap: "20px",
                    margin: "20px",
                    borderRadius: "10px",
                    flexWrap: "wrap", // allows stacking on small screens
                    justifyContent: "center", // center banners if not full width
                }}
            >
                <img
                    src={GamingKeyboardBanner}
                    alt="Gaming Keyboard Banner"
                    style={{
                        flex: "1 1 300px", // flexible but minimum width 300px
                        maxWidth: "48%", // ensures both banners fit side by side
                        borderRadius: "10px",
                        objectFit: "cover",
                    }}
                />
                <img
                    src={GamingMouseBanner}
                    alt="Gaming Mouse Banner"
                    style={{
                        flex: "1 1 300px",
                        maxWidth: "48%",
                        borderRadius: "10px",
                        objectFit: "cover",
                    }}
                />
            </section>




            {/* Popular Brands */}
            <section style={{ padding: "40px 20px", textAlign: "center", backgroundColor: "#fff" }}>
                <h2 style={{ fontSize: "2rem", marginBottom: "20px", color: "#333" }}>Popular Brands</h2>
                <div
                    style={{
                        display: "grid",
                        gridTemplateColumns: "repeat(auto-fit, minmax(100px, 1fr))", // slightly smaller
                        gap: "10px", // reduced gap
                        justifyItems: "center",
                    }}
                >
                    {[{ name: "", img: DellLogo },
                        { name: "", img: LenovoLogo },
                        { name: "", img: HPLogo },
                        { name: "", img: AsusLogo },
                        { name: "", img: SamsungLogo },
                        { name: "", img: AppleLogo }].map((product, idx) => (
                        <div
                            key={idx}
                            style={{
                                width: "100px",  // slightly smaller
                                height: "100px",
                                backgroundColor: "#f8f8f8",
                                borderRadius: "50%", // circular
                                display: "flex",
                                alignItems: "center",
                                justifyContent: "center",
                                boxShadow: "0 0 10px rgba(0,0,0,0.1)",
                                overflow: "hidden",
                                cursor: "pointer",
                                transition: "transform 0.3s",
                            }}
                            onMouseEnter={(e) => (e.currentTarget.style.transform = "scale(1.05)")}
                            onMouseLeave={(e) => (e.currentTarget.style.transform = "scale(1)")}
                        >
                            <img
                                src={product.img}
                                alt={product.name}
                                style={{ width: "80%", height: "80%", objectFit: "contain", borderRadius: "50%" }}
                            />
                        </div>
                    ))}
                </div>
            </section>


        </div>
    );
};

export default Home;