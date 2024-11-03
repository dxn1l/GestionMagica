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
public class RegistrationAspect {

    @Autowired
    private UserRepository userRepository;

    @Before("execution(* com.example.GestionMagica.controllers.AuthController.register(..)) && args(user,..)")
    public void beforeRegister(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null) {
            throw new SecurityException("B :Usuario ya existe");
        } else {
            System.out.println("B : Usuario no existe, procediendo con el registro del usuario: " + user.getUsername());
        }
    }

    @After("execution(* com.example.GestionMagica.controllers.AuthController.register(..)) && args(user,..)")
    public void afterRegister(User user) {
        System.out.println("Af : Registro exitoso del usuario: " + user.getUsername());
    }

    @Around("execution(* com.example.GestionMagica.controllers.AuthController.register(..)) && args(user,..)")
    public Object aroundRegister(ProceedingJoinPoint joinPoint, User user) throws Throwable {
        System.out.println("Ar : Inicio de registro para el usuario: " + user.getUsername());
        Object result = joinPoint.proceed();
        System.out.println("Ar : Finalización de registro para el usuario: " + user.getUsername());
        return result;
    }
}