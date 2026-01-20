package com.hotel.proyecto.proyecto_hotel.dto.response;

import java.sql.Timestamp;

public record GetHistorialReserva(
    Long idReserva,
    int numeroHabitacion,
    Timestamp fechaLlegada,
    Timestamp fechaSalida,
    String estado
) {
}
