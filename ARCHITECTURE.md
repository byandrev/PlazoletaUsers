# Arquitectura Hexagonal del Proyecto

Este documento explica la estructura y el flujo de la aplicación basándose en los principios de la Arquitectura Hexagonal (o Puertos y Adaptadores). El objetivo principal es desacoplar la lógica de negocio (Dominio) de los detalles de implementación (Infraestructura, UI, Base de Datos).

## Estructura del Proyecto

El código está organizado en tres capas principales dentro de `src/main/java/com/pragma/powerup`:

1.  **Domain (`domain`)**: El núcleo de la aplicación. Contiene la lógica de negocio pura.
2.  **Application (`application`)**: Orquesta el flujo de datos entre la infraestructura y el dominio.
3.  **Infrastructure (`infrastructure`)**: Implementa los detalles técnicos (Frameworks, Bases de Datos, APIs externas).

---

## 1. Capa de Dominio (`domain`)

Es la capa más interna y no depende de ninguna otra capa (ni de Spring Boot, ni de JPA). Aquí residen las reglas de negocio.

*   **Model (`domain.model`)**: Objetos de negocio puros.
    *   Ejemplo: `UserModel` (representa un usuario con sus atributos y validaciones básicas).
*   **API (`domain.api`)**: Interfaces que definen los casos de uso (Input Ports). Son los puntos de entrada a la lógica de negocio.
    *   Ejemplo: `IUserServicePort` (define métodos como `saveUser`, `getAllUsers`).
*   **SPI (`domain.spi`)**: Interfaces que definen el acceso a datos o servicios externos (Output Ports). El dominio dice *qué* necesita, pero no *cómo* se hace.
    *   Ejemplo: `IUserPersistencePort` (define métodos para guardar y recuperar usuarios).
*   **UseCase (`domain.usecase`)**: Implementación de la lógica de negocio (implementa `domain.api`). Utiliza los puertos de salida (`domain.spi`) para interactuar con el exterior.
    *   Ejemplo: `UserUseCase` (valida reglas de negocio, como la mayoría de edad, y llama a `IUserPersistencePort`).

## 2. Capa de Aplicación (`application`)

Actúa como intermediario. Transforma los datos que vienen de fuera (DTOs) a modelos de dominio y viceversa.

*   **DTO (`application.dto`)**: Objetos de Transferencia de Datos. Estructuras simples para recibir y enviar datos a través de la API.
    *   Ejemplo: `UserRequestDto` (datos recibidos al crear un usuario), `UserResponseDto`.
*   **Handler (`application.handler`)**: Orquestadores. Reciben la petición del controlador, convierten DTOs a Modelos (usando Mappers) y llaman al Caso de Uso (`domain.api`).
    *   Ejemplo: `UserHandler` (usa `IUserRequestMapper` para convertir `UserRequestDto` a `UserModel` y llama a `IUserServicePort`).
*   **Mapper (`application.mapper`)**: Conversores entre DTOs y Modelos.

## 3. Capa de Infraestructura (`infrastructure`)

Contiene la implementación técnica. Aquí es donde "enchufamos" los adaptadores a los puertos del dominio.

*   **Input Adapters (`infrastructure.input`)**: Puntos de entrada a la aplicación (ej. Controladores REST).
    *   Ejemplo: `UserRestController` (recibe peticiones HTTP, valida el cuerpo y llama al `IUserHandler`).
*   **Output Adapters (`infrastructure.out`)**: Implementaciones de los puertos de salida (`domain.spi`).
    *   Ejemplo: `UserJpaAdapter` (implementa `IUserPersistencePort` usando JPA/Hibernate para guardar en base de datos).
*   **Configuration (`infrastructure.configuration`)**: Configuración de Beans de Spring. Aquí se inyectan las dependencias, uniendo los puertos con sus adaptadores.

---

## Flujo de una Petición (Ejemplo: Crear Usuario)

1.  **Petición HTTP**: El cliente envía un `POST /api/v1/users/` con un JSON.
2.  **Controller (Infraestructura)**: `UserRestController` recibe la petición y el JSON se mapea a `UserRequestDto`.
3.  **Handler (Aplicación)**: El controlador llama a `userHandler.saveUser(userRequestDto)`.
    *   `UserHandler` convierte `UserRequestDto` a `UserModel` usando un Mapper.
    *   Llama al puerto de entrada del dominio: `userServicePort.saveUser(userModel)`.
4.  **UseCase (Dominio)**: `UserUseCase` ejecuta la lógica de negocio.
    *   Verifica si el usuario es mayor de edad.
    *   Encripta la clave (usando `IPasswordEncoderPort`).
    *   Asigna el rol.
    *   Llama al puerto de salida: `userPersistencePort.saveUser(userModel)`.
5.  **Adapter (Infraestructura)**: `UserJpaAdapter` (que implementa `IUserPersistencePort`) recibe el `UserModel`.
    *   Convierte `UserModel` a `UserEntity` (entidad JPA).
    *   Usa `IUserRepository` (JPA Repository) para guardar en la base de datos.
6.  **Base de Datos**: Los datos se persisten.
