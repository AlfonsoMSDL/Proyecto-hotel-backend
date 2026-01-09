package com.hotel.proyecto.proyecto_hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;


import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "fotos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Foto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String ruta;
    @Column(name = "fecha_creacion")
    @CreationTimestamp
    private Timestamp fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitacion habitacion;

    public Foto(String nombre, String ruta,Habitacion habitacion) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.habitacion = habitacion;
    }

}
