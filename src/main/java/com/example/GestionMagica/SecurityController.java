package com.example.GestionMagica;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Esto debe coincidir con el nombre de tu archivo HTML de login sin la extensión
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Esto debe coincidir con el nombre de tu archivo HTML de home sin la extensión
    }
}