package com.hotel.proyecto.proyecto_hotel.dto.response;

import com.hotel.proyecto.proyecto_hotel.model.enums.Rol;

public record GetUsuarioLogin(
         Long id,
         String nombre,
         String apellido,
         String correo,
         String rol
) {
}
