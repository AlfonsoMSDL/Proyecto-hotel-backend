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
### 👤 Usuarios y autenticación

- Registro de usuarios con rol de **cliente**
- Inicio de sesión mediante correo y contraseña
- Manejo de roles (cliente / admin)
- Gestión de clientes por parte del administrador

### 🏨 Habitaciones

- Creación de nuevas habitaciones por parte del administrador
- Edición de la información de las habitaciones
- Eliminación de habitaciones junto con toda su información asociada
- Consulta de habitaciones disponibles en el hotel
- Filtrado de habitaciones por tipo y precio
- Filtrado de habitaciones por disponibilidad según fecha de inicio y fin
- Asociación de múltiples imágenes a una habitación
- Manejo de estados de la habitación (disponible, ocupada, en mantenimiento)
- Actualización automática del estado de la habitación al marcar la entrada de un cliente

### 📅 Reservas

- Creación de reservas por parte del cliente para un rango de fechas válido
- Validación de fechas para garantizar que no se crucen reservas existentes
- Visualización de un calendario de ocupación por habitación, bloqueando fechas no disponibles
- Búsqueda de reservas por:
  - Nombre del cliente
  - Nombre del cliente y habitación reservada
- Cancelación de reservas por parte del cliente
- Manejo de estados de la reserva:
  - Confirmada
  - Cancelada
  - Completada
- Marcado de entrada del cliente (actualiza el estado de la habitación a ocupada)
- Marcado de salida del cliente y actualización de la reserva a **Completada**
- Visualización del historial de reservas del cliente (confirmadas, canceladas y completadas)

### 📄 Documentación

- Documentación de la API disponible mediante **Swagger**

## 🚀 Próximas funcionalidades planeadas

- Desarrollo del frontend con **React**
- Integración completa frontend–backend
- Implementación de una pasarela de pagos
- Seguridad avanzada (JWT, control de accesos por rol)
- Despliegue del sistema en **AWS**
- Manejo de notificaciones (correo o sistema interno)

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

`http://localhost:8181/hotel/api/swagger-ui/index.html`

---
## 📌 Estado del proyecto

**🚧 En desarrollo**

El proyecto continúa evolucionando, incorporando nuevas funcionalidades y mejoras conforme avanza su implementación.