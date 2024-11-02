package com.example.GestionMagica.services;

import com.example.GestionMagica.Entitys.User;
import com.example.GestionMagica.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public boolean registerUser(User newUser) {
        if (userRepository.findByUsername(newUser.getUsername()) != null) {
            return false; // El usuario ya existe
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword())); // Cifrar la contraseña
        userRepository.save(newUser);
        return true; // Registro exitoso
    }
}
