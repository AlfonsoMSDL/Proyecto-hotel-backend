# Backend – Sistema de Gestión Hotelera
Backend de un **sistema de gestión hotelera**, desarrollado como proyecto personal, enfocado en aplicar conceptos de ingeniería de software, modelado del dominio y buenas prácticas en el desarrollo backend.

El proyecto se encuentra en **desarrollo activo** y busca cubrir de forma integral los procesos principales de un hotel: gestión de usuarios, habitaciones, reservas y pagos.

---
## 📌 Enfoque del proyecto
Antes de implementar el backend, se realizó un análisis del dominio del negocio, entendiendo cómo funciona un hotel en términos de:

- Usuarios y roles
- Habitaciones y su disponibilidad
- Reservas y manejo de fechas
- Estados de habitaciones y reservas
- Pagos asociados a una reserva

A partir de este análisis se diseñaron:

- Modelo conceptual
- Modelo lógico de la base de datos
- Diagrama de clases

Estos modelos sirven como base para el desarrollo del sistema y ayudan a mantener coherencia entre el negocio, la base de datos y el código.

---
## 🧱 Arquitectura general

- API REST desarrollada con Spring Boot
- Persistencia con Spring Data JPA
- Base de datos relacional PostgreSQL
- Documentación automática de endpoints con Swagger
- Contenerización del backend y la base de datos con Docker y Docker Compose

---
## 🔧 Tecnologías utilizadas

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Docker Compose
- Swagger / OpenAPI

---
## ⚙️ Funcionalidades implementadas
### 👤 Usuarios

- Registro de usuarios

- Inicio de sesión con correo y clave

- Manejo de roles (cliente / admin)

### 🏨 Habitaciones

- Creación y gestión de habitaciones

- Consulta de habitaciones

- Filtrado por tipo y precio

- Asociación de múltiples fotos a una habitación

- Manejo de estados de la habitación

### 📄 Documentación

- Documentación de la API disponible mediante **Swagger**

---
## 🧩 Funcionalidades en desarrollo

- ### Módulo de reservas

  - Asociación entre usuario y habitación
  - Manejo de fechas (llegada y salida)
  - Lógica de disponibilidad
  - Estados de la reserva (creada, confirmada, cancelada, etc.)

---
## 🚀 Próximas funcionalidades planeadas

- Desarrollo del frontend con **React**
- Integración completa frontend–backend
- Implementación de una pasarela de pagos
- Despliegue del sistema en **AWS**

---
## ▶️ Ejecución del proyecto

- Clonar el repositorio:
`git clone https://github.com/AlfonsoMSDL/Proyecto-hotel-backend.git`
- Moverse a la carpeta del proyecto:
`cd Proyecto-hotel-backend`
- Ejecutar con Docker compose:
`docker compose up --build`

---
## 📑 Documentación de la API
Una vez el proyecto esté en ejecución, la documentación de los endpoints está disponible en:

`http://localhost:8181/hotel/api/swagger-ui.html`

---
## 📌 Estado del proyecto

**🚧 En desarrollo**

El proyecto continúa evolucionando, incorporando nuevas funcionalidades y mejoras conforme avanza su implementación.