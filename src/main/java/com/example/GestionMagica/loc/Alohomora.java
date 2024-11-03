package com.example.GestionMagica.loc;


import org.springframework.stereotype.Component;

@Component
public class Alohomora implements Spell {
    @Override
    public void cast() {
        System.out.println("Alohomora!");
    }
}
