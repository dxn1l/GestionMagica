package com.example.GestionMagica.loc;


import org.springframework.stereotype.Component;

@Component
public class Crucio implements Spell {
    @Override
    public void cast() {
        System.out.println("Crucio!");
    }
}
