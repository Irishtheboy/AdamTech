import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import loginBg from "../assets/images/loginbg.jpg"; // same background as SignUp

const Login = ({ setUser }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const res = await axios.post(
          "http://localhost:8080/adamtech/customer/login",
          { email, password }
      );

      console.log("Login response:", res.data); // Debug log

      // ✅ CORRECTED: Your backend returns "token" not "accessToken"
      const { token, email: userEmail, role } = res.data;

      // ✅ Save to localStorage with correct key names
      localStorage.setItem("token", token);
      localStorage.setItem("user", JSON.stringify({ email: userEmail, role }));

      // ✅ Update user state
      setUser({ email: userEmail, role });
      navigate("/");
    } catch (err) {
      console.error("Login error:", err.response?.data || err.message);
      setError("Login failed. Please check email and password.");
    } finally {
      setLoading(false);
    }
  };

  // Reuse styles from SignUp
  const authContainerStyle = {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    minHeight: "100vh",
    background: `url(${loginBg}) no-repeat center center fixed`,
    backgroundSize: "cover",
    padding: "20px",
  };

  const authTitleStyle = {
    color: "#fff",
    fontSize: "2rem",
    marginBottom: "20px",
    textAlign: "center",
    textShadow: "2px 2px 4px rgba(0, 0, 0, 0.5)",
  };

  const authFormStyle = {
    background: "rgba(255, 255, 255, 0.15)",
    backdropFilter: "blur(10px)",
    WebkitBackdropFilter: "blur(10px)",
    borderRadius: "15px",
    padding: "30px",
    width: "100%",
    maxWidth: "400px",
    boxShadow: "0 8px 32px rgba(0, 0, 0, 0.3)",
    border: "1px solid rgba(255, 255, 255, 0.18)",
    display: "flex",
    flexDirection: "column",
    gap: "15px",
  };

  const authInputStyle = {
    padding: "12px",
    border: "none",
    borderRadius: "5px",
    background: "rgba(255, 255, 255, 0.2)",
    color: "#fff",
    fontSize: "1rem",
    outline: "none",
    transition: "background 0.3s",
  };

  const authInputFocusStyle = {
    background: "rgba(255, 255, 255, 0.3)",
  };

  const authBtnStyle = {
    padding: "12px",
    border: "none",
    borderRadius: "5px",
    background: "#4f46e5", // Indigo
    color: "#fff",
    fontSize: "1rem",
    fontWeight: "bold",
    cursor: "pointer",
    transition: "background 0.3s",
  };

  const authBtnHoverStyle = {
    background: "#3730a3",
  };

  const authBtnDisabledStyle = {
    background: "#ccc",
    cursor: "not-allowed",
  };

  const authFooterStyle = {
    marginTop: "20px",
    color: "#fff",
    textAlign: "center",
  };

  const authLinkStyle = {
    color: "#fff",
    textDecoration: "none",
    fontWeight: "bold",
  };

  const authLinkHoverStyle = {
    textDecoration: "underline",
  };

  return (
      <div style={authContainerStyle}>
        <h2 style={authTitleStyle}>Welcome Back</h2>

        <form style={authFormStyle} onSubmit={handleSubmit}>
          <input
              type="email"
              placeholder="Email Address"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              style={authInputStyle}
              onFocus={(e) =>
                  (e.target.style.background = authInputFocusStyle.background)
              }
              onBlur={(e) =>
                  (e.target.style.background = authInputStyle.background)
              }
          />
          <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              style={authInputStyle}
              onFocus={(e) =>
                  (e.target.style.background = authInputFocusStyle.background)
              }
              onBlur={(e) =>
                  (e.target.style.background = authInputStyle.background)
              }
          />
          <button
              type="submit"
              disabled={loading}
              style={{
                ...authBtnStyle,
                ...(loading ? authBtnDisabledStyle : {}),
              }}
              onMouseOver={(e) =>
                  !loading &&
                  (e.target.style.background = authBtnHoverStyle.background)
              }
              onMouseOut={(e) =>
                  !loading && (e.target.style.background = authBtnStyle.background)
              }
          >
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>

        {error && <p style={{ color: "red", marginTop: "10px" }}>{error}</p>}

        <div style={authFooterStyle}>
          <p>
            Don't have an account?{" "}
            <Link
                to="/signup"
                style={authLinkStyle}
                onMouseOver={(e) =>
                    (e.target.style.textDecoration =
                        authLinkHoverStyle.textDecoration)
                }
                onMouseOut={(e) =>
                    (e.target.style.textDecoration = authLinkStyle.textDecoration)
                }
            >
              Sign Up
            </Link>
          </p>
        </div>
      </div>
  );
};

export default Login;