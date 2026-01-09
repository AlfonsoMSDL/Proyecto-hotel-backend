package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.model.EstadoReserva;

public interface EstadoReservaService {
    EstadoReserva findById(Long id);
    EstadoReserva save(EstadoReserva estadoReserva);
}
