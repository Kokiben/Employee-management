package com.myFullstackkaoutar.Employee_management.controllers;


import com.myFullstackkaoutar.Employee_management.Services.AuthService;
import com.myFullstackkaoutar.Employee_management.dtos.LoginRequest;
import com.myFullstackkaoutar.Employee_management.dtos.SignupRequest;
import com.myFullstackkaoutar.Employee_management.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<String>> login(
            @Valid @RequestBody LoginRequest loginRequest) {

        String token = authService.login(loginRequest);

        return new ResponseEntity<>(new GlobalResponse<>(token), HttpStatus.CREATED);
    }

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<String>> signup(
            @Valid @RequestBody SignupRequest signupRequest, @RequestParam String token) {


        authService.signup(signupRequest, token);

        return new ResponseEntity<>(new GlobalResponse<>("Signed Up"), HttpStatus.CREATED);
    }
}
