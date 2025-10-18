import React from "react";

const teamMembers = [
    { name: "Franco Lukhele", role: "Front-end & Back-end", img: "" },
    { name: "Naqeebah Khan", role: "Front-end & Back-end", img: "" },
    { name: "Teyana Raubenheimer", role: "Front-end & Back-end", img: "" },
    { name: "Rori", role: "Front-end & Back-end", img: "" },
    { name: "Seymour Lawrence", role: "Front-end & Back-end", img: "" },
];

const About = () => {
    return (
        <div style={{ maxWidth: "1000px", margin: "0 auto", padding: "20px" }}>
            <h1 style={{ textAlign: "center", marginBottom: "25px" }}>About Us</h1>

            <div
                style={{
                    background: "#fff",
                    padding: "20px",
                    borderRadius: "12px",
                    boxShadow: "0 1px 3px rgba(0,0,0,0.08)",
                    lineHeight: "1.6",
                    marginBottom: "40px",
                }}
            >
                <p style={{ fontSize: "14px", color: "#333" }}>
                    We are final-year students n the Diploma in ICT: Applications Development.
                    As part of our capstone project for the subject Applications Development Practice 3(ADP), we have developed this eCommerce platform to
                    showcase our skills in full-stack development, user interface design, and
                    practical application of our learning throughout the subject.
                </p>

                <p style={{ fontSize: "14px", color: "#333", marginTop: "15px" }}>
                    This platform allows users to browse products, manage their orders, save their
                    favorite items, manage addresses, and request refunds or returns. Our goal was
                    to create a clean, functional, and user-friendly experience while applying best
                    coding practices and modern development techniques.
                </p>

                <p style={{ fontSize: "14px", color: "#333", marginTop: "15px" }}>
                    Thank you for visiting our project! We hope you enjoy exploring it as much as we
                    enjoyed building it.
                </p>
            </div>

            {/* Team Cards */}
            <div
                style={{
                    display: "grid",
                    gridTemplateColumns: "repeat(auto-fit, minmax(180px, 1fr))",
                    gap: "20px",
                }}
            >
                {teamMembers.map((member, index) => (
                    <div
                        key={index}
                        style={{
                            background: "#fff",
                            padding: "20px",
                            borderRadius: "12px",
                            boxShadow: "0 4px 8px rgba(0,0,0,0.1)",
                            textAlign: "center",
                            transition: "transform 0.2s",
                        }}
                    >
                        <div
                            style={{
                                width: "100px",
                                height: "100px",
                                margin: "0 auto 15px auto",
                                borderRadius: "50%",
                                backgroundColor: "#ccc",
                                display: "flex",
                                alignItems: "center",
                                justifyContent: "center",
                                fontSize: "40px",
                                color: "#fff",
                                overflow: "hidden",
                            }}
                        >
                            {/* Placeholder initials */}
                            {member.name
                                .split(" ")
                                .map((n) => n[0])
                                .join("")}
                        </div>
                        <h3 style={{ margin: "10px 0 5px 0" }}>{member.name}</h3>
                        <p style={{ fontSize: "13px", color: "#666", margin: 0 }}>{member.role}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default About;
