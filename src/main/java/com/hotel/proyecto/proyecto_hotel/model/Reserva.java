package com.hotel.proyecto.proyecto_hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reserva implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_llegada")
    private Timestamp fechaLlegada;
    @Column(name = "fecha_salida")
    private Timestamp fechaSalida;

    @ManyToOne()
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne()
    @JoinColumn(name = "id_habitacion")
    private Habitacion habitacion;

    @ToString.Exclude
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<EstadoDeLaReserva> estadosDeLaReserva;


}
