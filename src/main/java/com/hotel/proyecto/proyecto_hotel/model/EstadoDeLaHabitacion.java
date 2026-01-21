package com.hotel.proyecto.proyecto_hotel.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "estados_de_las_habitaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstadoDeLaHabitacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "id_habitacion")
    private Habitacion habitacion;

    @ManyToOne()
    @JoinColumn(name = "id_estado_habitacion")
    private EstadoHabitacion estadoHabitacion;

    @Column(name = "fecha_inicio")
    private Timestamp fechaInicio;
    @Column(name = "fecha_fin")
    private Timestamp fechaFin;
}
