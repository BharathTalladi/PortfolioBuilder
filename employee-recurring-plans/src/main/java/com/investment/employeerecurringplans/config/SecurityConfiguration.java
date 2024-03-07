package com.investment.employeerecurringplans.config;

import com.investment.employeerecurringplans.entity.Role;
import com.investment.employeerecurringplans.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    private final UserService userService;
    // Defining a custom security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    // Configuring CORS (Cross-Origin Resource Sharing)
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(List.of("Authorization"));
                    config.setMaxAge(3600L);
                    return config;
                }))
                        // Disable CSRF (Cross-Site Request Forgery) protection
                .csrf(AbstractHttpConfigurer::disable)
                        // Configure URL authorization rules
                    .authorizeHttpRequests(request ->
                        request.requestMatchers("/contributions/register","/contributions/login").permitAll()// Allow registration and login endpoints
                                .requestMatchers("/contributions/createUserPlan",
                                        "contributions/getUserPlanById/**","/contributions/editUserContributions/**").hasAuthority(Role.USER.name())// Require USER role for accessing endpoints
                                .requestMatchers("/contributions/createUserPlanByEmployer",
                                                 "/contributions/getAllUsersPlan","/contributions/editEmployerContributions/**").hasAuthority(Role.ADMIN.name())// Require ADMIN role for accessing endpoints
                                .anyRequest()
                                .authenticated() // Requires authentication for all other endpoints
                )
                        // Configure session management to be STATELESS (no sessions)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        // Configure authentication provider
                .authenticationProvider(authenticationProvider())
                        // Add JWT filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    // Configuring password encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Configure authentication provider using DAO
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    // Configure authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
