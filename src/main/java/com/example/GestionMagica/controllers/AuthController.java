package com.example.GestionMagica.controllers;

import com.example.GestionMagica.Manager.SessionManager;
import com.example.GestionMagica.Repository.UserRepository;
import com.example.GestionMagica.Entities.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            String sessionId = SessionManager.createSession(user.getUsername());
            return ResponseEntity.ok(sessionId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User newUser) {
        if (userRepository.findByUsername(newUser.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }

        // Encriptar la contraseña antes de guardarla
        String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashedPassword);
        userRepository.save(newUser);
        return ResponseEntity.ok("Usuario registrado con exito");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Session-Id") String sessionId) {
        SessionManager.invalidateSession(sessionId);
        return ResponseEntity.ok("Sesión invalidada con éxito");
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestHeader("Session-Id") String sessionId) {
        String username = SessionManager.getUsername(sessionId);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(user);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleSecurityException(SecurityException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}