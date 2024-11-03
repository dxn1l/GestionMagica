package com.example.GestionMagica.AOP;

import com.example.GestionMagica.Manager.SessionManager;
import com.example.GestionMagica.controllers.SpellController.SpellRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Component
public class SpellAspect {

    @Pointcut("execution(* com.example.GestionMagica.controllers.SpellController.castSpell(..)) && args(spellRequest, sessionId,..)")
    public void castSpellPointcut(SpellRequest spellRequest, String sessionId) {}

    @Before("castSpellPointcut(spellRequest, sessionId)")
    public void beforeCastSpell(SpellRequest spellRequest, String sessionId) {
        String username = SessionManager.getUsername(sessionId);
        System.out.println("B : Casteando hechizo: " + spellRequest.getSpell() + " por parte del usuario: " + username);
    }

    @After("castSpellPointcut(spellRequest, sessionId)")
    public void afterCastSpell(SpellRequest spellRequest, String sessionId) {
        String username = SessionManager.getUsername(sessionId);
        System.out.println("Af : Hechizo lanzado con éxito: " + spellRequest.getSpell() + " por parte del usuario: " + username);
    }

    @Around("castSpellPointcut(spellRequest, sessionId)")
    public Object aroundCastSpell(ProceedingJoinPoint joinPoint, SpellRequest spellRequest, String sessionId) throws Throwable {
        String username = SessionManager.getUsername(sessionId);
        if (username == null) {
            System.out.println("\n" + "Inicie sesión");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
        System.out.println("\n" + "Ar : Inicio de hechizo: " + spellRequest.getSpell() + " por parte del usuario: " + username);
        Object result = joinPoint.proceed();
        System.out.println("Ar : Finalización de hechizo: " + spellRequest.getSpell() + " por parte del usuario: " + username + "\n");
        return result;
    }
}