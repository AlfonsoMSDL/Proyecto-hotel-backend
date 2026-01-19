package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.dto.request.SaveReserva;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetReserva;
import com.hotel.proyecto.proyecto_hotel.model.Reserva;
import com.hotel.proyecto.proyecto_hotel.repository.ReservaRepository;

public interface ReservaService {
    GetReserva save(SaveReserva reserva);
    Reserva  findById(Long id);
}
