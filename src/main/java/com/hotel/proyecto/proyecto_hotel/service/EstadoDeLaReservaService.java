package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.model.EstadoDeLaReserva;

public interface EstadoDeLaReservaService {
    EstadoDeLaReserva findById(Long id);
    EstadoDeLaReserva save(EstadoDeLaReserva estadoReserva);
}
