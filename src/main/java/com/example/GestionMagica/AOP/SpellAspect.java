package com.example.GestionMagica.AOP;

import com.example.GestionMagica.Manager.SessionManager;
import com.example.GestionMagica.Repository.UserRepository;
import com.example.GestionMagica.controllers.SpellController.SpellRequest;
import com.example.GestionMagica.Entities.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Component
public class SpellAspect {

    @Autowired
    private UserRepository userRepository;

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
            System.out.println("Inicie sesión");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        if ("estudiante".equals(user.getRole()) && ("crucio".equals(spellRequest.getSpell()) || "alohomora".equals(spellRequest.getSpell()))) {
            System.out.println("Ar : El usuario " + username + " no puede lanzar el hechizo " + spellRequest.getSpell() + "\n");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Estudiantes no pueden usar este hechizo");
        }

        System.out.println("\n" + "Ar : Inicio de hechizo: " + spellRequest.getSpell() + " por parte del usuario: " + username);
        Object result = joinPoint.proceed();
        System.out.println("Ar : Finalización de hechizo: " + spellRequest.getSpell() + " por parte del usuario: " + username + "\n");
        return result;
    }
}