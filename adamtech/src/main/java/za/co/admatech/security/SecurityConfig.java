package za.co.admatech.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// This annotation is typically used on a class that extends WebSecurityConfigurerAdapter or provides a SecurityFilterChain bean.
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .authorizeHttpRequests(auth -> auth
            // Public endpoints - no authentication required
            .requestMatchers(
                "/customer/login",
                "/customer/create",
                "/products/read/**",
                "/products/getAll",
                "/products/{productId}/image"
            ).permitAll()
            
            // Customer-specific endpoints - require authentication
            .requestMatchers(
                "/customer/me",
                "/customer/read/**",
                "/customer/update",
                "/customer/delete/**"
            ).authenticated()
            
            // Shopping cart endpoints - require authentication
            .requestMatchers(
                "/cart/**",
                "/cart-items/**",
                "/wishlist/**"
            ).authenticated()
            
            // Order and payment endpoints - require authentication
            .requestMatchers(
                "/order/**",
                "/orderItem/**",
                "/payments/**",
                "/address/**"
            ).authenticated()
            
            // Admin endpoints - require authentication (should add role-based auth later)
            .requestMatchers(
                "/customer/getAll",
                "/products/create",
                "/products/update/**",
                "/products/delete/**",
                "/inventory/**"
            ).authenticated()
            
            .anyRequest().authenticated()
        )
        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // ensure JWT auth filter runs before the username/password filter
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
