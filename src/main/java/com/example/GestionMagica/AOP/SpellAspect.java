package com.example.GestionMagica.AOP;

import com.example.GestionMagica.controllers.SpellController.SpellRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpellAspect {

    @Before("execution(* com.example.GestionMagica.controllers.SpellController.castSpell(..)) && args(spellRequest,..)")
    public void beforeCastSpell(SpellRequest spellRequest) {
        System.out.println("B : Castear hechizo: " + spellRequest.getSpell());
    }

    @After("execution(* com.example.GestionMagica.controllers.SpellController.castSpell(..)) && args(spellRequest,..)")
    public void afterCastSpell(SpellRequest spellRequest) {
        System.out.println("Af : Hechizo lanzado con éxito: " + spellRequest.getSpell());
    }

    @Around("execution(* com.example.GestionMagica.controllers.SpellController.castSpell(..)) && args(spellRequest,..)")
    public Object aroundCastSpell(ProceedingJoinPoint joinPoint, SpellRequest spellRequest) throws Throwable {
        System.out.println("Ar : Inicio de hechizo: " + spellRequest.getSpell());
        Object result = joinPoint.proceed();
        System.out.println("Ar : Finalización de hechizo: " + spellRequest.getSpell());
        return result;
    }
}