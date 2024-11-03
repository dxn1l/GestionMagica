package com.example.GestionMagica.AOP;

import com.example.GestionMagica.Entitys.User;
import com.example.GestionMagica.Repository.UserRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {

    @Autowired
    private UserRepository userRepository;

    @Before("execution(* com.example.GestionMagica.controllers.AuthController.login(..)) && args(user,..)")
    public void beforeLogin(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser == null || !foundUser.getPassword().equals(user.getPassword())) {
            throw new SecurityException("B : Usuario no autenticado");
        } else {
            System.out.println("B : Usuario autenticado: " + user.getUsername());
        }
    }

    @After("execution(* com.example.GestionMagica.controllers.AuthController.login(..)) && args(user,..)")
    public void afterLogin(User user) {
        System.out.println("Af : Login exitoso del usuario: " + user.getUsername());
    }

    @Around("execution(* com.example.GestionMagica.controllers.AuthController.login(..)) && args(user,..)")
    public Object aroundLogin(ProceedingJoinPoint joinPoint, User user) throws Throwable {
        System.out.println("Ar : Intentando login para el usuario: " + user.getUsername());
        Object result = joinPoint.proceed();
        System.out.println("Ar : Login completado para el usuario: " + user.getUsername());
        return result;
    }
}