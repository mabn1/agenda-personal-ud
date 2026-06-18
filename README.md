# Agenda Personal - Prueba Técnica OATI

## Descripción

Desarrollo de una API REST para la gestión de contactos personales, realizada como parte de la prueba técnica para el proceso de selección de la Oficina Asesora de Tecnologías e Información (OATI) de la Universidad Distrital Francisco José de Caldas.

La solución planteada permite modelar una agenda de contactos donde cada persona puede registrar múltiples números telefónicos y múltiples direcciones de correo electrónico, garantizando la persistencia de la información mediante una base de datos relacional.

---

## Alcance implementado

Durante el desarrollo se abordaron los siguientes aspectos solicitados en el enunciado:

* Uso de control de versiones mediante Git y GitHub.
* Definición de un modelo de datos relacional.
* Implementación de persistencia utilizando PostgreSQL.
* Desarrollo del proyecto utilizando Spring Boot y Spring Data JPA.
* Separación de responsabilidades mediante una estructura por capas.
* Modelado de relaciones uno a muchos entre personas, teléfonos y correos electrónicos.
* Configuración de la aplicación para la gestión de entidades mediante JPA/Hibernate.

---

## Modelo de datos

La solución se compone de tres entidades principales:

### DatosPersona

Contiene la información general del contacto:

* Nombre completo
* Número de documento
* Dirección

### Telefono

Permite registrar uno o varios números telefónicos asociados a una persona.

Incluye:

* Indicativo internacional
* Número telefónico

### Correo

Permite registrar una o varias direcciones de correo electrónico asociadas a una persona.

### Diagrama

El modelo de datos se encuentra disponible en:

`docs/modelo-datos.png`

---

## Stack tecnológico

* Java 17
* Spring Boot 3.5
* Spring Data JPA
* Maven
* PostgreSQL 17
* Hibernate

---

## Estructura del proyecto

src/main/java

* controller
* service
* repository
* entity
* dto
* exception

La estructura fue planteada siguiendo una organización por capas para facilitar la separación entre la lógica de negocio, el acceso a datos y la exposición de servicios REST.

---

## Base de datos

Motor utilizado:

PostgreSQL

Base de datos:

agenda_personal

Las tablas son generadas automáticamente mediante Hibernate a partir de las entidades JPA.

---

## Ejecución

1. Crear una base de datos llamada `agenda_personal`.
2. Configurar las credenciales de PostgreSQL en el archivo `application.properties`.
3. Ejecutar el proyecto mediante Maven o desde el IDE.

```bash
mvn spring-boot:run
```

---

## Consideraciones de diseño

Se optó por modelar la información principal alrededor de la entidad de contacto (DatosPersona), permitiendo asociar múltiples teléfonos y correos electrónicos a cada registro.

El número de documento se definió como único para evitar duplicidad de contactos y mantener la integridad de la información.

Adicionalmente, se incluyó un campo de indicativo telefónico con el fin de soportar números nacionales e internacionales.

---

## Evidencias

* Diagrama de modelo de datos: `docs/modelo-datos.png`
* Evidencia de creación de tablas: `docs/evidencia-tablas.png`

---

## Repositorio

La trazabilidad completa del desarrollo se encuentra disponible en este repositorio GitHub.
