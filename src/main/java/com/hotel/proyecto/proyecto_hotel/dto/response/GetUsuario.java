package com.hotel.proyecto.proyecto_hotel.dto.response;

public record GetUsuario(
        Long id,
        String nombre,
        String apellido,
        String correo,
        String celular,
        String rol
) {
}
