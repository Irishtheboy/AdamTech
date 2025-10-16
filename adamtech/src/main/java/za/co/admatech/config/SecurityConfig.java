package za.co.admatech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // This enables @PreAuthorize
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configure(http))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (no authentication required)
                        .requestMatchers("/customer/login").permitAll()
                        .requestMatchers("/customer/create").permitAll()
                        .requestMatchers("/products/getAll").permitAll()
                        .requestMatchers("/products/**").permitAll()

                        // 🔒 ADMIN ENDPOINTS - ONLY FOR ROLE_ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 🔒 CUSTOMER ENDPOINTS - AUTHENTICATED USERS ONLY
                        .requestMatchers("/customer/me").authenticated()  // ✅ CHANGED: Now requires auth
                        .requestMatchers("/customer/update").authenticated()
                        .requestMatchers("/customer/delete/**").authenticated()
                        .requestMatchers("/customer/getAll").authenticated()

                        // 🔒 ADMIN-ONLY CUSTOMER ENDPOINTS
                        .requestMatchers("/customer/make-admin/**").hasRole("ADMIN")  // ✅ CHANGED: Admin only
                        .requestMatchers("/customer/fix-user-roles").hasRole("ADMIN") // ✅ CHANGED: Admin only
                        .requestMatchers("/customer/admin/**").hasRole("ADMIN")       // ✅ CHANGED: Admin only

                        // 🔒 PROTECTED RESOURCES
                        .requestMatchers("/cart/**").authenticated()
                        .requestMatchers("/cart-items/**").authenticated()
                        .requestMatchers("/order/**").authenticated()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}