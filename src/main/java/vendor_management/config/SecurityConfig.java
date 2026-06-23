package vendor_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import vendor_management.security.JwtFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {


        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 1. Authentication endpoints complete bypass
                        .requestMatchers("/auth/**").permitAll()

                        // 2. FIXED: Explicitly allow Purchase Order endpoints for both Admin and User/Employee roles
                        // It covers both list page ("") and specific ID details ("/**") safely
                        .requestMatchers("/purchase-orders", "/purchase-orders/**").hasAnyRole("ADMIN", "USER")

                        // 3. Admin strictly bounded resources
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 4. User bounded resources
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")

                        // 5. Vendor explicit authentication checkpoints
                        .requestMatchers("/admin/vendors/**").authenticated()

                        // 6. Remaining fallback requests
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}