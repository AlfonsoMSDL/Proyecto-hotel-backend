package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.model.Reserva;
import com.hotel.proyecto.proyecto_hotel.repository.ReservaRepository;

public interface ReservaService {
    Reserva save(Reserva reserva);
    Reserva  findById(Long id);
}
