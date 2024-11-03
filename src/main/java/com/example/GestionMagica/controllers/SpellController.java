package com.example.GestionMagica.controllers;

import com.example.GestionMagica.Manager.SessionManager;
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

    @PostMapping("/cast")
    public ResponseEntity<String> castSpell(@RequestBody SpellRequest spellRequest, @RequestHeader("Session-Id") String sessionId) {
        String username = SessionManager.getUsername(sessionId);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
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