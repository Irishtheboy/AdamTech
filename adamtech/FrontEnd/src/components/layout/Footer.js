import React from 'react';

const Footer = () => {
  return (
    <footer style={{
      backgroundColor: '#fff',
      color: '#333',
      padding: '10px 30px', // slimmer padding
      display: 'flex',
      flexWrap: 'wrap',
      gap: '15px', // slightly smaller gap
      fontFamily: "'Segoe UI', sans-serif",
      boxShadow: '0 -4px 8px rgba(0, 0, 0, 0.05)', // subtle shadow like header
    }}>
      
      {/* Subscription Section */}
      <div style={{ flex: '1 1 250px', marginBottom: '10px' }}> 
        <h2 style={{ color: '#f4a261', marginBottom: '6px', fontSize: '1.2rem' }}>AdamTech</h2>
        <p style={{ color: '#666', marginBottom: '6px', fontSize: '0.9rem' }}>
          Subscribe to our Email alerts for early discount offers and new product info.
        </p>
        <input 
          type="email" 
          placeholder="Email Address*" 
          style={{
            width: '70%',
            padding: '6px',
            marginBottom: '6px',
            borderRadius: '4px',
            border: '1px solid #ccc',
            backgroundColor: '#fff',
            color: '#333',
            fontSize: '0.85rem'
          }} 
        />
        <button style={{
          backgroundColor: '#f4a261',
          color: '#fff',
          padding: '6px 14px',
          border: 'none',
          borderRadius: '4px',
          cursor: 'pointer',
          fontSize: '0.85rem'
        }}>
          Subscribe
        </button>
      </div>

      {/* Help Section */}
      <div style={{ flex: '1 1 150px', marginBottom: '10px' }}>
        <h3 style={{ color: '#f4a261', marginBottom: '6px', fontSize: '1rem' }}>Help</h3>
        <ul style={{ listStyle: 'none', padding: 0, color: '#666', lineHeight: '1.6', fontSize: '0.85rem' }}>
          <li>FAQs</li>
          <li>Track Order</li>
          <li>Cancel Order</li>
          <li>Return Order</li>
          <li>Warranty Info</li>
        </ul>
      </div>

      {/* Policies Section */}
      <div style={{ flex: '1 1 150px', marginBottom: '10px' }}>
        <h3 style={{ color: '#f4a261', marginBottom: '6px', fontSize: '1rem' }}>Policies</h3>
        <ul style={{ listStyle: 'none', padding: 0, color: '#666', lineHeight: '1.6', fontSize: '0.85rem' }}>
          <li>Return Policy</li>
          <li>Security</li>
          <li>Sitemap</li>
          <li>Privacy Policy</li>
          <li>Terms & Conditions</li>
        </ul>
      </div>

      {/* Company Section */}
      <div style={{ flex: '1 1 150px', marginBottom: '10px' }}>
        <h3 style={{ color: '#f4a261', marginBottom: '6px', fontSize: '1rem' }}>Company</h3>
        <ul style={{ listStyle: 'none', padding: 0, color: '#666', lineHeight: '1.6', fontSize: '0.85rem' }}>
          <li>About Us</li>
          <li>Contact Us</li>
          <li>Service Centres</li>
          <li>Careers</li>
          <li>Affiliates</li>
        </ul>
      </div>

    </footer>
  );
};

export default Footer;




