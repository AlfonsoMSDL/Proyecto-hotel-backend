package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.dto.response.GetUsuario;

public interface AuthService {
    Object login(String correo, String clave) ;
}
