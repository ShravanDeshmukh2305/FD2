package com.example.FD.Aggregator.service;

import com.example.FD.Aggregator.dto.LoginRequest;
import com.example.FD.Aggregator.dto.RegisterUserRequest;
import com.example.FD.Aggregator.entity.User;
import com.example.FD.Aggregator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    public void registerUser(RegisterUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);
        userRepository.save(user);
    }

    public String loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // Generate a unique session token and store it in Redis
        String sessionToken = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(sessionToken, user.getEmail(), Duration.ofMinutes(30));  // Session expires in 30 mins

        return sessionToken;
    }

    public User getUserBySessionToken(String sessionToken) {
        String email = redisTemplate.opsForValue().get(sessionToken);
        if (email == null) {
            throw new SecurityException("Invalid session or session expired");
        }
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

