package com.example.GestionMagica.controllers;

import com.example.GestionMagica.Manager.SessionManager;
import com.example.GestionMagica.Repository.UserRepository;
import com.example.GestionMagica.Entities.User;
import com.example.GestionMagica.loc.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spells")
public class SpellController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/cast")
    public ResponseEntity<String> castSpell(@RequestBody SpellRequest spellRequest, @RequestHeader("Session-Id") String sessionId) {
        String username = SessionManager.getUsername(sessionId);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        if ("estudiante".equals(user.getRole()) && ("crucio".equals(spellRequest.getSpell()) || "alohomora".equals(spellRequest.getSpell()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Estudiantes no pueden usar este hechizo");
        }

        Spell spell = (Spell) context.getBean(spellRequest.getSpell().toLowerCase());
        String message = "Usuario " + username + " ha lanzado el hechizo: " + spellRequest.getSpell();
        spell.cast();
        return ResponseEntity.ok(message);
    }

    public static class SpellRequest {
        private String spell;

        public String getSpell() {
            return spell;
        }

        public void setSpell(String spell) {
            this.spell = spell;
        }
    }
}