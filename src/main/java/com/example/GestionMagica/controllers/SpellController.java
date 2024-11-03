package com.example.GestionMagica.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spells")
public class SpellController {

    @PostMapping("/cast")
    public ResponseEntity<String> castSpell(@RequestBody SpellRequest spellRequest) {
        String spellName = spellRequest.getSpell();
        return ResponseEntity.ok("Hechizo " + spellName + " lanzado con éxito.");
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