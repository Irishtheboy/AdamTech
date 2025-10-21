import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AdminProducts = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const [editingProduct, setEditingProduct] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [imagePreview, setImagePreview] = useState(null);

    // Form state
    const [formData, setFormData] = useState({
        name: '',
        description: '',
        sku: '',
        priceAmount: '',
        priceCurrency: 'USD',
        categoryId: '',
        image: null
    });

    const categories = [
        { value: 'LAPTOPS', label: 'Laptops' },
        { value: 'CASES', label: 'PC Cases' },
        { value: 'PERIPHERALS', label: 'Peripherals' }
    ];

    // Fetch all products
    const fetchProducts = async () => {
        setLoading(true);
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:8080/adamtech/products/getAll', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setProducts(response.data);
        } catch (error) {
            console.error('Error fetching products:', error);
            setError('Failed to load products');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setFormData(prev => ({
                ...prev,
                image: file
            }));

            // Create preview
            const reader = new FileReader();
            reader.onload = (e) => {
                setImagePreview(e.target.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const resetForm = () => {
        setFormData({
            name: '',
            description: '',
            sku: '',
            priceAmount: '',
            priceCurrency: 'USD',
            categoryId: '',
            image: null
        });
        setEditingProduct(null);
        setShowForm(false);
        setMessage('');
        setError('');
        setImagePreview(null);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage('');
        setError('');

        try {
            const token = localStorage.getItem('token');
            
            // Create form data for file upload
            const submitData = new FormData();
            submitData.append('name', formData.name);
            submitData.append('description', formData.description);
            submitData.append('sku', formData.sku);
            submitData.append('priceAmount', parseFloat(formData.priceAmount));
            submitData.append('priceCurrency', formData.priceCurrency);
            submitData.append('categoryId', formData.categoryId);
            
            if (formData.image) {
                submitData.append('image', formData.image);
            }

            let response;
            if (editingProduct) {
                // Update existing product
                response = await axios.put(
                    `http://localhost:8080/adamtech/products/update/${editingProduct.productId}`,
                    submitData,
                    {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                );
                setMessage('Product updated successfully!');
            } else {
                // Create new product
                response = await axios.post(
                    'http://localhost:8080/adamtech/products/create',
                    submitData,
                    {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                );
                setMessage('Product created successfully!');
            }

            resetForm();
            fetchProducts(); // Refresh the list
        } catch (error) {
            console.error('Error saving product:', error);
            setError(error.response?.data?.message || 'Failed to save product');
        } finally {
            setLoading(false);
        }
    };

    const handleEdit = (product) => {
        setEditingProduct(product);
        setFormData({
            name: product.name,
            description: product.description,
            sku: product.sku,
            priceAmount: product.price?.amount?.toString() || '',
            priceCurrency: product.price?.currency || 'USD',
            categoryId: product.categoryId,
            image: null
        });
        
        // Set image preview if product has an image
        if (product.productId) {
            setImagePreview(`http://localhost:8080/adamtech/products/${product.productId}/image?t=${Date.now()}`);
        } else {
            setImagePreview(null);
        }
        
        setShowForm(true);
    };

    const handleDelete = async (productId) => {
        if (!window.confirm('Are you sure you want to delete this product?')) {
            return;
        }

        try {
            const token = localStorage.getItem('token');
            await axios.delete(`http://localhost:8080/adamtech/products/delete/${productId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setMessage('Product deleted successfully!');
            fetchProducts(); // Refresh the list
        } catch (error) {
            console.error('Error deleting product:', error);
            setError('Failed to delete product');
        }
    };

    const removeImage = () => {
        setFormData(prev => ({
            ...prev,
            image: null
        }));
        setImagePreview(null);
        // Clear file input
        const fileInput = document.getElementById('imageUpload');
        if (fileInput) fileInput.value = '';
    };

    return (
        <div style={{
            maxWidth: '1200px',
            margin: '0 auto',
            padding: '20px',
            fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif"
        }}>
            <div style={{
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                marginBottom: '30px'
            }}>
                <h1 style={{
                    color: '#333',
                    fontSize: '2.5rem',
                    fontWeight: '600'
                }}>
                    Product Management
                </h1>
                <button
                    onClick={() => setShowForm(true)}
                    style={{
                        backgroundColor: '#ff6600',
                        color: 'white',
                        padding: '12px 24px',
                        border: 'none',
                        borderRadius: '8px',
                        cursor: 'pointer',
                        fontSize: '16px',
                        fontWeight: '600'
                    }}
                >
                    + Add New Product
                </button>
            </div>

            {/* Messages */}
            {message && (
                <div style={{
                    padding: '12px 16px',
                    backgroundColor: '#d4edda',
                    color: '#155724',
                    border: '1px solid #c3e6cb',
                    borderRadius: '8px',
                    marginBottom: '20px'
                }}>
                    ✅ {message}
                </div>
            )}

            {error && (
                <div style={{
                    padding: '12px 16px',
                    backgroundColor: '#f8d7da',
                    color: '#721c24',
                    border: '1px solid #f5c6cb',
                    borderRadius: '8px',
                    marginBottom: '20px'
                }}>
                    ❌ {error}
                </div>
            )}

            {/* Product Form */}
            {showForm && (
                <div style={{
                    backgroundColor: 'white',
                    borderRadius: '12px',
                    padding: '30px',
                    boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
                    marginBottom: '30px'
                }}>
                    <h2 style={{ marginBottom: '20px', color: '#333' }}>
                        {editingProduct ? 'Edit Product' : 'Add New Product'}
                    </h2>

                    <form onSubmit={handleSubmit}>
                        <div style={{
                            display: 'grid',
                            gridTemplateColumns: '1fr 1fr',
                            gap: '20px',
                            marginBottom: '20px'
                        }}>
                            {/* Product Name */}
                            <div style={{ display: 'flex', flexDirection: 'column' }}>
                                <label style={{ marginBottom: '8px', fontWeight: '500' }}>
                                    Product Name *
                                </label>
                                <input
                                    type="text"
                                    name="name"
                                    value={formData.name}
                                    onChange={handleInputChange}
                                    required
                                    disabled={loading}
                                    style={{
                                        padding: '12px 15px',
                                        borderRadius: '8px',
                                        border: '1px solid #ddd',
                                        fontSize: '14px'
                                    }}
                                />
                            </div>

                            {/* SKU */}
                            <div style={{ display: 'flex', flexDirection: 'column' }}>
                                <label style={{ marginBottom: '8px', fontWeight: '500' }}>
                                    SKU *
                                </label>
                                <input
                                    type="text"
                                    name="sku"
                                    value={formData.sku}
                                    onChange={handleInputChange}
                                    required
                                    disabled={loading}
                                    style={{
                                        padding: '12px 15px',
                                        borderRadius: '8px',
                                        border: '1px solid #ddd',
                                        fontSize: '14px'
                                    }}
                                />
                            </div>

                            {/* Category */}
                            <div style={{ display: 'flex', flexDirection: 'column' }}>
                                <label style={{ marginBottom: '8px', fontWeight: '500' }}>
                                    Category *
                                </label>
                                <select
                                    name="categoryId"
                                    value={formData.categoryId}
                                    onChange={handleInputChange}
                                    required
                                    disabled={loading}
                                    style={{
                                        padding: '12px 15px',
                                        borderRadius: '8px',
                                        border: '1px solid #ddd',
                                        fontSize: '14px'
                                    }}
                                >
                                    <option value="">Select Category</option>
                                    {categories.map(cat => (
                                        <option key={cat.value} value={cat.value}>
                                            {cat.label}
                                        </option>
                                    ))}
                                </select>
                            </div>

                            {/* Price */}
                            <div style={{ display: 'flex', flexDirection: 'column' }}>
                                <label style={{ marginBottom: '8px', fontWeight: '500' }}>
                                    Price *
                                </label>
                                <div style={{ display: 'flex', gap: '10px' }}>
                                    <input
                                        type="number"
                                        name="priceAmount"
                                        value={formData.priceAmount}
                                        onChange={handleInputChange}
                                        step="0.01"
                                        min="0"
                                        required
                                        disabled={loading}
                                        style={{
                                            padding: '12px 15px',
                                            borderRadius: '8px',
                                            border: '1px solid #ddd',
                                            fontSize: '14px',
                                            flex: '1'
                                        }}
                                        placeholder="Amount"
                                    />
                                    <select
                                        name="priceCurrency"
                                        value={formData.priceCurrency}
                                        onChange={handleInputChange}
                                        disabled={loading}
                                        style={{
                                            padding: '12px 15px',
                                            borderRadius: '8px',
                                            border: '1px solid #ddd',
                                            fontSize: '14px',
                                            width: '100px'
                                        }}
                                    >
                                        <option value="USD">USD</option>
                                        <option value="EUR">EUR</option>
                                        <option value="GBP">GBP</option>
                                        <option value="ZAR">ZAR</option>
                                    </select>
                                </div>
                            </div>

                            {/* Image Upload */}
                            <div style={{ display: 'flex', flexDirection: 'column', gridColumn: '1 / -1' }}>
                                <label style={{ marginBottom: '8px', fontWeight: '500' }}>
                                    Product Image
                                </label>
                                <div style={{ display: 'flex', gap: '20px', alignItems: 'flex-start' }}>
                                    <div style={{ flex: 1 }}>
                                        <input
                                            id="imageUpload"
                                            type="file"
                                            accept="image/*"
                                            onChange={handleFileChange}
                                            disabled={loading}
                                            style={{
                                                padding: '12px 15px',
                                                borderRadius: '8px',
                                                border: '1px solid #ddd',
                                                fontSize: '14px',
                                                width: '100%'
                                            }}
                                        />
                                        <p style={{ fontSize: '12px', color: '#666', marginTop: '5px' }}>
                                            Supported formats: JPG, PNG, GIF. Max size: 5MB
                                        </p>
                                    </div>
                                    
                                    {imagePreview && (
                                        <div style={{ position: 'relative', display: 'inline-block' }}>
                                            <img 
                                                src={imagePreview} 
                                                alt="Preview" 
                                                style={{
                                                    width: '100px',
                                                    height: '100px',
                                                    objectFit: 'cover',
                                                    borderRadius: '8px',
                                                    border: '1px solid #ddd'
                                                }}
                                            />
                                            <button
                                                type="button"
                                                onClick={removeImage}
                                                style={{
                                                    position: 'absolute',
                                                    top: '-8px',
                                                    right: '-8px',
                                                    backgroundColor: '#dc3545',
                                                    color: 'white',
                                                    border: 'none',
                                                    borderRadius: '50%',
                                                    width: '24px',
                                                    height: '24px',
                                                    cursor: 'pointer',
                                                    fontSize: '12px',
                                                    display: 'flex',
                                                    alignItems: 'center',
                                                    justifyContent: 'center'
                                                }}
                                            >
                                                ×
                                            </button>
                                        </div>
                                    )}
                                </div>
                            </div>

                            {/* Description - Full Width */}
                            <div style={{ display: 'flex', flexDirection: 'column', gridColumn: '1 / -1' }}>
                                <label style={{ marginBottom: '8px', fontWeight: '500' }}>
                                    Description *
                                </label>
                                <textarea
                                    name="description"
                                    value={formData.description}
                                    onChange={handleInputChange}
                                    required
                                    disabled={loading}
                                    rows="4"
                                    style={{
                                        padding: '12px 15px',
                                        borderRadius: '8px',
                                        border: '1px solid #ddd',
                                        fontSize: '14px',
                                        resize: 'vertical'
                                    }}
                                    placeholder="Enter detailed product description..."
                                />
                            </div>
                        </div>

                        {/* Form Actions */}
                        <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end' }}>
                            <button
                                type="button"
                                onClick={resetForm}
                                disabled={loading}
                                style={{
                                    padding: '12px 24px',
                                    backgroundColor: '#6c757d',
                                    color: 'white',
                                    border: 'none',
                                    borderRadius: '8px',
                                    cursor: 'pointer',
                                    fontSize: '14px'
                                }}
                            >
                                Cancel
                            </button>
                            <button
                                type="submit"
                                disabled={loading}
                                style={{
                                    padding: '12px 24px',
                                    backgroundColor: loading ? '#ccc' : '#28a745',
                                    color: 'white',
                                    border: 'none',
                                    borderRadius: '8px',
                                    cursor: loading ? 'not-allowed' : 'pointer',
                                    fontSize: '14px',
                                    fontWeight: '600'
                                }}
                            >
                                {loading ? 'Saving...' : (editingProduct ? 'Update Product' : 'Create Product')}
                            </button>
                        </div>
                    </form>
                </div>
            )}

            {/* Products List */}
            <div style={{
                backgroundColor: 'white',
                borderRadius: '12px',
                padding: '30px',
                boxShadow: '0 4px 20px rgba(0,0,0,0.1)'
            }}>
                <h2 style={{ marginBottom: '20px', color: '#333' }}>
                    All Products ({products.length})
                </h2>

                {loading ? (
                    <div style={{ textAlign: 'center', padding: '40px' }}>
                        <p>Loading products...</p>
                    </div>
                ) : products.length === 0 ? (
                    <div style={{ textAlign: 'center', padding: '40px', color: '#666' }}>
                        <p>No products found. Add your first product!</p>
                    </div>
                ) : (
                    <div style={{ overflowX: 'auto' }}>
                        <table style={{
                            width: '100%',
                            borderCollapse: 'collapse'
                        }}>
                            <thead>
                            <tr style={{ backgroundColor: '#f8f9fa' }}>
                                <th style={{ padding: '12px', textAlign: 'left', borderBottom: '2px solid #dee2e6' }}>Image</th>
                                <th style={{ padding: '12px', textAlign: 'left', borderBottom: '2px solid #dee2e6' }}>Name</th>
                                <th style={{ padding: '12px', textAlign: 'left', borderBottom: '2px solid #dee2e6' }}>SKU</th>
                                <th style={{ padding: '12px', textAlign: 'left', borderBottom: '2px solid #dee2e6' }}>Category</th>
                                <th style={{ padding: '12px', textAlign: 'left', borderBottom: '2px solid #dee2e6' }}>Price</th>
                                <th style={{ padding: '12px', textAlign: 'left', borderBottom: '2px solid #dee2e6' }}>Description</th>
                                <th style={{ padding: '12px', textAlign: 'left', borderBottom: '2px solid #dee2e6' }}>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {products.map(product => (
                                <tr key={product.productId} style={{ borderBottom: '1px solid #dee2e6' }}>
                                    <td style={{ padding: '12px' }}>
                                        {product.productId ? (
                                            <img 
                                                src={`http://localhost:8080/adamtech/products/${product.productId}/image?t=${Date.now()}`}
                                                alt={product.name}
                                                style={{
                                                    width: '50px',
                                                    height: '50px',
                                                    objectFit: 'cover',
                                                    borderRadius: '4px',
                                                    border: '1px solid #ddd'
                                                }}
                                                onError={(e) => {
                                                    e.target.src = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNTAiIGhlaWdodD0iNTAiIHZpZXdCb3g9IjAgMCA1MCA1MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHJlY3Qgd2lkdGg9IjUwIiBoZWlnaHQ9IjUwIiBmaWxsPSIjRjBGMEYwIi8+CjxwYXRoIGQ9Ik0yNSAzMEMyOC44NjYgMzAgMzIgMjYuODY2IDMyIDIzQzMyIDE5LjEzNCAyOC44NjYgMTYgMjUgMTZDMjEuMTM0IDE2IDE4IDE5LjEzNCAxOCAyM0MxOCAyNi44NjYgMjEuMTM0IDMwIDI1IDMwWiIgZmlsbD0iI0Q4RDhEOCIvPgo8cGF0aCBkPSJNMTguNSA0MEMxNi41NjcgNDAgMTUgMzguNDMzIDE1IDM2LjVMMTUgMTkuNUMxNSAxNy41NjcgMTYuNTY3IDE2IDE4LjUgMTZMMzEuNSAxNkMzMy40MzMgMTYgMzUgMTcuNTY3IDM1IDE5LjVMMzUgMzYuNUMzNSAzOC40MzMgMzMuNDMzIDQwIDMxLjUgNDBMMTguNSA0MFoiIGZpbGw9IiNEOEQ4RDgiLz4KPC9zdmc+';
                                                }}
                                            />
                                        ) : (
                                            <div style={{
                                                width: '50px',
                                                height: '50px',
                                                backgroundColor: '#f0f0f0',
                                                borderRadius: '4px',
                                                display: 'flex',
                                                alignItems: 'center',
                                                justifyContent: 'center',
                                                color: '#999',
                                                fontSize: '10px',
                                                textAlign: 'center'
                                            }}>
                                                No Image
                                            </div>
                                        )}
                                    </td>
                                    <td style={{ padding: '12px', fontWeight: '500' }}>
                                        {product.name}
                                    </td>
                                    <td style={{ padding: '12px' }}>{product.sku}</td>
                                    <td style={{ padding: '12px' }}>
                                            <span style={{
                                                padding: '4px 8px',
                                                backgroundColor: '#e9ecef',
                                                borderRadius: '4px',
                                                fontSize: '12px',
                                                fontWeight: '500'
                                            }}>
                                                {product.categoryId}
                                            </span>
                                    </td>
                                    <td style={{ padding: '12px' }}>
                                        {product.price?.currency} {product.price?.amount?.toFixed(2)}
                                    </td>
                                    <td style={{ padding: '12px', maxWidth: '300px' }}>
                                        <div style={{
                                            fontSize: '14px',
                                            color: '#666',
                                            overflow: 'hidden',
                                            textOverflow: 'ellipsis',
                                            display: '-webkit-box',
                                            WebkitLineClamp: 2,
                                            WebkitBoxOrient: 'vertical'
                                        }}>
                                            {product.description}
                                        </div>
                                    </td>
                                    <td style={{ padding: '12px' }}>
                                        <div style={{ display: 'flex', gap: '8px' }}>
                                            <button
                                                onClick={() => handleEdit(product)}
                                                style={{
                                                    padding: '6px 12px',
                                                    backgroundColor: '#17a2b8',
                                                    color: 'white',
                                                    border: 'none',
                                                    borderRadius: '4px',
                                                    cursor: 'pointer',
                                                    fontSize: '12px'
                                                }}
                                            >
                                                Edit
                                            </button>
                                            <button
                                                onClick={() => handleDelete(product.productId)}
                                                style={{
                                                    padding: '6px 12px',
                                                    backgroundColor: '#dc3545',
                                                    color: 'white',
                                                    border: 'none',
                                                    borderRadius: '4px',
                                                    cursor: 'pointer',
                                                    fontSize: '12px'
                                                }}
                                            >
                                                Delete
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        </div>
    );
};

export default AdminProducts;