package za.co.admatech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.security.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/adamtech/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
        String email = user.get("email");
        String password = user.get("password");

        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Authentication failed: " + e.getMessage());
        }

        String accessToken = jwtUtil.generateAccessToken(email, "USER");
        String refreshToken = jwtUtil.generateRefreshToken(email, "USER");

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");
        if (jwtUtil.validateToken(refreshToken)) {
            String email = jwtUtil.getUsername(refreshToken);
            String newAccessToken = jwtUtil.generateAccessToken(email, "USER");
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        }
        return ResponseEntity.badRequest().body("Invalid refresh token");
    }
}

