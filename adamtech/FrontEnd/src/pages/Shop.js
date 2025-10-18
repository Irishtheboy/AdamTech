import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import Loader from "../components/Loader";

const Shop = ({ addToCart }) => {
  const [products, setProducts] = useState([]);
  const [notification, setNotification] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState({
    category: "All",
    brand: "All",
    price: "All",
    sort: "Default",
  });

  const navigate = useNavigate();
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const scrollToCategory = params.get("category");

  const preloadImage = (url) =>
      new Promise((resolve) => {
        const img = new Image();
        img.src = url;
        img.onload = () => resolve(url);
        img.onerror = () => resolve("/placeholder.png");
      });

  const fetchProducts = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await axios.get("http://localhost:8080/adamtech/products/getAll");
      const productsArray = Array.isArray(res.data) ? res.data : res.data.content || [];
      const productsWithImages = await Promise.all(
          productsArray.map(async (product) => {
            const imageUrl = `http://localhost:8080/adamtech/products/${product.productId}/image`;
            await preloadImage(imageUrl);
            return { ...product, imageUrl };
          })
      );
      setProducts(productsWithImages);
    } catch (err) {
      console.error("❌ Error fetching products:", err.response || err);
      setError("Could not load products.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  useEffect(() => {
    if (scrollToCategory && products.length > 0) {
      const targetId = scrollToCategory.replace(/\s+/g, "").toLowerCase();
      const element = document.getElementById(targetId);
      if (element) element.scrollIntoView({ behavior: "smooth" });
    }
  }, [scrollToCategory, products]);

  const handleAddToCart = (product) => {
    addToCart(product);
    setNotification(`${product.name} was added to your cart`);
    setTimeout(() => setNotification(""), 3000);
  };

  const categoriesList = ["All", ...new Set(products.map((p) => p.categoryId || "Uncategorized"))];
  const brandsList = ["All", ...new Set(products.map((p) => p.brand || "Generic"))];

  let filteredProducts = products.filter(
      (p) =>
          (filters.category === "All" || p.categoryId === filters.category) &&
          (filters.brand === "All" || p.brand === filters.brand) &&
          (filters.price === "All" ||
              (filters.price === "<1000" && p.price?.amount < 1000) ||
              (filters.price === "1000-5000" && p.price?.amount >= 1000 && p.price?.amount <= 5000) ||
              (filters.price === ">5000" && p.price?.amount > 5000))
  );

  if (filters.sort === "PriceLowHigh") filteredProducts.sort((a, b) => a.price.amount - b.price.amount);
  if (filters.sort === "PriceHighLow") filteredProducts.sort((a, b) => b.price.amount - a.price.amount);

  const groupedProducts = filteredProducts.reduce((groups, product) => {
    const category = product.categoryId || "Uncategorized";
    if (!groups[category]) groups[category] = [];
    groups[category].push(product);
    return groups;
  }, {});

  // Reset all filters
  const clearFilters = () => {
    setFilters({ category: "All", brand: "All", price: "All", sort: "Default" });
  };

  return (
      <div style={{ backgroundColor: "#f9f9f9", minHeight: "100vh", padding: "20px 40px", fontFamily: "'Segoe UI', sans-serif" }}>
        <h1 style={{ fontSize: "2.5rem", marginBottom: "30px", textAlign: "center", color: "#f4a261" }}>
          Find the Best Tech Deals Here!
        </h1>

        {notification && (
            <div style={{
              position: "fixed",
              top: "20px",
              right: "20px",
              backgroundColor: "#f4a261",
              color: "#fff",
              padding: "12px 20px",
              borderRadius: "8px",
              boxShadow: "0 4px 12px rgba(0,0,0,0.2)",
              zIndex: 1000,
              fontWeight: "bold",
            }}>
              {notification}
            </div>
        )}

        {loading && <Loader />}
        {error && <p style={{ color: "red", textAlign: "center" }}>{error}</p>}

        <div style={{ display: "flex", gap: "30px", alignItems: "flex-start" }}>

          {/* Left Column: Filter Panel */}
          <div style={{
            flex: "0 0 220px",
            backgroundColor: "#fff",
            padding: "20px",
            borderRadius: "8px",
            boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
            display: "flex",
            flexDirection: "column",
            gap: "15px",
            position: "sticky",
            top: "80px",
            alignSelf: "flex-start",
            height: "fit-content"
          }}>
            <h3 style={{ margin: 0, fontSize: "1.2rem", color: "#333" }}>Filters</h3>

            {/* Clear Filters Button */}
            <button
                onClick={clearFilters}
                style={{
                  backgroundColor: "#f4a261",
                  color: "#fff",
                  padding: "8px 12px",
                  border: "none",
                  borderRadius: "6px",
                  cursor: "pointer",
                  fontWeight: "600",
                  fontSize: "0.9rem",
                  marginTop: "10px"
                }}
                onMouseEnter={(e) => e.target.style.backgroundColor = "#d65b3f"}
                onMouseLeave={(e) => e.target.style.backgroundColor = "#e76f51"}
            >
              Clear Filters
            </button>

            {/* Category Dropdown */}
            <div>
              <label style={{ fontSize: "0.9rem", fontWeight: "600", color: "#666" }}>Category</label>
              <select
                  value={filters.category}
                  onChange={(e) => setFilters({ ...filters, category: e.target.value })}
                  style={{ width: "100%", padding: "8px", marginTop: "5px", borderRadius: "5px", border: "1px solid #ccc" }}
              >
                {categoriesList.map((cat, idx) => <option key={idx} value={cat}>{cat}</option>)}
              </select>
            </div>

            {/* Brand Dropdown */}
            <div>
              <label style={{ fontSize: "0.9rem", fontWeight: "600", color: "#666" }}>Brand</label>
              <select
                  value={filters.brand}
                  onChange={(e) => setFilters({ ...filters, brand: e.target.value })}
                  style={{ width: "100%", padding: "8px", marginTop: "5px", borderRadius: "5px", border: "1px solid #ccc" }}
              >
                {brandsList.map((brand, idx) => <option key={idx} value={brand}>{brand}</option>)}
              </select>
            </div>

            {/* Price Dropdown */}
            <div>
              <label style={{ fontSize: "0.9rem", fontWeight: "600", color: "#666" }}>Price</label>
              <select
                  value={filters.price}
                  onChange={(e) => setFilters({ ...filters, price: e.target.value })}
                  style={{ width: "100%", padding: "8px", marginTop: "5px", borderRadius: "5px", border: "1px solid #ccc" }}
              >
                <option value="All">All Prices</option>
                <option value="<1000">Under R1,000</option>
                <option value="1000-5000">R1,000 - R5,000</option>
                <option value=">5000">Above R5,000</option>
              </select>
            </div>

            {/* Sort Dropdown */}
            <div>
              <label style={{ fontSize: "0.9rem", fontWeight: "600", color: "#666" }}>Sort By</label>
              <select
                  value={filters.sort}
                  onChange={(e) => setFilters({ ...filters, sort: e.target.value })}
                  style={{ width: "100%", padding: "8px", marginTop: "5px", borderRadius: "5px", border: "1px solid #ccc" }}
              >
                <option value="Default">Default</option>
                <option value="PriceLowHigh">Price: Low to High</option>
                <option value="PriceHighLow">Price: High to Low</option>
              </select>
            </div>
          </div>

          {/* Right Column: Products */}
          <div style={{ flex: "1" }}>
            {Object.keys(groupedProducts).length === 0 && !loading ? (
                <p style={{ textAlign: "center" }}>No products match your filters.</p>
            ) : (
                Object.keys(groupedProducts).map((category) => (
                    <div key={category} style={{ marginBottom: "40px" }}>
                      <h2
                          id={category.replace(/\s+/g, "").toLowerCase()}
                          style={{
                            fontSize: "1.8rem",
                            marginBottom: "15px",
                            textAlign: "left",
                            color: "#333",
                            borderBottom: "3px solid #f4a261",
                            display: "inline-block",
                            paddingBottom: "5px"
                          }}
                      >
                        {category}
                      </h2>
                      <div style={{
                        display: "grid",
                        gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
                        gap: "25px",
                      }}>
                        {groupedProducts[category].map((product) => (
                            <div
                                key={product.productId}
                                style={{
                                  backgroundColor: "#fff",
                                  padding: "20px",
                                  borderRadius: "16px",
                                  textAlign: "center",
                                  boxShadow: "0 6px 15px rgba(0,0,0,0.1)",
                                  transition: "transform 0.3s ease, box-shadow 0.3s ease",
                                  cursor: "pointer",
                                  display: "flex",
                                  flexDirection: "column",
                                  justifyContent: "space-between",
                                }}
                                onClick={() => navigate(`/product/${product.productId}`)}
                                onMouseEnter={(e) => {
                                  e.currentTarget.style.transform = "translateY(-8px)";
                                  e.currentTarget.style.boxShadow = "0 12px 25px rgba(0,0,0,0.15)";
                                }}
                                onMouseLeave={(e) => {
                                  e.currentTarget.style.transform = "translateY(0)";
                                  e.currentTarget.style.boxShadow = "0 6px 15px rgba(0,0,0,0.1)";
                                }}
                            >
                              <div>
                                <img
                                    src={product.imageUrl}
                                    alt={product.name}
                                    style={{ width: "100%", height: "180px", objectFit: "cover", borderRadius: "12px", marginBottom: "15px" }}
                                />
                                <h3 style={{ fontSize: "1.2rem", color: "#333", marginBottom: "8px", fontWeight: "600" }}>{product.name}</h3>
                                <p style={{ fontSize: "1rem", fontWeight: "bold", color: "#f4a261", marginBottom: "12px" }}>
                                  R{product.price?.amount?.toLocaleString() || "0.00"}
                                </p>
                              </div>
                              {/* <button
                        onClick={(e) => {
                          e.stopPropagation();
                          handleAddToCart(product);
                        }}
                        style={{
                          backgroundColor: "#f4a261",
                          color: "#fff",
                          border: "none",
                          padding: "10px 16px",
                          borderRadius: "8px",
                          cursor: "pointer",
                          fontWeight: "600",
                          fontSize: "0.9rem",
                          transition: "background-color 0.2s ease",
                        }}
                        onMouseEnter={(e) => e.target.style.backgroundColor = "#e6913d"}
                        onMouseLeave={(e) => e.target.style.backgroundColor = "#f4a261"}
                      >
                        Add to Cart
                      </button> */}
                            </div>
                        ))}
                      </div>
                    </div>
                ))
            )}
          </div>

        </div>
      </div>
  );
};

export default Shop;






