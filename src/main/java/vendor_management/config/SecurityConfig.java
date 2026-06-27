package vendor_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vendor_management.security.JwtFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                // Ensures session context is completely stateless for custom API token mapping
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Authentication endpoints complete bypass
                        .requestMatchers("/auth/**").permitAll()

                        // 🚀 2. EMPLOYEES MODULE: Explicitly allow dynamic listings publicly to unblock dropdowns
                        .requestMatchers(
                                "/admin/employees",
                                "/admin/employees/",
                                "/admin/employees/**",
                                "/admin/employees/active"
                        ).permitAll()

                        // 🚀 3. PURCHASE ORDERS MODULE: Bypassing variations for dynamic frontend cross-origin lookups
                        .requestMatchers(
                                "/purchase-orders",
                                "/purchase-orders/",
                                "/purchase-orders/**",
                                "/admin/purchase-orders",
                                "/admin/purchase-orders/",
                                "/admin/purchase-orders/**"
                        ).permitAll()



                        .requestMatchers("/admin/dashboard/**")
                        .hasRole("ADMIN")

                        .requestMatchers(
                                "/admin/invoices",
                                "/admin/invoices/",
                                "/admin/invoices/**"
                        ).permitAll()

                        // 🚀 4. DELIVERIES MODULE LISTINGS & DROPDOWNS: Complete open bypass using wildcards BEFORE strict checks
                        .requestMatchers(
                                "/admin/deliveries",
                                "/admin/deliveries/",
                                "/admin/deliveries/**", // 👈 CRITICAL FIXED LINE: Unblocks flat list and subpaths globally
                                "/admin/deliveries/available-employees",
                                "/admin/deliveries/available-purchase-orders"
                        ).permitAll()

                        // 5. Secured Module Checkpoints (Strict Top-Down Order)
                        .requestMatchers("/admin/vendors/**").authenticated()
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")

                        // 6. Global Admin structural catch-all configuration
                        .requestMatchers("/vendor/**")
                        .hasRole("VENDOR")
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 7. Remaining fallback requests
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}