package com.example.FD.Aggregator.exceptions;


import com.example.FD.Aggregator.dto.ApiResponse;
import com.example.FD.Aggregator.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                new ApiResponse.SuccessResponse<>("auth201", "User Already Registered", null),
                new ApiResponse.ErrorResponse("ERR409", ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
//
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ApiResponse<Object>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
//        ApiResponse<Object> response = new ApiResponse<>(
//                new ApiResponse.SuccessResponse<>("auth201", "Data not found", null),
//                new ApiResponse.ErrorResponse("ERR404", ex.getMessage())
//        );
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ApiResponse<Object>> handleBadCredentialsException(BadCredentialsException ex) {
//        ApiResponse<Object> response = new ApiResponse<>(
//                new ApiResponse.SuccessResponse<>("auth201", "Invalid credentials", null),
//                new ApiResponse.ErrorResponse("ERR401", ex.getMessage())
//        );
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }
}
