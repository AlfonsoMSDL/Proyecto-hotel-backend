package com.hotel.proyecto.proyecto_hotel.dto.response;

public record GetHabitacion(
    Long id,
    Integer numero,
    String tipoHabitacion,
    Double precioNoche,
    Integer capacidad,
    String descripcion
) {
}
