package com.hotel.proyecto.proyecto_hotel.dto.request;

public record UsuarioSave(
        String nombre,
        String apellido,
        String correo,
        String celular,
        String clave
) {
}
