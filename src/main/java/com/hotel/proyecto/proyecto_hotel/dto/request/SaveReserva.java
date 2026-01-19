package com.hotel.proyecto.proyecto_hotel.dto.request;

import java.sql.Timestamp;

public record SaveReserva(
        Long idHabitacion,
        Long idUsuario,
        Timestamp fechaLlegada,
        Timestamp fechaSalida
) {
}
