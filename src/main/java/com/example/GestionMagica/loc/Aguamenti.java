package com.example.GestionMagica.loc;


import org.springframework.stereotype.Component;

@Component
public class Aguamenti implements Spell {
    @Override
    public void cast() {
        System.out.println("Aguamenti!");
    }
}
