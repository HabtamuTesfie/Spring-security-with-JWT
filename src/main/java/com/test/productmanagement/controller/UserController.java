package com.test.productmanagement.controller;

import com.test.productmanagement.security.payload.request.LoginRequest;
import com.test.productmanagement.security.payload.request.SignupRequest;
import com.test.productmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        return userService.registerUser(signUpRequest);
    }
}
