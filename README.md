# Gestión Mágica

## Integrantes

- Daniel Andrés Moreno Rey.
- Rodrigo Rojas Redondo.
- Arturo Lopez Castaño.
- Roberto Quilez.

## Link del repositorio

[https://github.com/erickgutierrez/GestionMagica](https://github.com/dxn1l/GestionMagica)

## Descripción

Gestión Mágica es una aplicación Spring Boot que permite a los usuarios registrarse, iniciar sesión y lanzar hechizos según sus roles. Los usuarios pueden ser Aurores o Estudiantes, con diferentes permisos para lanzar hechizos.

## Estructura del proyecto

El proyecto está estructurado de la siguiente manera:

- **BackEnd**

    - **AOP**:
        - `LogingAspect.java` - Aspecto para manejar el inicio de sesión, incluyendo validaciones y roles.
        - `RegistrationAspect.java` - Aspecto para manejar el registro de usuarios, incluyendo validaciones y roles.
        - `SpellAspect.java` - Aspecto para manejar el lanzamiento de hechizos, incluyendo validaciones y registros.

    - **controllers**:
        - `AuthController.java` - Controlador REST para la autenticación de usuarios, incluyendo login, registro y logout.
        - `SpellController.java` - Controlador REST para manejar el lanzamiento de hechizos.

    - **Entities**:
        - `User.java` - Entidad JPA que representa a un usuario en el sistema.

    - **loc**:
        - `Accio.java` - Implementación del hechizo Accio.
        - `Aguamenti.java` - Implementación del hechizo Aguamenti.
        - `Alohomora.java` - Implementación del hechizo Alohomora.
        - `Crucio.java` - Implementación del hechizo Crucio.
        - `Spell.java` - Interfaz que define el contrato para los hechizos.

    - **Repository**:
        - `UserRepository.java` - Repositorio JPA para manejar las operaciones de la entidad User.

    - `GestionMagicaApplication.java` - Clase principal de la aplicación Spring Boot.

- **FrontEnd**

    - `login.html` - Página HTML para el inicio de sesión y registro de usuarios.
    - `spell.html` - Página HTML para lanzar hechizos después de iniciar sesión.

- **Base de datos**

    - `docker-compose.yml` - Archivo de configuración de Docker Compose para levantar una base de datos MySQL.
    - `application.properties` - Archivo de configuración de Spring Boot para la conexión a la base de datos.

## Características

- Registro de usuarios con selección de roles (Auror o Estudiante)
- Inicio de sesión y gestión de sesiones
- Control de acceso basado en roles para lanzar hechizos
- Programación orientada a aspectos (AOP) para registro y comprobaciones de seguridad

## Tecnologías Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- AspectJ
- Maven
- HTML, CSS, JavaScript

## Ejecución

Para ejecutar la aplicación se debe hacer lo siguiente:

1. Se requiere tener instalado Docker y Maven en el sistema.
2. Clonar el repositorio en la máquina local.
3. Ejecutar la aplicación.
4. Acceder a la URL [http://localhost:8080/login.html](http://localhost:8080/login.html) en un navegador web o usar el link que se encuentra en el terminal tras ejecutar la aplicación.