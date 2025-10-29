package com.example.authenticationservice.controller;


import com.example.authenticationservice.dto.LoginRequest;
import com.example.authenticationservice.dto.RegisterRequest;
import com.example.authenticationservice.dto.UserDto;
import com.example.authenticationservice.security.JwtTokenService;
import com.example.authenticationservice.security.annotations.AllowAdmin;
import com.example.authenticationservice.service.exceptions.InvalidUserException;
import com.example.authenticationservice.service.exceptions.UserAlreadyExistException;
import com.example.authenticationservice.service.UserService;
import com.example.authenticationservice.service.exceptions.UserNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private  final JwtTokenService jwtTokenService;

    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest)
    {
        try {
            return ResponseEntity.ok(userService.register(registerRequest));
        }catch (InvalidUserException | UserAlreadyExistException e){
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/healthcheck")
    public ResponseEntity<?> checkHealth() {
        return ResponseEntity.status(200).build();
    }

    @PostMapping( "/login")
    @SneakyThrows
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        UserDto user= userService.login(request);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error","Invalid credentials"));
        }
        String jwt = jwtTokenService.createJwtToken(user.username(), user.userRole(), user.id());
        Cookie cookie = new Cookie("auth-cookie",jwt);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
        return ResponseEntity.ok(Map.of("token", jwt));
    }
    @GetMapping("/admin/{id}")
    @AllowAdmin
    public ResponseEntity<List<UserDto>> getRegisteredUsers(@PathVariable("id") Long adminId) {
        return ResponseEntity.ok(userService.getRegisteredUsers(adminId));
    }
    @DeleteMapping("/{id}")
    @AllowAdmin
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(id);
        }catch(UserNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
