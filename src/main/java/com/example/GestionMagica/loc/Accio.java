package com.example.GestionMagica.loc;


import org.springframework.stereotype.Component;

@Component
public class Accio implements Spell {
    @Override
    public void cast() {
        System.out.println("Accio!");
    }
}
