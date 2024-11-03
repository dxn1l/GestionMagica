package com.example.GestionMagica.controllers;

import com.example.GestionMagica.Manager.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spells")
public class SpellController {

    @PostMapping("/cast")
    public ResponseEntity<String> castSpell(@RequestBody SpellRequest spellRequest, @RequestHeader("Session-Id") String sessionId) {
        String spellName = spellRequest.getSpell();
        String username = SessionManager.getUsername(sessionId);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
        String message = "Usuario " + username + " ha lanzado el hechizo : " + spellName;
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