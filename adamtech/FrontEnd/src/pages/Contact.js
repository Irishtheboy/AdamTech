import React from 'react';

const Contact = () => {
    return (
        <div style={{ maxWidth: '800px', margin: '0 auto', padding: '20px' }}>
            <h1 style={{ textAlign: 'center', marginBottom: '25px' }}>Contact Us</h1>

            <div
                style={{
                    background: '#fff',
                    padding: '20px',
                    borderRadius: '12px',
                    boxShadow: '0 1px 3px rgba(0,0,0,0.08)',
                    lineHeight: '1.6',
                }}
            >
                <p style={{ fontSize: '14px', color: '#333' }}>
                    For any inquiries, questions, or support, you can reach out to us using the details below.
                    We are happy to assist you with any part of our website.
                </p>

                <div style={{ marginTop: '20px' }}>
                    <h2 style={{ fontSize: '16px', marginBottom: '10px' }}>Head Office</h2>
                    <p style={{ fontSize: '14px', color: '#333' }}>
                        Cape Town Central, Western Cape, South Africa
                    </p>

                    <h2 style={{ fontSize: '16px', marginTop: '15px', marginBottom: '10px' }}>Trading Hours</h2>
                    <p style={{ fontSize: '14px', color: '#333' }}>
                        Monday – Friday: 08:00 – 17:00 <br />
                        Saturday: 09:00 – 13:00 <br />
                        Sunday & Public Holidays: Closed
                    </p>

                    <h2 style={{ fontSize: '16px', marginTop: '15px', marginBottom: '10px' }}>Contact Details</h2>
                    <p style={{ fontSize: '14px', color: '#333' }}>
                        Email: support@adamtech.co.za <br />
                        Phone: +27 12 345 6789
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Contact;
