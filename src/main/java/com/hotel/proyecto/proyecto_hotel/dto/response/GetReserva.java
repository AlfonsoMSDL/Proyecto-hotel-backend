package com.hotel.proyecto.proyecto_hotel.dto.response;

import java.sql.Timestamp;

public record GetReserva(
        Long id,
        Long idHabitacion,
        Long idUsuario,
        Timestamp fechaLlegada,
        Timestamp fechaSalida
) {
}
