package com.example.GestionMagica.controllers;

import com.example.GestionMagica.Entitys.User;
import com.example.GestionMagica.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        boolean authenticated = authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (authenticated) {
            return ResponseEntity.ok("Login exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User newUser) {
        boolean success = authenticationService.registerUser(newUser);
        if (success) {
            return ResponseEntity.ok("Registro exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el registro: usuario ya existe");
        }
    }
}
