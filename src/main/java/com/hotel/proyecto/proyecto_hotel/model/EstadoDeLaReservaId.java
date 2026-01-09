package com.hotel.proyecto.proyecto_hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDeLaReservaId implements Serializable {
    @Column(name = "id_reserva")
    private Long idReserva;

    @Column(name = "id_estado_reserva")
    private Long idEstadoReserva;

}
