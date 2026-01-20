
-- =========================
-- Tabla Usuarios
-- =========================
CREATE TABLE usuarios (
                          id BIGSERIAL PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          apellido VARCHAR(100) NOT NULL,
                          correo VARCHAR(150) NOT NULL UNIQUE,
                          celular VARCHAR(20),
                          clave VARCHAR(255) NOT NULL,
                          rol VARCHAR(50) NOT NULL
);

-- =========================
-- Tabla Habitaciones
-- =========================
CREATE TABLE habitaciones (
                              id BIGSERIAL PRIMARY KEY,
                              numero INT NOT NULL UNIQUE,
                              tipo_habitacion VARCHAR(50) NOT NULL,
                              precio_noche DECIMAL(10,2) NOT NULL,
                              capacidad INT NOT NULL,
                              descripcion VARCHAR(255)
);

-- =========================
-- Tabla Estados_habitaciones
-- =========================

CREATE TABLE estados_habitaciones (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    nombre VARCHAR(45) NOT NULL,
    descripcion VARCHAR(255)
);

-- =========================
-- Tabla Estados_de_las_habitaciones
-- =========================

CREATE TABLE estados_de_las_habitaciones (
    id BIGSERIAL PRIMARY KEY NOT NULL ,
    id_estado_habitacion BIGINT REFERENCES estados_habitaciones(id),
    id_habitacion BIGINT REFERENCES habitaciones(id),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE
);

-- =========================
-- Tabla Estados_reservas
-- =========================
CREATE TABLE estados_reservas (
                                  id BIGSERIAL PRIMARY KEY,
                                  nombre VARCHAR(50) NOT NULL,
                                  descripcion VARCHAR(255)
);

-- =========================
-- Tabla Reservas
-- =========================
CREATE TABLE reservas (
                          id BIGSERIAL PRIMARY KEY,
                          fecha_llegada TIMESTAMP NOT NULL,
                          fecha_salida TIMESTAMP NOT NULL,
                          id_usuario BIGINT NOT NULL,
                          id_habitacion BIGINT NOT NULL,

                          CONSTRAINT fk_reserva_usuario
                              FOREIGN KEY (id_usuario)
                                  REFERENCES usuarios (id)
                                  ON DELETE RESTRICT
                                  ON UPDATE RESTRICT,

                          CONSTRAINT fk_reserva_habitacion
                              FOREIGN KEY (id_habitacion)
                                  REFERENCES habitaciones (id)
                                  ON DELETE RESTRICT
                                  ON UPDATE RESTRICT
);

-- =========================
-- Tabla Pagos
-- =========================
CREATE TABLE pagos (
                       id BIGSERIAL PRIMARY KEY,
                       fecha_pago TIMESTAMP NOT NULL,
                       monto DECIMAL(10,2) NOT NULL,
                       metodo_pago VARCHAR(50) NOT NULL,
                       estado_pago VARCHAR(50) NOT NULL,
                       id_reserva BIGINT NOT NULL,

                       CONSTRAINT fk_pago_reserva
                           FOREIGN KEY (id_reserva)
                               REFERENCES reservas (id)
                               ON DELETE RESTRICT
                               ON UPDATE RESTRICT
);

-- =========================
-- Tabla Fotos
-- =========================
CREATE TABLE fotos (
                       id BIGSERIAL PRIMARY KEY,
                       ruta VARCHAR(255) NOT NULL,
                       fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       id_habitacion BIGINT NOT NULL,

                       CONSTRAINT fk_foto_habitacion
                           FOREIGN KEY (id_habitacion)
                               REFERENCES habitaciones (id)
                               ON DELETE RESTRICT
                               ON UPDATE RESTRICT
);

-- =========================
-- Tabla EstadosDeLasReservas
-- =========================
CREATE TABLE estados_de_las_reservas (
                                         id_reserva BIGINT NOT NULL,
                                         id_estado_reserva BIGINT NOT NULL,
                                         fecha_inicio TIMESTAMP NOT NULL,
                                         fecha_fin TIMESTAMP,

                                         PRIMARY KEY (id_reserva, id_estado_reserva),

                                         CONSTRAINT fk_estado_reserva_reserva
                                             FOREIGN KEY (id_reserva)
                                                 REFERENCES reservas (id)
                                                 ON DELETE NO ACTION
                                                 ON UPDATE NO ACTION,

                                         CONSTRAINT fk_estado_reserva_estado
                                             FOREIGN KEY (id_estado_reserva)
                                                 REFERENCES estados_reservas (id)
                                                 ON DELETE NO ACTION
                                                 ON UPDATE NO ACTION
);


-- Creando inserciones iniciales

-- Insertando estados de habitaciones

INSERT INTO estados_habitaciones (nombre, descripcion)  VALUES ('Disponible','Habitacion disponible para reservar'),
                                                               ('Ocupada','Habitacion ocupada por inquilinos'),
                                                               ('En mantenimiento','Habitacion en mantenimiento'),
                                                               ('Reservada','Habitacion reservada por un cliente.');
-- Insertando estados de reservas
INSERT INTO estados_reservas (nombre,descripcion) VALUES ('Completada','El cliente ya fue al hotel'),
                                                         ('Cancelada','El cliente cancelo la reservacion'),
                                                         ('Confirmada','El cliente crea la reserva correctamente');