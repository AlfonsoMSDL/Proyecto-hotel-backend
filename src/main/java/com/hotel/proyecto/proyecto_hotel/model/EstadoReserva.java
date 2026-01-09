package com.hotel.proyecto.proyecto_hotel.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "estados_reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstadoReserva implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

}
