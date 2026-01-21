package com.hotel.proyecto.proyecto_hotel.service;

import com.hotel.proyecto.proyecto_hotel.dto.request.SaveReserva;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetHistorialReserva;
import com.hotel.proyecto.proyecto_hotel.dto.response.GetReserva;
import com.hotel.proyecto.proyecto_hotel.model.Reserva;
import com.hotel.proyecto.proyecto_hotel.repository.ReservaRepository;

import java.util.List;

public interface ReservaService {
    GetReserva save(SaveReserva reserva);
    Reserva  findById(Long id);
    List<GetReserva> buscarReservasConfirmadasPorHabitacion(Long idHabitacion);
    List<GetHistorialReserva> historialReservasPorUsuarioYEstadoReserva(Long idUsuario,String estado);
    void cancelarReserva(Long idReserva);
}
