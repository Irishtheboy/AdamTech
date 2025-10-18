import React, { useState, useEffect, useRef } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { AiOutlineSearch, AiOutlineUser, AiOutlineHeart, AiOutlineShoppingCart } from 'react-icons/ai';
import '../../styles/HeaderStyling.css';
import ADM_logo from '../../assets/images/ADM_logo.png';

const Header = ({ user, setUser }) => {
    const [isSticky, setIsSticky] = useState(false);
    const [showProfileMenu, setShowProfileMenu] = useState(false);
    const cartItems = [];
    const location = useLocation();
    const navigate = useNavigate();
    const profileRef = useRef();

    useEffect(() => {
        const handleScroll = () => setIsSticky(window.scrollY >= 50);
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (profileRef.current && !profileRef.current.contains(event.target)) {
                setShowProfileMenu(false);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    const getLinkStyle = (path) => ({
        color: location.pathname === path ? '#f4a261' : '#333',
        textDecoration: 'none',
        fontSize: '1rem',
        fontWeight: location.pathname === path ? 'bold' : 'normal'
    });

    const handleProfileClick = () => {
        if (!user) {
            navigate('/login');
        } else {
            setShowProfileMenu(!showProfileMenu);
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        setUser(null);
        setShowProfileMenu(false);
        navigate('/');
    };

    return (
        <header className={isSticky ? 'header sticky' : 'header'} style={{ backgroundColor: '#fff', padding: '10px 20px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)' }}>
            <div className="container" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', maxWidth: '1200px', margin: '0 auto' }}>

                {/* Logo */}
                <h2 className="logo" style={{ margin: 0, display: 'flex', alignItems: 'center' }}>
                    <Link to="/" style={{ textDecoration: 'none', display: 'flex', alignItems: 'center', color: '#f4a261' }}>
                        <img src={ADM_logo} alt="AdamTech Logo" style={{ height: '40px', width: 'auto', marginRight: '10px' }} />
                        <span style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>AdamTech</span>
                    </Link>
                </h2>

                {/* Navigation Links */}
                <nav style={{ display: 'flex', gap: '20px', flex: 1, justifyContent: 'center' }}>
                    <Link to="/" style={getLinkStyle('/')}>HOME</Link>
                    <Link to="/about" style={getLinkStyle('/about')}>ABOUT</Link>
                    <Link to="/shop" style={getLinkStyle('/shop')}>SHOP</Link>
                    <Link to="/contact" style={getLinkStyle('/contact')}>CONTACT</Link>

                    {/* ✅ ADMIN LINK - Only show for admins */}
                    {user && user.role === 'ROLE_ADMIN' && (
                        <Link
                            to="/admin/dashboard"
                            style={{
                                ...getLinkStyle('/admin/dashboard'),
                                color: '#d32f2f',
                                fontWeight: 'bold'
                            }}
                        >
                            ADMIN
                        </Link>
                    )}
                </nav>

                {/* Icons and Profile */}
                <div style={{ display: 'flex', gap: '15px', alignItems: 'center' }}>
                    <Link to="/search" className="icon" style={{ color: '#666', fontSize: '1.2rem', textDecoration: 'none' }}>
                        <AiOutlineSearch />
                    </Link>

                    {/* User Profile */}
                    <div
                        className="user-profile"
                        ref={profileRef}
                        onClick={handleProfileClick}
                        style={{ position: 'relative', cursor: 'pointer' }}
                    >
                        <div className="user-profile-inner" style={{ display: 'flex', alignItems: 'center', gap: '5px' }}>
                            <AiOutlineUser />
                            <span>{user ? user.email : 'Sign In/Register'}</span>
                            {/* ✅ Show admin badge if user is admin */}
                            {user && user.role === 'ROLE_ADMIN' && (
                                <span style={{
                                    backgroundColor: '#d32f2f',
                                    color: 'white',
                                    fontSize: '0.7rem',
                                    padding: '2px 6px',
                                    borderRadius: '10px',
                                    fontWeight: 'bold'
                                }}>
                                    ADMIN
                                </span>
                            )}
                        </div>

                        {user && showProfileMenu && (
                            <div style={{
                                position: 'absolute',
                                top: '35px',
                                right: '0',
                                width: '220px',
                                background: 'white',
                                borderRadius: '8px',
                                padding: '10px',
                                boxShadow: '0 4px 20px rgba(0,0,0,0.15)',
                                display: 'flex',
                                flexDirection: 'column',
                                gap: '8px',
                                zIndex: 1000,
                                border: '1px solid #eee'
                            }}>
                                <Link
                                    to="/profile"
                                    style={{
                                        color: '#333',
                                        textDecoration: 'none',
                                        padding: '8px 12px',
                                        borderRadius: '4px',
                                        transition: 'background 0.3s ease',
                                    }}
                                    onMouseOver={(e) => e.currentTarget.style.background = '#f5f5f5'}
                                    onMouseOut={(e) => e.currentTarget.style.background = 'transparent'}
                                >
                                    👤 Profile
                                </Link>

                                {/* ✅ ADMIN DASHBOARD SECTION */}
                                {user.role === 'ROLE_ADMIN' && (
                                    <>
                                        <div style={{
                                            padding: '4px 12px',
                                            fontSize: '0.8rem',
                                            color: '#999',
                                            fontWeight: 'bold',
                                            borderBottom: '1px solid #eee',
                                            marginBottom: '4px'
                                        }}>
                                            ADMIN TOOLS
                                        </div>

                                        <Link
                                            to="/admin/dashboard"
                                            style={{
                                                color: '#d32f2f',
                                                textDecoration: 'none',
                                                padding: '8px 12px',
                                                borderRadius: '4px',
                                                transition: 'background 0.3s ease',
                                                fontWeight: 'bold'
                                            }}
                                            onMouseOver={(e) => e.currentTarget.style.background = '#ffebee'}
                                            onMouseOut={(e) => e.currentTarget.style.background = 'transparent'}
                                        >
                                            📊 Admin Dashboard
                                        </Link>

                                        <Link
                                            to="/admin/orders"
                                            style={{
                                                color: '#d32f2f',
                                                textDecoration: 'none',
                                                padding: '8px 12px',
                                                borderRadius: '4px',
                                                transition: 'background 0.3s ease',
                                                fontWeight: 'bold'
                                            }}
                                            onMouseOver={(e) => e.currentTarget.style.background = '#ffebee'}
                                            onMouseOut={(e) => e.currentTarget.style.background = 'transparent'}
                                        >
                                            📦 Order Management
                                        </Link>
                                    </>
                                )}

                                <button
                                    onClick={handleLogout}
                                    style={{
                                        background: 'none',
                                        border: 'none',
                                        color: '#666',
                                        cursor: 'pointer',
                                        textAlign: 'left',
                                        padding: '8px 12px',
                                        borderRadius: '4px',
                                        transition: 'background 0.3s ease',
                                        marginTop: '8px',
                                        borderTop: '1px solid #eee',
                                        paddingTop: '12px'
                                    }}
                                    onMouseOver={(e) => e.currentTarget.style.background = '#f5f5f5'}
                                    onMouseOut={(e) => e.currentTarget.style.background = 'transparent'}
                                >
                                    🚪 Logout
                                </button>
                            </div>
                        )}
                    </div>

                    {/* Wishlist & Cart */}
                    <Link to="/wishlist" className="icon" style={{ color: '#666', fontSize: '1.2rem', textDecoration: 'none' }}>
                        <AiOutlineHeart />
                    </Link>
                    <Link to="/cart" className="icon" style={{ color: '#666', fontSize: '1.2rem', textDecoration: 'none', position: 'relative' }}>
                        <AiOutlineShoppingCart />
                        {cartItems.length > 0 && (
                            <span className="badge" style={{ backgroundColor: '#f4a261', color: '#fff', borderRadius: '50%', padding: '2px 6px', fontSize: '0.8rem', position: 'absolute', top: '-5px', right: '-5px' }}>
                                {cartItems.length}
                            </span>
                        )}
                    </Link>
                </div>
            </div>
        </header>
    );
};

export default Header;