package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.model.EstadoDeLaReserva;
import com.hotel.proyecto.proyecto_hotel.model.EstadoReserva;
import com.hotel.proyecto.proyecto_hotel.model.Reserva;

public interface EstadoDeLaReservaService {
    EstadoDeLaReserva findById(Long id);
    EstadoDeLaReserva save(EstadoDeLaReserva estadoReserva);
    EstadoDeLaReserva findByReservaAndEstadoReserva(Reserva reserva, EstadoReserva estadoReserva);
}
