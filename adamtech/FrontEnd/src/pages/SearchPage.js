import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import Loader from "../components/Loader";

const SearchPage = ({ addToCart }) => {
    const [query, setQuery] = useState("");
    const [allProducts, setAllProducts] = useState([]);
    const [results, setResults] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const navigate = useNavigate();
    const location = useLocation();

    // If you want the query to be read from ?q= param when opening /search?q=...:
    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const q = params.get("q") || "";
        setQuery(q);
        if (q) performSearch(q);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [location.search]);

    const preloadImage = (url) =>
        new Promise((resolve) => {
            const img = new Image();
            img.src = url;
            img.onload = () => resolve(url);
            img.onerror = () => resolve("/placeholder.png");
        });

    const fetchAllProducts = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await axios.get("http://localhost:8080/adamtech/products/getAll");
            const productsArray = Array.isArray(res.data) ? res.data : res.data.content || [];
            const productsWithImages = await Promise.all(
                productsArray.map(async (product) => {
                    const imageUrl = `http://localhost:8080/adamtech/products/${product.productId}/image`;
                    await preloadImage(imageUrl).catch(() => "/placeholder.png");
                    return { ...product, imageUrl };
                })
            );
            setAllProducts(productsWithImages);
            return productsWithImages;
        } catch (err) {
            console.error("Error fetching products for search:", err);
            setError("Could not load products.");
            setAllProducts([]);
            return [];
        } finally {
            setLoading(false);
        }
    };

    // Run once on mount so we have products to filter
    useEffect(() => {
        fetchAllProducts();
    }, []);

    const performSearch = async (q) => {
        setError(null);
        setLoading(true);
        try {
            const products = allProducts.length ? allProducts : await fetchAllProducts();
            const normalized = (s = "") => s.toString().toLowerCase();
            const term = normalized(q).trim();

            if (!term) {
                setResults([]);
                setLoading(false);
                return;
            }

            const matched = products.filter((p) => {
                return (
                    normalized(p.name).includes(term) ||
                    normalized(p.sku).includes(term) ||
                    normalized(p.categoryId).includes(term)
                );
            });

            setResults(matched);
        } catch (err) {
            console.error("Could not perform search:", err);
            setError("Could not load search results.");
        } finally {
            setLoading(false);
        }
    };

    const onSubmit = (e) => {
        e.preventDefault();
        // push query to url for share/link
        navigate(`/search?q=${encodeURIComponent(query)}`, { replace: true });
        performSearch(query);
    };

    const handleAddToCart = (product, e) => {
        if (e) e.stopPropagation();
        if (typeof addToCart === "function") addToCart(product);
    };

    return (
        <div style={{ padding: "20px 40px", fontFamily: "'Segoe UI', sans-serif", minHeight: "80vh" }}>
            <h1 style={{ textAlign: "center", color: "#f4a261", marginBottom: "20px" }}>Search Products</h1>

            <form onSubmit={onSubmit} style={{ display: "flex", justifyContent: "center", gap: "10px", marginBottom: "20px" }}>
                <input
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="Search by product name, SKU or category..."
                    style={{
                        width: "60%",
                        padding: "10px 12px",
                        borderRadius: "8px",
                        border: "1px solid #ccc",
                        fontSize: "1rem",
                    }}
                />
                <button
                    type="submit"
                    style={{
                        backgroundColor: "#f4a261",
                        color: "#fff",
                        padding: "10px 18px",
                        border: "none",
                        borderRadius: "8px",
                        fontWeight: "bold",
                        cursor: "pointer",
                    }}
                    onMouseOver={(e) => (e.currentTarget.style.backgroundColor = "#e39352")}
                    onMouseOut={(e) => (e.currentTarget.style.backgroundColor = "#f4a261")}
                >
                    Search
                </button>
            </form>

            {loading && <Loader />}

            {error && (
                <p style={{ color: "red", textAlign: "center", marginTop: "10px" }}>{error}</p>
            )}

            {!loading && !error && (
                <>
                    <div style={{ marginBottom: "14px", textAlign: "center", color: "#666" }}>
                        {results.length === 0 ? (
                            query ? (
                                <span>No products found for “{query}”.</span>
                            ) : (
                                <span>Enter a search term to find products.</span>
                            )
                        ) : (
                            <span>Showing {results.length} result{results.length > 1 ? "s" : ""} for “{query}”</span>
                        )}
                    </div>

                    <div style={{
                        display: "grid",
                        gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
                        gap: "25px"
                    }}>
                        {results.map((product) => (
                            <div
                                key={product.productId}
                                style={{
                                    backgroundColor: "#fff",
                                    padding: "20px",
                                    borderRadius: "12px",
                                    textAlign: "center",
                                    boxShadow: "0 6px 15px rgba(0,0,0,0.08)",
                                    transition: "transform 0.2s, box-shadow 0.2s",
                                    cursor: "pointer",
                                    display: "flex",
                                    flexDirection: "column",
                                    justifyContent: "space-between",
                                }}
                                onClick={() => navigate(`/product/${product.productId}`)}
                                onMouseEnter={(e) => {
                                    e.currentTarget.style.transform = "translateY(-6px)";
                                    e.currentTarget.style.boxShadow = "0 12px 25px rgba(0,0,0,0.12)";
                                }}
                                onMouseLeave={(e) => {
                                    e.currentTarget.style.transform = "translateY(0)";
                                    e.currentTarget.style.boxShadow = "0 6px 15px rgba(0,0,0,0.08)";
                                }}
                            >
                                <div>
                                    <img
                                        src={product.imageUrl}
                                        alt={product.name}
                                        style={{ width: "100%", height: "180px", objectFit: "cover", borderRadius: "8px", marginBottom: "12px" }}
                                    />
                                    <h3 style={{ fontSize: "1.1rem", color: "#333", marginBottom: "8px" }}>{product.name}</h3>
                                    <p style={{ fontSize: "1rem", fontWeight: "bold", color: "#f4a261", marginBottom: "12px" }}>
                                        R{product.price?.amount?.toLocaleString?.() ?? product.price?.amount ?? "0.00"}
                                    </p>
                                </div>

                                <div style={{ display: "flex", gap: "8px", marginTop: "8px" }}>
                                    <button
                                        onClick={(e) => handleAddToCart(product, e)}
                                        style={{
                                            flex: 1,
                                            backgroundColor: "#f4a261",
                                            color: "#fff",
                                            padding: "10px 12px",
                                            border: "none",
                                            borderRadius: "8px",
                                            cursor: "pointer",
                                            fontWeight: "600"
                                        }}
                                        onMouseOver={(e) => (e.currentTarget.style.backgroundColor = "#e39352")}
                                        onMouseOut={(e) => (e.currentTarget.style.backgroundColor = "#f4a261")}
                                    >
                                        Add to Cart
                                    </button>

                                    <button
                                        onClick={(e) => { e.stopPropagation(); navigate(`/product/${product.productId}`); }}
                                        style={{
                                            background: "transparent",
                                            border: "1px solid #f4a261",
                                            color: "#f4a261",
                                            padding: "10px 12px",
                                            borderRadius: "8px",
                                            cursor: "pointer",
                                            fontWeight: "600",
                                            minWidth: "80px"
                                        }}
                                    >
                                        View
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                </>
            )}
        </div>
    );
};

export default SearchPage;
