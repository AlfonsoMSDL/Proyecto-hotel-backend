package com.hotel.proyecto.proyecto_hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "estados_de_las_reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstadoDeLaReserva implements Serializable {
    @EmbeddedId
    private EstadoDeLaReservaId id;

    @ManyToOne
    @MapsId("idReserva")
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;

    @ManyToOne
    @MapsId("idEstadoReserva")
    @JoinColumn(name = "id_estado_reserva")
    private EstadoReserva estadoReserva;

    @Column(name = "fecha_inicio")
    private Timestamp fechaInicio;

    @Column(name = "fecha_fin")
    private Timestamp fechaFin;
}
