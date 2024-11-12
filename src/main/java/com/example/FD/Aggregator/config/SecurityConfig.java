package com.example.FD.Aggregator.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/mobile/register", "/api/mobile/verify" , "/api/mobile/user-details", "/api/mobile/user-details-update", "/api/mobile/user-by-email" , "api/mobile/user-by-mobile")) // Ignore CSRF for these endpoints
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/mobile/register", "/api/mobile/verify", "/api/mobile/user-details", "/api/mobile/user-details-update", "/api/mobile/user-by-email" , "api/mobile/user-by-mobile").permitAll() // Allow registration and verification without authentication
//                        .anyRequest().authenticated() // Require authentication for any other request
//                )
//                .formLogin(login -> login
//                        .permitAll() // Allow all users to see the login form
//                )
//                .logout(logout -> logout.permitAll()) // Allow all users to logout
//                .exceptionHandling(eh -> eh.accessDeniedPage("/403")); // Custom access denied page
//
//        return http.build();
//    }
//}


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        "/api/mobile/register",
                        "/api/mobile/verify",
                        "/api/mobile/user-details",
                        "/api/mobile/user-details-update",
                        "/api/mobile/user-by-email",
                        "/api/mobile/user-by-mobile",
                       "/api/email/generate",
                        "/api/email/verify",
                        "/api/mpin"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/mobile/register",
                                "/api/mobile/verify",
                                "/api/mobile/user-details",
                                "/api/mobile/user-details-update",
                                "/api/mobile/user-by-email",
                                "/api/mobile/user-by-mobile",
                               "/api/email/generate",
                                "/api/email/verify",
                                "/api/mpin").permitAll() // Allow registration and verification without authentication
                        .anyRequest().authenticated() // Require authentication for any other request
                )
                .formLogin(login -> login
                        .permitAll() // Allow all users to see the login form
                )
                .logout(logout -> logout.permitAll()) // Allow all users to logout
                .exceptionHandling(eh -> eh.accessDeniedPage("/403")); // Custom access denied page

        return http.build();
    }
}

