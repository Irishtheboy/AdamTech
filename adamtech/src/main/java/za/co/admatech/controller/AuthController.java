package za.co.admatech.controller;

import za.co.admatech.config.JwtConfig;
import za.co.admatech.domain.RefreshToken;
import za.co.admatech.domain.User;
import za.co.admatech.dto.*;
import za.co.admatech.repository.RefreshTokenRepository;
import za.co.admatech.service.user_domain_service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtConfig.generateToken(authentication);
            String refreshJwt = jwtConfig.generateRefreshToken(authentication.getName());
            
            User user = (User) authentication.getPrincipal();
            
            // Save refresh token
            RefreshToken refreshToken = new RefreshToken(refreshJwt, user, 
                    LocalDateTime.now().plusDays(7));
            refreshTokenRepository.save(refreshToken);

            JwtResponse jwtResponse = new JwtResponse(jwt, refreshJwt, user);

            return ResponseEntity.ok(ApiResponse.success("Login successful", jwtResponse));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        try {
            if (userService.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Username is already taken!"));
            }

            if (userService.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Email is already in use!"));
            }

            // Create new user account
            User user = new User(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    signUpRequest.getPassword());

            User savedUser = userService.createUser(user);

            return ResponseEntity.ok(ApiResponse.success("User registered successfully",
                    new UserResponse(savedUser)));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(requestRefreshToken);
        
        if (refreshTokenOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Refresh token is not in database!"));
        }

        RefreshToken refreshToken = refreshTokenOpt.get();
        
        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Refresh token was expired. Please make a new signin request"));
        }

        User user = refreshToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        String token = jwtConfig.generateToken(auth);

        JwtResponse jwtResponse = new JwtResponse(token, requestRefreshToken, user);
        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", jwtResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByToken(refreshToken);
        
        if (tokenOpt.isPresent()) {
            refreshTokenRepository.delete(tokenOpt.get());
        }

        return ResponseEntity.ok(ApiResponse.success("User logged out successfully"));
    }

    // Inner classes for request/response DTOs
    public static class RefreshTokenRequest {
        private String refreshToken;

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    public static class UserResponse {
        private Long id;
        private String username;
        private String email;
        private String role;

        public UserResponse(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.role = user.getRole().name();
        }

        // Getters
        public Long getId() { return id; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
    }
}
