package com.service.beauth.controller;

import com.service.beauth.dto.LoginRequest;
import com.service.beauth.dto.UserRequest;
import com.service.beauth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/verif")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.register(userRequest));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping(value = "/validateAccessToken")
    public ResponseEntity<Object> validateAccessToken(@RequestParam(value = "accessToken", defaultValue = "")String accessToken) {
        return ResponseEntity.ok(userService.validateAccessToken(accessToken));
    }

    @GetMapping(value = "/test")
    public ResponseEntity<Object> doTest() {
        return ResponseEntity.ok("Success Test");
    }
}
