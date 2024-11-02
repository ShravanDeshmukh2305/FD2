package com.example.FD.Aggregator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/register", "/api/auth/login"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
//                        .loginPage("/api/auth/login")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(eh -> eh.accessDeniedPage("/403"));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}






//
//package com.example.FD.Aggregator.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.Customizer;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
//////                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/register", "/api/auth/login")) // Disable CSRF for these endpoints
//////                .authorizeHttpRequests(auth -> auth
//////                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll() // Allow unauthenticated access to auth endpoints
//////                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Allow access to Swagger UI
//////                        .anyRequest().authenticated() // Require authentication for all other requests
//////                )
//////                .formLogin(login -> login
//////                        .permitAll() // Allow everyone to access the login page
//////                )
//////                .logout(logout -> logout.permitAll())
//////                .exceptionHandling(eh -> eh.accessDeniedPage("/403")); // Custom access denied page
////
////                    .authorizeHttpRequests((authorize) -> authorize
////                            .requestMatchers("/api/auth/**").permitAll()
////                            .anyRequest().authenticated()
////                    )
////                    // Form login handles the redirect to the login page from the
////                    // authorization server filter chain
////                    .formLogin(Customizer.withDefaults());
////
////            return http.build();
////
////    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // Password encoder
//    }
//}
