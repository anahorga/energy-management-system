package com.example.authenticationservice.controller;


import com.example.authenticationservice.dto.RegisterRequest;
import com.example.authenticationservice.service.InvalidUserException;
import com.example.authenticationservice.service.UserAlreadyExistException;
import com.example.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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


}
