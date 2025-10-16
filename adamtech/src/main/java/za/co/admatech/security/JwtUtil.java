package za.co.admatech.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mySuperSecretKeyForJWT123"; // 🔐 change in production

    private final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15; // 15 mins
    private final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7 days

    public String generateAccessToken(String username, String role) {
        return createToken(username, role, ACCESS_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(String username, String role) {
        return createToken(username, role, REFRESH_TOKEN_VALIDITY);
    }

    private String createToken(String username, String role, long validity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String getRole(String token) {
        return (String) Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody().get("role");
    }
}

