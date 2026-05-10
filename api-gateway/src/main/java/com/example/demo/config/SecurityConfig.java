package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;

import com.example.demo.filter.JwtAuthFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(cors -> {})
            .authorizeExchange(ex -> ex

            		.pathMatchers(
            			    "/swagger-ui.html",
            			    "/swagger-ui/**",
            			    "/v3/api-docs/**",
            			    "/webjars/**",
            			    "/auth-service/**"
            			).permitAll()

            			.pathMatchers(HttpMethod.POST, "/user-service/users").permitAll()


                .pathMatchers("/department-service/**").hasRole("OWNER")
                .pathMatchers("/report-service/**").hasRole("OWNER")
                .pathMatchers("/user-service/users/staff/**").hasRole("OWNER")


                .pathMatchers(HttpMethod.GET, "/room-service/**")
                    .hasAnyRole("OWNER", "MANAGER", "RECEPTIONIST")

                .pathMatchers(HttpMethod.POST, "/room-service/**")
                    .hasAnyRole("OWNER", "MANAGER")

                .pathMatchers(HttpMethod.PUT, "/room-service/**")
                    .hasAnyRole("OWNER", "MANAGER")

                .pathMatchers(HttpMethod.DELETE, "/room-service/**")
                .hasAnyRole("OWNER", "MANAGER")
 

                .pathMatchers("/booking-service/**")
                    .hasAnyRole("OWNER", "MANAGER", "RECEPTIONIST")
                    

                    .pathMatchers("/payment-service/**")
                    .hasAnyRole("OWNER", "MANAGER", "RECEPTIONIST")

                .pathMatchers(HttpMethod.GET, "/user-service/users/**")
                    .hasAnyRole("OWNER", "RECEPTIONIST")

                .pathMatchers(HttpMethod.POST, "/user-service/users/**")
                    .hasAnyRole("OWNER", "RECEPTIONIST")

                .pathMatchers(HttpMethod.PUT, "/user-service/users/**")
                    .hasAnyRole("OWNER", "RECEPTIONIST")

                    
                .pathMatchers(HttpMethod.DELETE, "/user-service/users/**")
                    .hasRole("OWNER")


                .anyExchange().authenticated()
            )

            .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
    }
}