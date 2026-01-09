package com.hotel.proyecto.proyecto_hotel.dto.response;

public record GetFoto(
        Long id,
        String nombre,
        String ruta,
        Long idHabitacion
) {
}
