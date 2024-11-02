package com.example.FD.Aggregator.controller;

import com.example.FD.Aggregator.dto.LoginRequest;
import com.example.FD.Aggregator.dto.RegisterUserRequest;
import com.example.FD.Aggregator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String sessionToken = userService.loginUser(request);
        return ResponseEntity.ok(sessionToken);
    }
}

//
//package com.example.FD.Aggregator.controller;
//
//import com.example.FD.Aggregator.dto.LoginRequest;
//import com.example.FD.Aggregator.dto.RegisterUserRequest;
//import com.example.FD.Aggregator.service.UserService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final UserService userService;
//
//    @Operation(summary = "Register a new user", description = "Registers a user with the provided details")
//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody RegisterUserRequest request) {
//        userService.registerUser(request);
//        return ResponseEntity.ok("User registered successfully");
//    }
//
//    @Operation(summary = "User login", description = "Logs in a user and returns a session token")
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
//        String sessionToken = userService.loginUser(request);
//        return ResponseEntity.ok(sessionToken);
//    }
//}
